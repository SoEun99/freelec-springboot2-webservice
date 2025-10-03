package com.soeun.book.springboot.web.dto;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void lombok_Test() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

//assertThat
// · assertj라는 테스트 검증 라이브러리의 검증 메소드
// · 검증하고 싶은 대상을 메소드 인자로 받음
// · 메소드 체이닝이 지원되어 isEqualsTo와 같이 메소드를 이어서 사용 가능

//isEqualTo
// · assertj의 동등 비교 메소드
// · assertThat의 값과 isEqualTo의 값을 비교해 같을 때만 성공

// * Junit의 기본 assertThat이 아닌 assertj의 assertThat 사용 장점
//  · CoreMatchers 같은 추가적인 라이브러리(is()) 필요 없음
//  · 자동완성 지원도가 좋음. IDE에서는 Matcher 라이브러리의 자동완성 지원이 약함