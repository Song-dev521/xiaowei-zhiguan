package com.ruoyi.web.controller.alias;

import com.ruoyi.system.domain.ProductAlias;
import com.ruoyi.system.mapper.ProductAliasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alias")
public class AliasController {

    @Autowired
    private ProductAliasMapper aliasMapper;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody ProductAlias alias) {
        Map<String, Object> result = new HashMap<>();
        try {
            aliasMapper.insert(alias);
            result.put("code", 200);
            result.put("data", "别名添加成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ProductAlias> list = aliasMapper.listAll();
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
            List<ProductAlias> list = aliasMapper.getByProductId(productId);
            result.put("code", 200);
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam String keyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ProductAlias> list = aliasMapper.searchByAlias(keyword);
            result.put("code", 200);
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        return result;
    }
}