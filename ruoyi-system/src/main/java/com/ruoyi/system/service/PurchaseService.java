package com.ruoyi.system.service;

import com.ruoyi.system.domain.Inventory;
import com.ruoyi.system.domain.Purchase;
import com.ruoyi.system.mapper.InventoryMapper;
import com.ruoyi.system.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    public List<Purchase> listAll() {
        return purchaseMapper.listAll();
    }

    public Purchase getById(Long id) {
        return purchaseMapper.getById(id);
    }

    @Transactional
    public int insert(Purchase purchase) {
        System.out.println("=== 进货 ===");
        System.out.println("productId: " + purchase.getProductId());
        System.out.println("quantity: " + purchase.getQuantity());

        int result = purchaseMapper.insert(purchase);
        if (result <= 0) return result;

        Inventory inv = inventoryMapper.getByProductId(purchase.getProductId());
        if (inv == null) {
            inv = new Inventory();
            inv.setProductId(purchase.getProductId());
            inv.setStock(purchase.getQuantity());
            inventoryMapper.insert(inv);
            System.out.println("新建库存: productId=" + purchase.getProductId() + ", stock=" + purchase.getQuantity());
        } else {
            inventoryMapper.addStock(purchase.getProductId(), purchase.getQuantity());
            System.out.println("更新库存: productId=" + purchase.getProductId() + ", 新增=" + purchase.getQuantity());
        }
        return result;
    }

    public int deleteById(Long id) {
        return purchaseMapper.deleteById(id);
    }
}