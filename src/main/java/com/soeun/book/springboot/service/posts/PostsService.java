package com.soeun.book.springboot.service.posts;

import com.soeun.book.springboot.domain.posts.PostsRepository;
import com.soeun.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();

    }

}

//@RequiredArgsConstructor
// · final이 선언 된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성
// · 생성자를 안쓰고 롬복 어노테이션을 쓰는 이유는 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속 수정하는 번거로움 해결