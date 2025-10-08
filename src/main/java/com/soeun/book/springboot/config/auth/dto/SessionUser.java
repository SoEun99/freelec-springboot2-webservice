package com.soeun.book.springboot.config.auth.dto;

import com.soeun.book.springboot.domain.user.Users;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users users) {
        this.name = users.getName();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }
}

//SessionUser
// · 인증 된 사용자 정보만 필요함
// · [중요] 만약 Users 클래스를 그대로 사용했다면 "직렬화를 구현하지 않았다" 오류가 발생함
//      오류를 해결하고자 Users 클래스에 직렬화 코드를 넣는 경우, Users 클래스가 엔티티이기 때문에 고려사항이 많음
//      엔티티 클래스는 언제 다른 엔티티와 관계가 형성될 지 모름
//      @OneToMany, @ManyToMany 등 자식 엔티티를 가진다면 자식들도 직렬화해야하는 성능이슈 발생