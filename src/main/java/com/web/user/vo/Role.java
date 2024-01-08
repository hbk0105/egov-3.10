package com.web.user.vo;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Role {
    private Long id;
    private String name;
    private Set<Menu> menus = new HashSet<>();

    // getters, setters, 추가 메서드 등
}
