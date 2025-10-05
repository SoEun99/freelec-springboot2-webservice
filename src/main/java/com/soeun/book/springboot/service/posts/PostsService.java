package com.soeun.book.springboot.service.posts;

import com.soeun.book.springboot.domain.posts.Posts;
import com.soeun.book.springboot.domain.posts.PostsRepository;
import com.soeun.book.springboot.web.dto.PostsResponseDto;
import com.soeun.book.springboot.web.dto.PostsSaveRequestDto;
import com.soeun.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;


    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;

    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();

    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

}

//@RequiredArgsConstructor
// · final이 선언 된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성
// · 생성자를 안쓰고 롬복 어노테이션을 쓰는 이유는 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속 수정하는 번거로움 해결

//특이점. update 기능 중 DB에 쿼리를 날리는 부분이 없음
// · JPA의 "영속성 컨텍스트" 덕분에 가능
// · 영속성 컨텍스트 : 엔티티를 영구 저장하는 환경, 일종의 논리적 개념
// · JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림