package com.set.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SessionUtil {

    public static void setAttribute(String name, Object value) {
        RequestContextHolder.getRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    public static void removeAttribute(String name) {
        RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }


    public static Object getAttribute(String name) {
        return RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    public static boolean isSessionExist(String name) {
        return RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION) != null;
    }

    public static String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
}