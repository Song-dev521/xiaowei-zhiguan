package com.ruoyi.system.service;

import com.ruoyi.system.domain.Product;
import com.ruoyi.system.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> listAll() {
        return productMapper.listAll();
    }

    public Product getById(Long id) {
        return productMapper.getById(id);
    }

    public int insert(Product product) {
        return productMapper.insert(product);
    }

    public int update(Product product) {
        return productMapper.update(product);
    }

    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }
}