package com.bit.mapper;

import com.bit.model.CartDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    public int insertUserCart(CartDTO cartDTO);
}
