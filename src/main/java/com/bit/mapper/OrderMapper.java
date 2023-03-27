package com.bit.mapper;

import com.bit.model.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface OrderMapper {
    public ArrayList<String> getUserOrdersDeliveryStates(String memberIdx);

    public ArrayList<OrderDTO> getUserOrders(String memberIdx);
}
