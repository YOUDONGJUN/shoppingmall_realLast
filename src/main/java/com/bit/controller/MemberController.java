package com.bit.controller;

import com.bit.model.MemberDTO;
import com.bit.service.*;
import com.bit.session_name.MemberSessionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;

@Controller
@RequestMapping("member")
public class MemberController implements MemberSessionName {

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
//    private JavaMailSender mailSender;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    @PostMapping("/user_check")
    public String user_check(HttpServletRequest request,
                             RedirectAttributes rs,
                             Model model,
                             HttpSession session,
                             HttpServletResponse response) {

        int result = memberService.user_check(request, model, session);
        if (result == 0) {
            rs.addAttribute("id", request.getParameter("memberId"));
            rs.addAttribute("autoLogin", request.getParameter("autoLogin"));

            return "redirect:successLogin";
        } else {
            rs.addFlashAttribute("msg", "fail");
            return "redirect:login";
        }
    }

    @RequestMapping("/successLogin")
    public String successLogin(@RequestParam String id,
                               @RequestParam(value = "autoLogin", required = false) String autoLogin,
                               HttpSession session,
                               HttpServletResponse response) {
        session.setAttribute(LOGIN, id);
        if (autoLogin != null) {
            int limitTime = 60 * 60 * 24 * 90; //90일
            Cookie loginCookie = new Cookie("loginCookie", session.getId());
            loginCookie.setPath("/");
            loginCookie.setMaxAge(limitTime);
            response.addCookie(loginCookie);

            //long expiredDate = System.currentTimeMillis() + (limitTime*1000);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new java.util.Date());
            cal.add(Calendar.MONTH, 3);

            Date limitDate = new Date(cal.getTimeInMillis());
            memberService.keepLogin(session.getId(), limitDate, id);
        }
        return "member/successLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response,
                         @CookieValue(value = "loginCookie", required = false) Cookie loginCookie) {
        if (session.getAttribute(LOGIN) != null) {
            if (loginCookie != null) {
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
                memberService.keepLogin("nan", new Date(System.currentTimeMillis()),
                        (String) session.getAttribute(LOGIN));
            }
            session.invalidate();
        }
        return "redirect:/index";
    }

    @GetMapping("memberInfo")
    public String memberInfo(Model model) {
        memberService.memberInfo(model);
        return "member/memberInfo";
    }

    @GetMapping("info")
    public String info(@RequestParam("memberId") String userId, Model model) {
        memberService.info(userId, model);
        return "member/info";
    }

    /*로그인 한 회원정보*/
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        memberService.profile(model, (String) session.getAttribute(LOGIN));
        return "member/profile";
    }

    /*회원정보 수정*/
    @PostMapping("modify")
    public String modify(HttpServletResponse response,
                         HttpServletRequest request) throws Exception {
        memberService.modify(request);
        return "redirect:/member/profile";
    }

    /*회원 탈퇴*/
    @GetMapping("delete")
    public String delete(HttpSession session) {
        memberService.delete((String) session.getAttribute(LOGIN));
        return "redirect:/member/logout";
    }

    @GetMapping("save")
    public String save(Model model) {
        // ms.save(사용자 정보를); 서비스로 이동해서 데이터를 저장한다
        return "redirect:/member/memberInfo";
    }

    @GetMapping("register_form")
    public String register_form() {
        return "member/join";
    }

    @PostMapping("register")
    public String register(MemberDTO memberDTO) {
        int result = memberService.register(memberDTO);
        if (result == 1) {
            return "redirect:login";
        }
        return "redirect:join"; // redirect:com.bit.member/join (?)
    }


//	myPage

    @GetMapping("readOrders")
    public String readOrders(MemberDTO memberDTO,
                             HttpServletRequest request,
                             Model model,
                             HttpSession session) {
        orderService.getUserOrdersDeliveryStates(request, model, session);
        orderService.getUserOrders(model, session);

        return "eunbin/readOrders";
    }

    @GetMapping("readWishes")
    public String readWishes(MemberDTO memberDTO,
                             HttpServletRequest request,
                             Model model,
                             HttpSession session) {
        wishService.getUserWishes(model, session);
        return "eunbin/readWishes";
    }

    @GetMapping("readMileage")
    public String readMileage(MemberDTO memberDTO,
                              HttpServletRequest request,
                              Model model,
                              HttpSession session) {
        mileageService.getUserMileages(model, session);
        mileageService.getUserTotalMileage(model, session);
        mileageService.getUserMileageStateList(request, model, session);
        return "eunbin/readMileage";
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

        return "eunbin/createReview";
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

    //아이디 찾기 페이지
    @RequestMapping("find_id_form.do")
    public String find_id_form() {
        return "member/find_id_form";
    }

    //아이디찾기
    @RequestMapping("/find_id.do")
    public String find_id(HttpServletResponse response, @RequestParam(value = "memberEmail", required = false) String email, Model md) throws Exception {
        md.addAttribute("id", memberService.find_id(response, email));
        return "member/find_id";
    }

    //비번 찾기 페이지
    @RequestMapping("find_pw_form.do")
    public String find_pw_form() {
        return "member/find_pw_form";
    }

    //비번 찾기
    @RequestMapping("/find_pw.do")
    public String find_pw(HttpServletResponse response, @RequestParam(value = "memberId", required = false) String id, Model md) throws Exception {
        md.addAttribute("pw", memberService.find_pw(response, id));
        return "member/find_pw";
    }

    @RequestMapping("/memberIdCheck")
    @ResponseBody
    public String memberIdCheckPOST(String memberId) {
        int result = memberService.idCheck(memberId);
        if (result != 0) {
            return "fail";
        } else {
            return "success";
        }
    }

//    @RequestMapping("/mailCheck")
//    @ResponseBody
//    public String mailCheckGET(String email) {
//        Random random = new Random();
//        int checkNum = random.nextInt(888888) + 111111;
//
//        String setFrom = "hyj870069@gmail.com";
//        String toMail = email;
//        String title = "회원가입 인증 확인";
//        String content = "인증번호는 " + checkNum + " 입니다";
//
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
//            helper.setFrom(setFrom);
//            helper.setTo(toMail);
//            helper.setSubject(title);
//            helper.setText(content, true);
//            mailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String num = Integer.toString(checkNum);
//        return num;
//    }

}






