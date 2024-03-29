package com.stn.security;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	
	private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
	
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res,
			AccessDeniedException ade) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		logger.info("Exceiption : {}",ade);
		logger.info("LocalizedMessage : {}",ade.getLocalizedMessage());
		logger.info("Message : {}",ade.getMessage());
		logger.info("StackTrace : {}",ade.getStackTrace());
		req.setAttribute("errMsg",ade.getMessage());
		req.getRequestDispatcher("/accessError.do").forward(req, res);
	}


}