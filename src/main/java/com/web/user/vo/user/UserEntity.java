package com.web.user.vo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class UserEntity implements User{

    private Long id;
    private String username;
    private String password; // 보안상의 이유로 실제 데이터베이스에는 암호화된 비밀번호를 저장합니다.
    private String email;
    // 기타 필드 (예: 이름, 프로필 사진 URL 등)

    // Getter, Setter, 생성자 등
    // ...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                // 기타 필드 출력
                '}';
    }
}
