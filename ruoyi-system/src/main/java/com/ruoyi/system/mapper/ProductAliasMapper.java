package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ProductAlias;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProductAliasMapper {

    @Insert("INSERT INTO product_alias (product_id, alias_name) VALUES (#{productId}, #{aliasName})")
    int insert(ProductAlias alias);

    @Select("SELECT * FROM product_alias WHERE product_id = #{productId}")
    List<ProductAlias> getByProductId(Long productId);

    @Select("SELECT * FROM product_alias WHERE alias_name LIKE CONCAT('%', #{keyword}, '%')")
    List<ProductAlias> searchByAlias(String keyword);

    @Select("SELECT * FROM product_alias")
    List<ProductAlias> listAll();
}