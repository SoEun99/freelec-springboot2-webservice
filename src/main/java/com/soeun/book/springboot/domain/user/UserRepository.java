package com.soeun.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

}

//@findByEmail
// · 소셜 로그인으로 반환되는 값 중, email을 통해 이미 생성된 사용자인지 신규가입자인지 판단하는 메소드