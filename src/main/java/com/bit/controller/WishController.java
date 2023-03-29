package com.bit.controller;

import com.bit.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/wish")
public class WishController {
    @Autowired
    WishService wishService;

    @GetMapping("/readWishes")
    public String createReview() {
        return "myPage/readWishes";
    }

    @ResponseBody
    @PostMapping(value = "deleteUserWishes", produces = "application/json; charset=utf-8")
    public String deleteUserWishes(
            @RequestBody Map<String, String> map,
            HttpServletRequest request) {
        try {
            System.out.println("wishController");
            System.out.println(map.get("wishId"));
            wishService.deleteUserWishes(map.get("wishId"), request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "myPage/readWishes";
    }
}
