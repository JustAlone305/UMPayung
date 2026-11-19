
package com.um.payung;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;

public class AiDebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("AI_SETUP", "AiDebugApplication started");

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Use App Check Debug Provider
        FirebaseAppCheck appCheck =
                FirebaseAppCheck.getInstance();

        appCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
        );

        // Trigger a token request for testing

        appCheck.getAppCheckToken(false)
                .addOnSuccessListener(result -> {

                    Log.d("AI_SETUP", "App Check token obtained");

                    // Run Gemini test after App Check succeeds
                    //GeminiTest.runTest();
                    //GeminiVisionTest.runTest(this);

                })
                .addOnFailureListener(error ->
                        Log.e("AI_SETUP",
                                "App Check verification failed",
                                error));

    }
}
