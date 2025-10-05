package com.soeun.book.springboot.domain.posts;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Posts {
// 특이점. 본 Posts 클래스에는 Setter 메소드가 없음
    // 자바빈 규약을 생각하며 getter/setter를 무작정 생성하는 경우, 인스턴스 값이 언제 어디서 변하는지 코드상으로 구분할 수가 없음
    // 따라서 Entity 클래스에서는 절대 Setter 메소드를 만들지 않음
    // 대신 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가함
    // 생성자를 통해 최종 값을 채운 후, DB에 삽입함. 값 변경이 필요하다면 해당 이벤트에 맞는 public 메소드를 호출해서 변경
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

//@Lombok Annotation
// · 코드를 단순화 시켜주며, 필수 어노테이션은 아님

//@Entity
// · 테이블과 링크될 클래스임을 나타냄
// · 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭
// · ex.SalesManager.java → sales_manager table

//@Id
// · 해당 테이블의 PK 필드를 나타냄

//@GeneratedValue
// · PK의 생성 규칙을 나타냄
// · 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됨

//@Column
// · 테이블의 칼럼을 나타냄. 굳이 선헌하지 않아도 해당 클래스 필드는 모두 칼럼이 됨
// · 사용하는 이유는, 기본 값 외에 추가 변경 필요한 옵션이 있을 경우
//Ex. 문자열의 경우 VARCHAR(255)가 기본값으로 사이즈를 500으로 늘리고 싶은 경우

//@NoArgsConstructor
// · 기본 생성자 자동 추가
// · public Posts() {}와 같은 효과

//@Getter
// · 클래스 내 모든 필드의 Getter 메소드를 자동 생성

//@Builder
// · 해당 클래스의 빌더 패턴 클래스를 생성
// · 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함