package com.ruoyi.system.service;

import com.ruoyi.system.domain.Inventory;
import com.ruoyi.system.domain.Sales;
import com.ruoyi.system.mapper.InventoryMapper;
import com.ruoyi.system.mapper.SalesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    public List<Sales> listAll() {
        return salesMapper.listAll();
    }

    public Sales getById(Long id) {
        return salesMapper.getById(id);
    }

    @Transactional
    public int insert(Sales sales) throws Exception {
        // 1. 检查库存是否足够
        Inventory inv = inventoryMapper.getByProductId(sales.getProductId());
        if (inv == null || inv.getStock() < sales.getQuantity()) {
            throw new Exception("库存不足！当前库存: " + (inv == null ? 0 : inv.getStock()));
        }

        // 2. 插入销售单
        int result = salesMapper.insert(sales);
        if (result <= 0) return result;

        // 3. 减少库存
        inventoryMapper.reduceStock(sales.getProductId(), sales.getQuantity());
        return result;
    }

    public int deleteById(Long id) {
        return salesMapper.deleteById(id);
    }
}