package com.web.user.vo.user;

public interface User {
    Long getId();
    String getUsername();
    String getPassword();
    String getEmail();
    // ... 공통 메서드
}