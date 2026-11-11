package com.um.payung;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private CampusGraphParser.GraphData campusGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the 14,000+ node graph asynchronously on a worker thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            CampusGraphParser.GraphData graph = CampusGraphParser.loadUMGraph(MainActivity.this);

            // Switch back to Main Thread once loading is done
            runOnUiThread(() -> {
                campusGraph = graph;
                Log.d("UM_GRAPH", "Successfully loaded into memory: " +
                        campusGraph.nodes.size() + " nodes, " +
                        campusGraph.edges.size() + " edges.");

                // Ready to initialize Google Maps or pass to CampusRouter here
            });
        });
    }
}