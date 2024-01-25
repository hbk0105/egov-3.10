package com.stn.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class AESEncryptionTag extends TagSupport {

    @Autowired
    AESEncryptionUtil aesEncryptionUtil;

    private String value;

    private String mode;

    private String var;

    public void setValue(String value) {
        this.value = value;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            // AESEncryptionUtil은 앞서 작성한 암호화 유틸리티 클래스입니다.

            // encrypt 또는 decrypt 처리
            String result = null;
            if (value != null && mode != null) {
                if("enc".equalsIgnoreCase(mode)){
                    result = aesEncryptionUtil.encrypt(value);
                }else if("dec".equalsIgnoreCase(mode)){
                    result = aesEncryptionUtil.decrypt(value);
                }
            }

            // 결과를 페이지에 출력 또는 변수에 저장
            if (var != null) {
                pageContext.setAttribute(var, result);
            } else {
                pageContext.getOut().print(result);
            }
        } catch (Exception e) {
            throw new JspException(e);
        }

        return Tag.SKIP_BODY;
    }
}