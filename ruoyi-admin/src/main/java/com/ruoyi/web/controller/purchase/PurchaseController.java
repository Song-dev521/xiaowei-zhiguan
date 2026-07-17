package com.ruoyi.web.controller.purchase;

import com.ruoyi.system.domain.Purchase;
import com.ruoyi.system.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Purchase> list = purchaseService.listAll();
            result.put("code", 200);
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Purchase purchase) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 自动计算总金额
            purchase.setTotal(purchase.getCost().multiply(new java.math.BigDecimal(purchase.getQuantity())));
            purchaseService.insert(purchase);
            result.put("code", 200);
            result.put("data", "进货成功，库存已更新");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }
}