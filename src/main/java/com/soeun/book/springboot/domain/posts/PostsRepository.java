package com.soeun.book.springboot.domain.posts;

//ibatis나 MyBatis 등에서 Dao라고 불리우는 DB Layer 접근자
//JPA에서는 Repository라고 부르며, 인터페이스로 생성
//인터페이스로 생성 후 JpaRepository<Entity 클래스, PK타입>를 상속하면 기본적인 CRUD 메소드가 자동 생성
//@Repository를 추가할 필요도 없음
//주의. "Entity 클래스와 기본 Entity Repository는 함께 위치"해야 함, 도메인 패키지로 관리 가능

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    
}
