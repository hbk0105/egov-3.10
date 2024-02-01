package com.stn.xss;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    // 주소 마스킹(신주소, 구주소, 도로명 주소 숫자만 전부 마스킹)
    public static String addressMasking(String address) throws Exception {
        // 신(구)주소, 도로명 주소
        String regex = "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|";
        String newRegx = "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))";

        Matcher matcher = Pattern.compile(regex).matcher(address);
        Matcher newMatcher = Pattern.compile(newRegx).matcher(address);
        if(matcher.find()) {
            return address.replaceAll("[0-9]", "*");
        } else if(newMatcher.find()) {
            return address.replaceAll("[0-9]", "*");
        }
        return address;
    }
    public static void main(String[] args) throws Exception {
        // 테스트

        System.out.println("Mobile: " + maskAs("0101234567","mobile"));
        System.out.println("Namer : " + maskAs("오바마","name"));
        System.out.println("Email : " + maskAs("abcd1234@gmail.com","email"));
        System.out.println("Email : " +  maskAs("1004865000012","accountNo"));
        System.out.println("Address : " +  maskAs("서울특별시송파구미성2차아파트43동201호","addr"));


    }
    // https://aldrn29.tistory.com/205

    public static String maskAs(String value, String maskingType ) {
        if (StringUtils.isEmpty(value) || value.trim().length() == 0) return value;

        String maskingValue = value;
        switch (maskingType) {
            case "name":
                if (value.length() == 2)
                    maskingValue = "*"+value.substring(1);
                else if (value.length() == 3)
                    maskingValue = value.substring(0,1)+"*"+value.substring(2);
                else if (value.length() >= 4)
                    maskingValue = value.substring(0,1)+"**"+value.substring(3);
                break;
            case "mobile":
                // 정규식: 10자리 또는 11자리의 숫자 형태의 전화번호
                String regex = "(\\d{3})(\\d{3,4})(\\d{4})";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                if (matcher.matches()) {
                    // 그룹으로 나누어서 마스킹 처리
                    String maskedPhoneNumber = matcher.group(1) + "-" + matcher.group(2) + "-****";
                    maskingValue = maskedPhoneNumber;
                } else {
                    // 일치하지 않으면 그대로 반환
                    maskingValue = value;
                }
                break;
            case "email":
                /*if (!value.substring(0).equals("@") && value.contains("@")){
                    String emailValue = value.split("@")[0];
                    if (emailValue.length() == 1 || emailValue.length() == 2)
                        maskingValue = (emailValue.length() == 1) ? value.replaceAll("(?<=.{0}).(?=.*@)", "*") : "*"+ emailValue.substring(1,2)+"@"+value.split("@")[1];
                    else if (emailValue.length() == 3 || emailValue.length() == 4)
                        maskingValue = (emailValue.length() == 3) ? value.replaceAll("(?<=.{1}).(?=.*@)", "*") : emailValue.substring(0,1)+ "**" + emailValue.substring(3)+"@"+ value.split("@")[1];
                    else if (emailValue.length() > 4)
                        maskingValue = emailValue.substring(0,1)+ "***" + emailValue.substring(4)+"@"+ value.split("@")[1];
                }*/
                if (value.length() >= 3) {
                    maskingValue = value.replaceAll("(?<=.{3}).(?=.*@)", "*");
                } else {
                    maskingValue = value;
                }
                break;
            case "addr":
                // 신(구)주소, 도로명 주소
                 regex = "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|";
                String newRegx = "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))";

                 matcher = Pattern.compile(regex).matcher(value);
                Matcher newMatcher = Pattern.compile(newRegx).matcher(value);
                if(matcher.find()) {
                    maskingValue = maskingValue.replaceAll("[0-9]", "*");
                } else if(newMatcher.find()) {
                    maskingValue = maskingValue.replaceAll("[0-9]", "*");
                }
                break;
            case "accountNo":
                if (maskingValue.length() >= 7) {
                    maskingValue = maskingValue.replaceAll("(?<=.{4}).(?=.{2})", "*");
                } else {
                    maskingValue = value;
                }
                break;

        }
        return maskingValue;
    }
}
