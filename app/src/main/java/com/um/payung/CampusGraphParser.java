package com.um.payung;

import android.content.Context;

import com.um.payung.models.CampusEdge;
import com.um.payung.models.CampusNode;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusGraphParser {

    public static class GraphData {
        public final Map<String, CampusNode> nodes;
        public final List<CampusEdge> edges;

        public GraphData(Map<String, CampusNode> nodes, List<CampusEdge> edges) {
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    public static GraphData loadUMGraph(Context context) {
        Map<String, CampusNode> nodeMap = new HashMap<>();
        List<CampusEdge> edgeList = new ArrayList<>();

        try {
            // Read um_campus_network.geojson from assets
            InputStream is = context.getAssets().open("um_campus_network.geojson");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(jsonString);

            // Handle standard Overpass JSON ("elements")
            if (root.has("elements")) {
                JSONArray elements = root.getJSONArray("elements");

                // Pass 1: Extract all Node coordinates
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject item = elements.getJSONObject(i);
                    if ("node".equals(item.optString("type"))) {
                        String id = String.valueOf(item.getLong("id"));
                        double lat = item.getDouble("lat");
                        double lon = item.getDouble("lon");
                        nodeMap.put(id, new CampusNode(id, lat, lon));
                    }
                }

                // Pass 2: Extract all Ways (Road Edges)
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject item = elements.getJSONObject(i);
                    if ("way".equals(item.optString("type"))) {
                        String wayId = String.valueOf(item.getLong("id"));
                        JSONArray nodeIds = item.getJSONArray("nodes");

                        for (int j = 0; j < nodeIds.length() - 1; j++) {
                            String fromId = String.valueOf(nodeIds.getLong(j));
                            String toId = String.valueOf(nodeIds.getLong(j + 1));

                            CampusNode nodeA = nodeMap.get(fromId);
                            CampusNode nodeB = nodeMap.get(toId);

                            if (nodeA != null && nodeB != null) {
                                double distance = calculateHaversine(
                                        nodeA.getLatitude(), nodeA.getLongitude(),
                                        nodeB.getLatitude(), nodeB.getLongitude()
                                );
                                String edgeId = wayId + "_" + j;
                                edgeList.add(new CampusEdge(edgeId, fromId, toId, distance));
                            }
                        }
                    }
                }
            }
            // Handle GeoJSON format ("features")
            else if (root.has("features")) {
                JSONArray features = root.getJSONArray("features");
                for (int i = 0; i < features.length(); i++) {
                    JSONObject feature = features.getJSONObject(i);
                    JSONObject geometry = feature.getJSONObject("geometry");
                    String type = geometry.getString("type");

                    if ("LineString".equals(type)) {
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        for (int j = 0; j < coordinates.length() - 1; j++) {
                            JSONArray coordA = coordinates.getJSONArray(j);
                            JSONArray coordB = coordinates.getJSONArray(j + 1);

                            double lonA = coordA.getDouble(0);
                            double latA = coordA.getDouble(1);
                            double lonB = coordB.getDouble(0);
                            double latB = coordB.getDouble(1);

                            String idA = "N_" + i + "_" + j;
                            String idB = "N_" + i + "_" + (j + 1);

                            nodeMap.putIfAbsent(idA, new CampusNode(idA, latA, lonA));
                            nodeMap.putIfAbsent(idB, new CampusNode(idB, latB, lonB));

                            double distance = calculateHaversine(latA, lonA, latB, lonB);
                            edgeList.add(new CampusEdge("E_" + i + "_" + j, idA, idB, distance));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new GraphData(nodeMap, edgeList);
    }

    private static double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371000.0; // Earth radius in meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}