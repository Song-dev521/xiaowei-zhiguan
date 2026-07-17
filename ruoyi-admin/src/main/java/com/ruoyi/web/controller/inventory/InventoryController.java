package com.ruoyi.web.controller.inventory;

import com.ruoyi.system.domain.Inventory;
import com.ruoyi.system.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryMapper inventoryMapper;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 使用 MyBatis 的 @Select 查询所有库存
            // 但 InventoryMapper 里没有 listAll 方法，我们直接用 SQL 查询
            List<Inventory> list = inventoryMapper.listAll();
            result.put("code", 200);
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @GetMapping("/product/{productId}")
    public Map<String, Object> getByProductId(@PathVariable Long productId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Inventory inv = inventoryMapper.getByProductId(productId);
            result.put("code", 200);
            result.put("data", inv);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }
}