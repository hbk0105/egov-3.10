package com.stn.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintWiterUtil {

    PrintWriter out = null;

    public void cmmnMsg(HttpServletResponse response , String outMsg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('"+outMsg+"');window.location.href='/';</script>");
        out.flush();
    }
}
