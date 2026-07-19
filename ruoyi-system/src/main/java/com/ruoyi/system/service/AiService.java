package com.ruoyi.system.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private static final String API_KEY = "sk-cc68131c2e6c4a948e146d6d4f634f0d";
    private static final String URL = "https://api.deepseek.com/chat/completions";

    // ========== 天气 API 配置（OpenWeatherMap） ==========
    private static final String WEATHER_KEY = "154e7bc032216ac17bfee5abd9fd1952";  // 替换成你注册的Key
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=Neijiang&appid=" + WEATHER_KEY + "&units=metric&lang=zh_cn";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ========== 获取天气（自动获取，失败降级） ==========
    private String getWeather() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(WEATHER_URL))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            JsonNode root = objectMapper.readTree(responseBody);

            // 检查返回码
            if (root.has("cod") && !"200".equals(root.get("cod").asText())) {
                return "未获取到天气数据";
            }

            // 提取温度和天气描述
            JsonNode main = root.get("main");
            JsonNode weather = root.get("weather");
            double temp = main.get("temp").asDouble();
            String desc = weather.get(0).get("description").asText();

            return "当前天气：" + desc + "，温度：" + temp + "℃";
        } catch (Exception e) {
            // 降级方案：API 失败时返回默认信息
            return "今日天气晴，气温适中，适合正常营业";
        }
    }

    // ========== 获取节假日 ==========
    private String getHoliday() {
        try {
            LocalDate today = LocalDate.now();
            int month = today.getMonthValue();
            int day = today.getDayOfMonth();
            String todayStr = String.format("%02d%02d", month, day);

            String[] holidays = {
                    "0101", // 元旦
                    "0405", // 清明节
                    "0501", // 劳动节
                    "0601", // 儿童节
                    "1001", "1002", "1003", "1004", "1005", "1006", "1007", // 国庆节
                    "1225"  // 圣诞节
            };

            for (String h : holidays) {
                if (h.equals(todayStr)) {
                    return "今天是节假日，客流可能增加，建议提前备货";
                }
            }

            DayOfWeek dow = today.getDayOfWeek();
            if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                return "今天是周末，客流可能增加";
            }

            return "今天是普通工作日";
        } catch (Exception e) {
            return "未获取到节假日数据";
        }
    }

    // ========== 调用 DeepSeek API ==========
    public String callDeepSeek(String userMessage) throws Exception {
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

        JsonNode root = objectMapper.readTree(responseBody);

        if (root.has("error")) {
            JsonNode error = root.get("error");
            throw new Exception("DeepSeek API 错误: " + error.get("message").asText());
        }

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

    // ========== 对外接口：生成经营建议（自动带天气+节假日） ==========
    public String getAdvice(String template, String userInput) throws Exception {
        String weather = getWeather();
        String holiday = getHoliday();
        String fullPrompt = template + "\n\n【天气信息】\n" + weather + "\n\n【节假日信息】\n" + holiday + "\n\n【用户输入】\n" + userInput;
        return callDeepSeek(fullPrompt);
    }
}
