package com.bit.service;

import com.bit.mapper.WishMapper;
import com.bit.model.MemberDTO;
import com.bit.model.WishDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class WishService {

    @Autowired
    WishMapper wishMapper;

    public void getUserWishes(Model model,
                              HttpSession session
    ) {
        // TODO Auto-generated method stub
        MemberDTO dto = (MemberDTO) session.getAttribute("userSessionData");
        String LoginUserIdx = dto.getMemberIdx();
        ArrayList<WishDTO> userWishList = wishMapper.getUserWishes(LoginUserIdx);
        model.addAttribute("userWishList", userWishList);
        userWishList.forEach((wish) -> System.out.println(wish.getWishName()));

    }

    public void deleteUserWishes(String wishId, HttpServletRequest request) {
        // TODO Auto-generated method stub
        System.out.println("wishService");
        System.out.println(wishId);

        wishMapper.deleteUserWishes(wishId);
    }
}
