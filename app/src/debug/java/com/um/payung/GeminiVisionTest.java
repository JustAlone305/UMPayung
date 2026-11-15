
package com.um.payung;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class GeminiVisionTest {

    private static final String TAG = "GEMINI_VISION";

    public static void runTest(Context context) {

        Log.d(TAG, "Starting image classification test");

        // Load flood image from debug resources
        Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.flood_test
        );

        if (bitmap == null) {
            Log.e(TAG, "Failed to load test image");
            return;
        }

        // Use the same model as our successful text test
        GenerativeModel ai = FirebaseAI.getInstance(
                GenerativeBackend.googleAI()
        ).generativeModel("gemini-3.5-flash-lite");

        GenerativeModelFutures model =
                GenerativeModelFutures.from(ai);

        // Image + text instructions
        Content prompt = new Content.Builder()
                .addImage(bitmap)
                .addText(
                        "You are a campus hazard photo classifier. "
                                + "Analyze the attached image. "
                                + "Choose hazardType from: "
                                + "Flood, Fallen Tree, Blocked Drain, "
                                + "Other, Unknown. "
                                + "Choose severity from: "
                                + "Low, Medium, High, Unknown. "
                                + "Use Unknown when evidence is insufficient. "
                                + "Do not invent water depth or hidden damage. "
                                + "Return only a JSON object with "
                                + "hazardType, severity and reason."
                )
                .build();

        ListenableFuture<GenerateContentResponse> response =
                model.generateContent(prompt);

        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {

                    @Override
                    public void onSuccess(
                            GenerateContentResponse result) {

                        Log.d(TAG, "SUCCESS: " + result.getText());
                    }

                    @Override
                    public void onFailure(Throwable error) {

                        Log.e(TAG, "FAILED: Image analysis", error);
                    }
                },
                MoreExecutors.directExecutor()
        );
    }
}
