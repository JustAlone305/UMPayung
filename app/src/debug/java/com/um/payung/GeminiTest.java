
package com.um.payung;

import android.util.Log;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import com.google.firebase.ai.FirebaseAI;
import com.google.firebase.ai.GenerativeModel;
import com.google.firebase.ai.java.GenerativeModelFutures;
import com.google.firebase.ai.type.Content;
import com.google.firebase.ai.type.GenerateContentResponse;
import com.google.firebase.ai.type.GenerativeBackend;

public class GeminiTest {

    public static void runTest() {

        Log.d("GEMINI_TEST", "Starting Gemini API test...");

        // Connect to Gemini Developer API
        GenerativeModel ai =
                FirebaseAI.getInstance(
                        GenerativeBackend.googleAI()
                ).generativeModel("gemini-3.5-flash-lite");

        GenerativeModelFutures model =
                GenerativeModelFutures.from(ai);

        // Test prompt
        Content prompt = new Content.Builder()
                .addText(
                        "What should a university student do "
                                + "when encountering a flooded road? "
                                + "Answer in one short sentence."
                )
                .build();

        // Send request
        ListenableFuture<GenerateContentResponse> response =
                model.generateContent(prompt);

        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {

                    @Override
                    public void onSuccess(
                            GenerateContentResponse result) {

                        Log.d("GEMINI_TEST",
                                "SUCCESS: " + result.getText());
                    }

                    @Override
                    public void onFailure(Throwable error) {

                        Log.e("GEMINI_TEST",
                                "FAILED: Gemini API request",
                                error);
                    }
                },
                MoreExecutors.directExecutor()
        );
    }
}
