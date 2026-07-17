package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Sales {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
    private String customer;
    private Integer isCredit;
    private LocalDateTime saleTime;

    // getter 和 setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
    public Integer getIsCredit() { return isCredit; }
    public void setIsCredit(Integer isCredit) { this.isCredit = isCredit; }
    public LocalDateTime getSaleTime() { return saleTime; }
    public void setSaleTime(LocalDateTime saleTime) { this.saleTime = saleTime; }
}