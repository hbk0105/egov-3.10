package com.set.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


public class CustomLoginFailureHandler implements AuthenticationFailureHandler{

	private static final Logger logger = LoggerFactory.getLogger(CustomLoginFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		/*
		logger.info(exception.getLocalizedMessage());
		logger.info(exception.getMessage());
		logger.info(request.getParameter("passwd"));
		for(StackTraceElement s : exception.getStackTrace()){
			logger.info(s.getClassName());
			logger.info(s.getFileName());
			logger.info(s.getMethodName());
			logger.info(s.getLineNumber()+"");
			logger.info(s.isNativeMethod()+"");
		}
		*/
		
		if (exception instanceof AuthenticationServiceException) {
			request.setAttribute("msg", "존재하지 않는 사용자입니다.");
		
		} else if(exception instanceof BadCredentialsException) {
			request.setAttribute("msg", "아이디 또는 비밀번호가 틀립니다.");
		} else if(exception instanceof LockedException) {
			request.setAttribute("msg", "잠긴 계정입니다..");
			
		} else if(exception instanceof DisabledException) {
			request.setAttribute("msg", "비활성화된 계정입니다..");
			
		} else if(exception instanceof AccountExpiredException) {
			request.setAttribute("msg", "만료된 계정입니다..");
			
		} else if(exception instanceof CredentialsExpiredException) {
			request.setAttribute("msg", "비밀번호가 만료되었습니다.");
		}

		request.setAttribute("returnUrl","/customLogin.do");
		request.getRequestDispatcher("/common/msgForward.do").forward(request, response);
	}
	
	
	
}
