package com.soeun.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

        GUEST("ROLE_GUEST", "손님"),
        USERS("ROLE_USERS", "일반 사용자");

        private final String key;
        private final String title;

}

//Spring Security
// · 권한 코드에 항상 ROLE_이 앞에 있어야 함 Ex. ROLE_GUEST, ROLE_USER