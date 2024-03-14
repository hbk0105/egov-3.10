package com.stn.interceptor;

import com.stn.util.*;
import com.web.user.vo.user.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor extends HandlerInterceptorAdapter  {

    private Logger log = LogManager.getLogger(this.getClass());

    @Value("${Globals.login.page}")
    String login;

    @Override
    public boolean preHandle(// 매개변수 Object는 핸들러 정보를 의미한다.
            HttpServletRequest request, HttpServletResponse response,Object obj) throws Exception {

        boolean re = true;

    	if(!(obj instanceof HandlerMethod)) return true;    	
        HandlerMethod method = (HandlerMethod)obj;
        
        log.debug("########## LoggerInterceptor 시작  ##########");

        HttpSession session = request.getSession();

        //if(session.getAttribute("idKey") == null){
        if(SessionUtil.getAttribute(SessionConst.LOGIN_USER) == null){
            new PrintWiterUtil().cmmnMsg(response,"로그인이 필요합니다.");
            re = false;
            return false;
        }

        if(session.getAttribute("idKey") != null && MySessionManager.instance().sessionIdDuplCheck(session.getAttribute("idKey").toString(), session)) {
            new PrintWiterUtil().cmmnMsg(response,"중복로그인으로 로그아웃 되었습니다.");
            re = false;
            return false;
        }

        if( re ) {

            /* CSRF 대응 로직 시작 */

            /* 보통은 아래 로직으로 충분히 가능, 추가로 진행 하고 싶을 경우, csrf token 사용 */
            /*
            String referer = request.getHeader("Referer"); // 요청을 보내기 전 페이지
            String host = request.getHeader("host"); // 도메인 호스트 (ex : www.naver.com)
            if (referer == null || !referer.contains(host)) {
                new PrintWiterUtil().cmmnMsg(response,"CSRF 공격이 감지되었습니다.");
                re = false;
            }

            String headerToken = request.getHeader("X-CSRF-HEADER");
            String paramToken = request.getParameter("CSRF_TOKEN");
            String cookieToken = null;
            for (Cookie cookie : request.getCookies()) {
                if ("CSRF_TOKEN".equals(cookie.getName())) { // 쿠키로 전달되 csrf 토큰 값
                    cookieToken = cookie.getValue();
                    // 재사용이 불가능하도록 쿠키 만료
                    cookie.setPath("/");
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
            // 쿠키 token 과 (파라미터 토큰 or 헤더 토큰)을 비교
            if (cookieToken == null || (!cookieToken.equals(headerToken) && !cookieToken.equals(paramToken)))  {
                new PrintWiterUtil().cmmnMsg(response,"CSRF 공격이 감지되었습니다.");
                re = false;
            }
            */
            /* CSRF 대응 로직 종료 */

            log.debug("########## LoggerInterceptor 종료  ##########");
        }
        System.out.println("## re :: " + re);

        return re; // 반환이 false라면 controller로 요청을 안함
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
