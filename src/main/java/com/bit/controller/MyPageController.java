package com.bit.controller;

import com.bit.mapper.ProductMapper;
import com.bit.model.MemberDTO;
import com.bit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


@Controller
@RequestMapping("myPage")
public class MyPageController {
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;
    @Autowired
    WishService wishService;
    @Autowired
    MileageService mileageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ProductMapper productMapper;

    /* 마이페이지 컨트롤러 */
    @GetMapping("myPage")
    public String myPage() {
        return "myPage/myPage";
    }

    @GetMapping("cart")
    public String cart(HttpSession session, Model model, HttpServletRequest re) {
        //System.out.println(re.getParameterValues("chtest"));
        //String[] str = re.getParameterValues("chtest");
		/*
		for(String productId : str) {
			System.out.println(productId);
		}
		*/
        String memberId = (String) session.getAttribute("loginUser");
        String memberIdx = productMapper.selectMemberIdx(memberId);
        model.addAttribute("cart", productMapper.selectCart(memberIdx));
        return "myPage/cart";
    }
//	myPage

    @GetMapping("readOrders")
    public String readOrders(MemberDTO memberDTO,
                             HttpServletRequest request,
                             Model model,
                             HttpSession session) {
        orderService.getUserOrdersDeliveryStates(request, model, session);
        orderService.getUserOrders(model, session);

        return "myPage/readOrders";
    }

    @GetMapping("readWishes")
    public String readWishes(MemberDTO memberDTO,
                             HttpServletRequest request,
                             Model model,
                             HttpSession session){
        wishService.getUserWishes(model, session);
        return "myPage/readWishes";
    }

    @GetMapping("readMileage")
    public String readMileage(MemberDTO memberDTO,
                              HttpServletRequest request,
                              Model model,
                              HttpSession session) {
        mileageService.getUserMileages(model, session);
        mileageService.getUserTotalMileage(model, session);
        mileageService.getUserMileageStateList(request, model, session);
        return "myPage/readMileage";
    }

    @GetMapping("readUnusedMileage")
    public String readUnusedMileage(MemberDTO memberDTO,
                                    HttpServletRequest request,
                                    Model model,
                                    HttpSession session) {
        mileageService.getUnusedUserMileages(model, session);
        mileageService.getUserTotalMileage(model, session);
        mileageService.getUserMileageStateList(request, model, session);
        return "myPage/readMileage";
    }

    @GetMapping("/createReview")
    public String createReview(MemberDTO memberDTO,
                               MultipartHttpServletRequest mul,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(name = "orderProductId", required = false) String orderProductId,
                               @RequestParam(name = "orderProductName", required = false) String orderProductName
    ) throws Exception {

        HttpSession session = request.getSession();
        session.setAttribute("orderProductId", orderProductId);
        session.setAttribute("orderProductName", orderProductName);

        return "myPage/createReview";
    }

    @PostMapping("saveReview")
    public void saveReview(MemberDTO memberDTO,
                           MultipartHttpServletRequest mul,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {

        String message = reviewService.reviewSave(mul, request, "test");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(message);

    }
}
