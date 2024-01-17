package com.web.sample.cntrl;

import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class SampleController {

    private Logger log = LogManager.getLogger(this.getClass());


    @RequestMapping("/sample/main.do")
    public String main(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status) throws Exception {
        return "/sample/main";
    }


    @RequestMapping("/sample/login.do")
    public String login(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status) throws Exception {
        return "/sample/login";
    }


    @GetMapping("/sample/hello")
    public String hello() {
        // 예외 발생 시뮬레이션
        throw new RuntimeException("서버에서 예외가 발생했습니다.");
    }


}
