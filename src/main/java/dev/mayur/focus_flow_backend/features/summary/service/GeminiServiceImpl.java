package dev.mayur.focus_flow_backend.features.summary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements AiService {

    private final WebClient webClient;
    @Value("${ai.api.key}")
    private String apiKey;
    @Value("${ai.api.url}")
    private String apiUrl;


    @Override
    public String generateSummary(String input) {
        String url = apiUrl + "?key=" + apiKey;

        Map<String, Object> requestBody = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", buildPrompt(input))))));

        try {
            Map response = webClient.post().uri(url).bodyValue(requestBody).retrieve().bodyToMono(Map.class).block(); // blocking for simplicity (OK for MVP)

            // 🔍 Extract response safely
            List<Map> candidates = (List<Map>) response.get("candidates");
            Map content = (Map) candidates.get(0).get("content");
            List<Map> parts = (List<Map>) content.get("parts");

            return parts.get(0).get("text").toString();

        } catch (Exception e) {
            return "AI summary not available right now.";
        }
    }

//    private String buildPrompt(String input) {
//        return """
//                You are a productivity assistant.
//
//                Analyze the user's daily tasks and generate:
//                1. A short summary (2-3 lines)
//                2. One key insight
//
//                Keep it simple and clear.
//
//                Tasks:
//                """ + input;
//    }

    private String buildPrompt(String input) {
        return """
        You are a productivity assistant.

        Analyze the user's tasks and produce a concise response.

        Rules:
        - Keep it short and meaningful
        - Focus on actual work, time usage, and patterns
        - Avoid generic or repetitive statements

        Output format:
        First section:
        Write 1-2 lines describing what the user mainly did.

        Second section:
        Write 1 single line giving a key insight about focus, time usage, or category.

        Formatting rules:
        - Do NOT include labels like "Summary" or "Insight"
        - Do NOT use symbols like / or bullet points
        - Use plain text with a normal line break between the two sections

        Tasks:
        """ + input;
    }

//    private String buildPrompt(String input) {
//        return """
//        You are a productivity assistant.
//
//        Analyze the user's tasks and return a concise result.
//
//        Rules:
//        - Keep it short and clear
//        - Focus on actual work done, time usage, and patterns
//        - Avoid generic statements
//
//        Output format:
//        Summary:
//        <max 2 lines describing main work>
//
//        Insight:
//        <1 line key observation about focus, time, or category>
//
//        Do not use symbols like / or bullet points or don' give new line symbol or /n. Use plain text with line breaks only.
//        Do not include summary or Summary text in summary.
//
//        Tasks:
//        """ + input;
//    }
}
