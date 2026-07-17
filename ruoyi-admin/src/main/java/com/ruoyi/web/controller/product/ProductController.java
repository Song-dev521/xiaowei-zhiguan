package com.ruoyi.web.controller.product;

import com.ruoyi.system.domain.Product;
import com.ruoyi.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> list = productService.listAll();
            result.put("code", 200);
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Product product) {
        Map<String, Object> result = new HashMap<>();
        try {
            productService.insert(product);
            result.put("code", 200);
            result.put("data", "添加成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Product product) {
        Map<String, Object> result = new HashMap<>();
        try {
            productService.update(product);
            result.put("code", 200);
            result.put("data", "更新成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            productService.deleteById(id);
            result.put("code", 200);
            result.put("data", "删除成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }
}