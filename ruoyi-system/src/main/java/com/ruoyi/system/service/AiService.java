package com.ruoyi.system.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private static final String API_KEY = "sk-cc68131c2e6c4a948e146d6d4f634f0d";
    private static final String URL = "https://api.deepseek.com/chat/completions";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String callDeepSeek(String userMessage) throws Exception {
        // 构建请求体
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "deepseek-chat");
        bodyMap.put("temperature", 0.7);

        Map<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", userMessage);
        bodyMap.put("messages", new Map[]{msg});

        String jsonBody = objectMapper.writeValueAsString(bodyMap);

        System.out.println("=== 发送请求 ===");
        System.out.println(jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        System.out.println("=== DeepSeek API 返回 ===");
        System.out.println(responseBody);

        // 使用 Jackson 解析返回
        JsonNode root = objectMapper.readTree(responseBody);

        // 检查错误
        if (root.has("error")) {
            JsonNode error = root.get("error");
            throw new Exception("DeepSeek API 错误: " + error.get("message").asText());
        }

        // 获取 content
        JsonNode choices = root.get("choices");
        if (choices == null || !choices.isArray() || choices.isEmpty()) {
            throw new Exception("choices 为空");
        }

        JsonNode choice = choices.get(0);
        JsonNode message = choice.get("message");
        if (message == null) {
            throw new Exception("message 为空");
        }

        JsonNode contentNode = message.get("content");
        if (contentNode == null) {
            throw new Exception("content 为空");
        }

        String content = contentNode.asText();
        if (content == null || content.isEmpty()) {
            throw new Exception("content 为空");
        }

        return content;
    }

    public String getAdvice(String template, String userInput) throws Exception {
        String fullPrompt = template + "\n\n【用户输入】\n" + userInput;
        return callDeepSeek(fullPrompt);
    }
}