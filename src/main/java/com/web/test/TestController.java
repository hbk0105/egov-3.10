package com.web.test;

import com.stn.util.CommandMap;
import com.stn.util.MySessionManager;
import com.stn.util.SessionConst;
import com.stn.util.SessionUtil;
import com.web.user.vo.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class TestController {

    @RequestMapping("/test.do")
    public String test( Model model) {

        //세션에 회원 데이터가 없으면 home
        if (SessionUtil.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "/egovframework/example/user/login";
        }
        UserEntity userEntity = (UserEntity) SessionUtil.getAttribute(SessionConst.LOGIN_USER);
        System.out.println("### userEntity " + userEntity);
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("userEntity", userEntity);
        return "/egovframework/example/user/userList";
    }

    @RequestMapping("/user.do")
    public String user( Model model ,  HttpServletRequest request) {
        HttpSession session = request.getSession();
        request.setAttribute("userId",session.getAttribute("idKey"));
        return "/egovframework/example/user/userList";
    }

    @RequestMapping("/login.do")
    public String login(HttpSession session, Model model , HttpServletRequest request) throws Exception {
        // id와 password를 사용하여 사용자 인증을 수행
        // 예를 들어, 데이터베이스에서 해당 id로 사용자 정보를 조회하고, 비밀번호 일치 여부를 확인하는 로직
        // 인증 성공
        String rt = "";
        try{
            if (true) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(1L);
                userEntity.setUsername("michael"+(long)(Math.random()*10));
                userEntity.setEmail("michael@sangs.co.rk"+(long)(Math.random()*10));
                userEntity.setPassword("1234"+(long)(Math.random()*10));

                System.out.println("### userEntity --> " + userEntity);
                SessionUtil.setAttribute(SessionConst.LOGIN_USER, userEntity); // 세션에 로그인 정보 저장

                //로그인 성공 후 호출
                session = request.getSession();
                MySessionManager.instance().loginProcess(userEntity.getId().toString(), session);


                model.addAttribute("userEntity", userEntity);
                //return "loginHome"; // 로그인 홈 화면으로 이동
                rt =  "/egovframework/example/user/success";
            } else {
                model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
                // return "loginForm"; // 로그인 폼 화면으로 이동
                rt =  "/egovframework/example/user/fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("login.do에서 에러발생");
        }
        return rt;
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletResponse response, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            SessionUtil.removeAttribute(SessionConst.LOGIN_USER);
            session.invalidate(); //세션을 제거한다.
        }
        return "redirect:/";
    }

    /* notifications 알림 조회 */
    @GetMapping("/sse/test.do")
    public String list(CommandMap commandMap , HttpServletResponse response) throws IOException {
       return "egovframework/example/sse/sse";
    }

    /* notifications 알림 추가 */
    @GetMapping("/sse/add.do")
    public String add(CommandMap commandMap , HttpServletResponse response) throws IOException {
        return "egovframework/example/sse/add";
    }



    @GetMapping(value = "/ajax/List.do")
    public  String List(Model model){
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("USER_ID", "AAA");
        map.put("PROG_ID", "KEBA1111");
        map.put("PROG_NAME", "KEBAKE");
        map.put("PHONE_NUM", "010-1111-1111");
        listMap.add(map);

        model.addAttribute("result",listMap);

        return "jsonView";
    }

    @GetMapping("/example")
    public ResponseEntity<String> example(Map data) {
        // 요청 본문의 JSON 데이터가 MyObject로 매핑됨
        // ...

        System.out.println("@@ data --> " + data);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/example2")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> example2(HashMap data) {
        System.out.println("@@ data --> " + data);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("code", "1");
        responseData.put("message", "OK");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // return Map<?,?>
    @GetMapping(value = "/ajax/Map.do")
    public String testList(Model model){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("USER_ID", "AAA");
        map.put("PROG_ID", "KEBA1111");
        map.put("PROG_NAME", "KEBAKE");
        map.put("PHONE_NUM", "010-1111-1111");
        model.addAttribute("result",map);
        return "jsonView";
    }



    public static void main(String[] args) throws NoSuchAlgorithmException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId((long)(Math.random()*10));
        userEntity.setUsername("michael"+(long)(Math.random()*10));
        userEntity.setEmail("michael@sangs.co.rk"+(long)(Math.random()*10));
        userEntity.setPassword("1234"+(long)(Math.random()*10));
        System.out.println(userEntity.toString());

    }


    private static String extractSalt(String encodedPassword) {
        return encodedPassword.substring(0, 10);
    }

    @GetMapping(value = "/body.do")
    @ResponseBody
    public HashMap<String, Object> testCall(HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            result.put("test1", "test1");
            result.put("test2", "test2");
        }catch (Exception e){
            e.printStackTrace();

        }
        return result;
    }


}