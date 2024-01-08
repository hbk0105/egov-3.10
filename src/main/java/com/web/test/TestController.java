package com.web.test;

import com.set.util.SessionConst;
import com.set.util.SessionUtil;
import com.web.user.vo.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class TestController {

    @RequestMapping("/test.do")
    public String test( Model model) {

        //세션에 회원 데이터가 없으면 home
        if (SessionUtil.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "user/login";
        }
        UserInfo userInfo = (UserInfo) SessionUtil.getAttribute(SessionConst.LOGIN_USER);
        System.out.println("### userInfo " + userInfo);
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("userEntity", userInfo);
        return "user/userList";
    }

    @RequestMapping("/login.do")
    public String login(HttpSession session, Model model) {
        // id와 password를 사용하여 사용자 인증을 수행
        // 예를 들어, 데이터베이스에서 해당 id로 사용자 정보를 조회하고, 비밀번호 일치 여부를 확인하는 로직
        // 인증 성공
        String rt = "";
        try{
            if (true) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId((long)(Math.random()*10));
                userInfo.setUsername("michael"+(long)(Math.random()*10));
                userInfo.setEmail("michael@sangs.co.rk"+(long)(Math.random()*10));
                userInfo.setPassword("1234"+(long)(Math.random()*10));

                System.out.println("### userInfo --> " + userInfo);
                SessionUtil.setAttribute(SessionConst.LOGIN_USER, userInfo); // 세션에 로그인 정보 저장
                model.addAttribute("user", userInfo);
                //return "loginHome"; // 로그인 홈 화면으로 이동
                rt =  "/user/success";
            } else {
                model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
                // return "loginForm"; // 로그인 폼 화면으로 이동
                rt =  "user/fail";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return rt;
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletResponse response, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate(); //세션을 제거한다.
        }
        return "redirect:/";
    }
}