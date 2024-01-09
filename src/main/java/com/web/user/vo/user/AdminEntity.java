package com.web.user.vo.user;

import com.web.user.vo.Menu;
import com.web.user.vo.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class AdminEntity extends UserEntity {

    // 관리자 특정 필드..
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public boolean hasMenuAccess(Menu menu) {
        for (Role role : roles) {
            if (role.getMenus().contains(menu)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasWritePermission() {
        // 로그인한 관리자에 대한 글 쓰기 권한 체크 로직
        // 예: roles 내에서 해당 권한을 가진 Role 객체가 있는지 확인
        return false; // 예시 코드, 실제 로직은 추가 구현 필요
    }

}
