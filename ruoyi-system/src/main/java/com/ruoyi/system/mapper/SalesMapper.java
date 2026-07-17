package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Sales;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SalesMapper {

    @Select("SELECT * FROM sales ORDER BY sale_time DESC")
    List<Sales> listAll();

    @Select("SELECT * FROM sales WHERE id = #{id}")
    Sales getById(Long id);

    @Insert("INSERT INTO sales (product_id, quantity, price, total, customer, is_credit) VALUES (#{productId}, #{quantity}, #{price}, #{total}, #{customer}, #{isCredit})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Sales sales);

    @Delete("DELETE FROM sales WHERE id = #{id}")
    int deleteById(Long id);
}