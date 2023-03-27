package com.bit.mapper;

import com.bit.model.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    public int reviewSave(ReviewDTO dto);

}
