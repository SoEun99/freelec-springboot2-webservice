package com.soeun.book.springboot.service.posts;

import com.soeun.book.springboot.domain.posts.Posts;
import com.soeun.book.springboot.domain.posts.PostsRepository;
import com.soeun.book.springboot.web.dto.PostsResponseDto;
import com.soeun.book.springboot.web.dto.PostsSaveRequestDto;
import com.soeun.book.springboot.web.dto.PostsUpdateRequestDto;
import com.soeun.book.springboot.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();

    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;

    }


    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(Posts -> new PostsListResponseDto(Posts))
                .collect(Collectors.toList());

    }

}

//@RequiredArgsConstructor
// · final이 선언 된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성
// · 생성자를 안쓰고 롬복 어노테이션을 쓰는 이유는 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속 수정하는 번거로움 해결

//특이점. update 기능 중 DB에 쿼리를 날리는 부분이 없음
// · JPA의 "영속성 컨텍스트" 덕분에 가능
// · 영속성 컨텍스트 : 엔티티를 영구 저장하는 환경, 일종의 논리적 개념
// · JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림
// · JPA의 Entity Manager가 활성화된 상태(Spring Data Jpa의 default 옵션)로
//   트랜잭션 안에서 DB의 데이터를 가져오면 해당 데이터는 영속성이 유지된 상태
// · 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영함
//   즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요 없음 = 더티 체킹(dirty checking)

//findAllDesc 메소드의 트랜잭션 어노테이션에 readOnly=true 옵션이 하나 추가됨
// · 트랜잭션 범위를 유지하되, 조회 기능만 남겨두어서 조회속도를 개선할 수 있음
// · 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드라면 사용을 추천!