package com.um.payung;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private CampusGraphParser.GraphData campusGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Bottom Navigation with Navigation Controller
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            NavigationUI.setupWithNavController(bottomNav, navController);
        }

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

    public CampusGraphParser.GraphData getCampusGraph() {
        return campusGraph;
    }
}
