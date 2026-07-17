package com.ruoyi.web.controller.ai;

import com.ruoyi.system.service.AiService;
import com.ruoyi.system.service.PromptTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    @Autowired
    private PromptTemplateService promptTemplateService;

    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String message = request.get("message");
            String response = aiService.callDeepSeek(message);
            result.put("code", 200);
            result.put("data", response);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @PostMapping("/advice")
    public Map<String, Object> advice(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String templateId = request.get("templateId");
            String input = request.get("input");
            String template = promptTemplateService.getTemplate(templateId);
            if (template == null) {
                result.put("code", 404);
                result.put("error", "模板不存在: " + templateId);
                return result;
            }
            String response = aiService.getAdvice(template, input);
            result.put("code", 200);
            result.put("data", response);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @GetMapping("/templates")
    public Map<String, Object> getTemplates() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", promptTemplateService.getAllTemplates());
        return result;
    }
}