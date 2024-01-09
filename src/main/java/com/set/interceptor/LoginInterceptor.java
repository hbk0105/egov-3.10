package com.set.interceptor;

import com.set.util.SessionConst;
import com.set.util.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor extends HandlerInterceptorAdapter  {

    private Logger log = LogManager.getLogger(this.getClass());

    @Value("${Globals.login.page}")
    String login;

    @Override
    public boolean preHandle(// 매개변수 Object는 핸들러 정보를 의미한다.
            HttpServletRequest request, HttpServletResponse response,Object obj) throws Exception {
        
    	if(!(obj instanceof HandlerMethod)) return true;    	
        HandlerMethod method = (HandlerMethod)obj;
        
        log.debug("########## LoggerInterceptor 시작  ##########");

        System.out.println("## login --> " + login);
        if(SessionUtil.getAttribute(SessionConst.LOGIN_USER) == null){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            //out.println("<script>alert('로그인이 필요합니다.');window.location.href='"+login+"';</script>");
            out.println("<script>alert('로그인이 필요합니다.');window.location.href='/';</script>");

            out.flush();
            //response.sendRedirect("/");
            return false;
        }


        
        log.debug("########## LoggerInterceptor 종료  ##########");
        
        return true; // 반환이 false라면 controller로 요청을 안함
    }

    // postHandel() 메소드
    // controller의 handler가 끝나면 처리됨
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {
    }

    // afterCompletion() 메소드
    // view까지 처리가 끝난 후에 처리됨
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response,Object obj, Exception e)throws Exception {
    	
    }


}
