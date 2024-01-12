package com.stn.util;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ReplaceCharsTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;

    private String inputString;

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (inputString != null) {
                String replacedString = inputString.replaceAll("&amp;lt;", "&#60;")
                        .replaceAll("&amp;gt;", "&#62;");
                pageContext.getOut().print(replacedString);
            }
        } catch (Exception e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}