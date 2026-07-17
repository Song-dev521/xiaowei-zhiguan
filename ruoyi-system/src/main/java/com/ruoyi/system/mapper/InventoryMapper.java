package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Inventory;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface InventoryMapper {

    @Select("SELECT * FROM inventory")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "stock", column = "stock")
    })
    List<Inventory> listAll();

    @Select("SELECT * FROM inventory WHERE product_id = #{productId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "stock", column = "stock")
    })
    Inventory getByProductId(Long productId);

    @Insert("INSERT INTO inventory (product_id, stock) VALUES (#{productId}, #{stock})")
    int insert(Inventory inventory);

    @Update("UPDATE inventory SET stock = stock + #{quantity} WHERE product_id = #{productId}")
    int addStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);

    @Update("UPDATE inventory SET stock = stock - #{quantity} WHERE product_id = #{productId}")
    int reduceStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}