package main.java;

import com.google.cloud.translate.v3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TranslateAPI {
    private static final String projectId = "translation-330114";

    /** Detect input language. */
    public static String detectLanguage(String textToDetect) throws IOException {
        // TODO(developer): Replace these variables before running the sample.
        String text = textToDetect;
        return detectLanguage(projectId, text);
    }

    // Detecting the language of a text string
    private static String detectLanguage(String projectId, String text) throws IOException {

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, "global");

            DetectLanguageRequest request =
                    DetectLanguageRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setContent(text)
                            .build();

            DetectLanguageResponse response = client.detectLanguage(request);

            // Display list of detected languages sorted by detection confidence.
            // The most probable language is first.
            List<DetectedLanguage> detectLang = new ArrayList<>();
            return detectLang.get(0).getLanguageCode();
        }
    }

    /** Translate input. */
    public static String translateText(String textToTranslate, String targetLang) throws IOException {
        return translateText(projectId, targetLang, textToTranslate);
    }

    private static String translateText(String projectId, String targetLang, String text) throws IOException {
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, "global");

            TranslateTextRequest request = TranslateTextRequest.newBuilder()
                    .setParent(parent.toString())
                    .setMimeType("text/plain")
                    .setTargetLanguageCode(targetLang)
                    .addContents(text)
                    .build();
            TranslateTextResponse response = client.translateText(request);
            List<Translation> res = response.getTranslationsList();
            return res.get(0).getTranslatedText();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(translateText("ебать мать сукин сын", "vi"));
    }
}
