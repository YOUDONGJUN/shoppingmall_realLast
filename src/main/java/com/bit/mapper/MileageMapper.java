package com.bit.mapper;

import com.bit.model.MileageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface MileageMapper {
    public ArrayList<MileageDTO> getUserMileages(String memberIdx);

    public int getUserTotalMileages(String memberIdx);

    public ArrayList<String> getUserMileageStateList(String memberIdx);

    public ArrayList<MileageDTO> getUnusedUserMileages(String memberIdx);
}
