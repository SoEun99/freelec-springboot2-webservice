package com.soeun.book.springboot.domain.user;

import com.soeun.book.springboot.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Users(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Users update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}

//@Enumerated(EnumType.STRING)
// · JPA로 데이터베이스를 저장할 때, ENUM 값을 어떤 형태로 저장할지 결정 (기본 : int)
// · 숫자로 저장되면 DB 확인 시 그 값이 무슨 코드인지 알 수가 없음
// · 그래서 문자열(EnumType.STRING)으로 저장되도록 선언