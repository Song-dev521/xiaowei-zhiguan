package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Purchase;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PurchaseMapper {

    @Select("SELECT * FROM purchase ORDER BY purchase_time DESC")
    List<Purchase> listAll();

    @Select("SELECT * FROM purchase WHERE id = #{id}")
    Purchase getById(Long id);

    @Insert("INSERT INTO purchase (product_id, quantity, cost, total, supplier) VALUES (#{productId}, #{quantity}, #{cost}, #{total}, #{supplier})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Purchase purchase);

    @Delete("DELETE FROM purchase WHERE id = #{id}")
    int deleteById(Long id);
}