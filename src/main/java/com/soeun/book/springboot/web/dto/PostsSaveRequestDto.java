package com.soeun.book.springboot.web.dto;

import com.soeun.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;
    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {

        this.title = title;
        this.content = content;
        this.author = author;

    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}

//★ 꼭 Entity 클래스와 Controller에서 쓸 Dto는 분리해서 사용해야 함
//Entity 클래스와 거의 유사한 형태임에도 Dto 클래스를 추가로 생성했음
//절대로 Entity 클래스를 Request/Response 클래스로 사용하면 안됨
//이유. Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스임. Entity 클래스를 기준으로 테이블이 생성되고, 스키마가 변경 됨
//      화면 변경은 사소한 변경인데, 이를 위해 테이블과 관련된 Entity 클래스를 변경하는 것은 너무 큰 변경임
//      수많은 서비스 클래스, 비즈니스 로직이 Entity 클래스를 기준으로 동작함 특히
//      Request와 Response는 View를 위한 클래스라 정말 자주 변경이 필요함 = View Layer와 DB Layer의 역할 분리를 철저히 하는 게 좋음