package com.stn.excpt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView Exception(HttpServletRequest req, Exception ex) {
        return createErrorModelAndView(req.getRequestURI() , "Exception [msg] : " + ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView NoHandlerFoundException(HttpServletRequest req, Exception ex) {
        return createErrorModelAndView(req.getRequestURI() , "NoHandlerFoundException [msg] : " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(HttpServletRequest req, Exception ex) {
        return createErrorModelAndView(req.getRequestURI() , "RuntimeException [msg] : " + ex.getMessage());

    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(HttpServletRequest req , SQLException ex) {
        return createErrorModelAndView(req.getRequestURI() , "SQLException [msg] : " + ex.getMessage());
    }

    private ModelAndView createErrorModelAndView(String reqUrl,String errorMessage) {
        log.error("reqUrl :  {}.", reqUrl);
        log.error( errorMessage);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",reqUrl);
        modelAndView.addObject("error", errorMessage);
        modelAndView.setViewName("/error/error"); // 에러 페이지의 뷰 이름을 설정
        return modelAndView;
    }

}