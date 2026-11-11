package com.um.payung.models;

public class CampusEdge {
    private String edgeId;
    private String fromNodeId;
    private String toNodeId;
    private double weightMeters;
    private boolean isBlocked; // Set to true when blocked by flood or fallen tree

    public CampusEdge() {}

    public CampusEdge(String edgeId, String fromNodeId, String toNodeId, double weightMeters) {
        this.edgeId = edgeId;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.weightMeters = weightMeters;
        this.isBlocked = false;
    }

    public String getEdgeId() { return edgeId; }
    public String getFromNodeId() { return fromNodeId; }
    public String getToNodeId() { return toNodeId; }
    public double getWeightMeters() { return weightMeters; }
    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }
}