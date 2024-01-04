package com.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class ParameterLogInterceptor extends HandlerInterceptorAdapter  {

    private Logger log = LogManager.getLogger(this.getClass());
    
    @Override
    public boolean preHandle(// 매개변수 Object는 핸들러 정보를 의미한다.
            HttpServletRequest request, HttpServletResponse response,Object obj) throws Exception {
        
    	if(!(obj instanceof HandlerMethod)) return true;    	
        HandlerMethod method = (HandlerMethod)obj;
        
        log.debug("########## LoggerInterceptor 시작  ##########");
        log.debug("");
        log.debug("#### bean :: " + method.getBean());
        log.debug("#### method :: " + method.getMethod().getName());
        log.debug("#### getRequestURI :: " + request.getRequestURI());
        log.debug("#### getRequestURL :: " + request.getRequestURL());
        log.debug("");
        Enumeration params = request.getParameterNames();
        log.debug("---------------------------- 파라미터 시작  ----------------------------");
        while (params.hasMoreElements()){
            String name = (String)params.nextElement();
            log.debug(name + " : " +request.getParameter(name));
        }
        log.debug("---------------------------- 파라미터 종료  ----------------------------");
        log.debug("");
        /*log.debug("[preHandle][" + request + "]"  + "[" + request.getMethod()
        + "]" + request.getRequestURI() + getParameters(request));*/
        
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
    
    private String getParameters(HttpServletRequest request) {
        StringBuffer posted = new StringBuffer();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        while (e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr + "=");
            if (curr.contains("password") 
              || curr.contains("pass")
              || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }
        String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (ipAddr!=null && !ipAddr.equals("")) {
            posted.append("&_psip=" + ipAddr); 
        }
        return posted.toString();
    }
    
    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
        	log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

}
