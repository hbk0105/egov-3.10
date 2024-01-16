package com.web.user.cntrl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

public class UserController {


    private Logger log = LogManager.getLogger(this.getClass());

    @RequestMapping("/user/List.do")
    public String memberList(){

        return "/user/userList";
    }
}
