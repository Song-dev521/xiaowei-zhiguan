package com.ruoyi.web.controller.sales;

import com.ruoyi.system.domain.Sales;
import com.ruoyi.system.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Sales> list = salesService.listAll();
            result.put("code", 200);
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Sales sales) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 自动计算总金额
            sales.setTotal(sales.getPrice().multiply(new java.math.BigDecimal(sales.getQuantity())));
            salesService.insert(sales);
            result.put("code", 200);
            result.put("data", "销售成功，库存已更新");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }
}