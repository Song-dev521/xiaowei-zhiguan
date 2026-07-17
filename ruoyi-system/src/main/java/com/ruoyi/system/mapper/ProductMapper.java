package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Product;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product")
    List<Product> listAll();

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product getById(Long id);

    @Insert("INSERT INTO product (name, price, cost, category) VALUES (#{name}, #{price}, #{cost}, #{category})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Update("UPDATE product SET name=#{name}, price=#{price}, cost=#{cost}, category=#{category} WHERE id=#{id}")
    int update(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Long id);
}