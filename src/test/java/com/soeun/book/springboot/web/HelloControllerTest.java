package com.soeun.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //
@WebMvcTest(controllers = HelloController.class) //
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello_test() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }
    @Test
    public void helloDto_test() throws Exception {
        String name = "hello";
        int amount = 1000;
        mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}


//-----------------------------------------------------

//@RunWith(SpringRunner.class)
// · 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자 실행
// · 스프링 부트 테스트와 JUnit 사이의 연결자 역할

//@WebMvcTest
// · Web(Spring MVC)에 집중할 수 있는 어노테이션
// · 선언할 경우 @Controller, @ControllerAdvice 등 사용 가능
// · 단, @Service, @Component, @Repository 등은 사용 불가능

//@AutoWired
// · 스프링이 관리하는 빈(Bean)을 주입받음

//-----------------------------------------------------

// param
// · API 테스트 시 사용될 요청 파라미터를 설정, 단 값은 String만 허용
// · 따라서 숫자, 날짜 등 데이터도 등록 시에는 '문자열'로 변경해야만 가능

// jsonPath
// · JSON 응답값을 필드별로 검증가능한 메소드
// · $를 기준, 필드명을 명시


//private MockMvc mvc
// · 웹 API 테스트 시 사용, 이 클래스로 HTTP GET, POST 등에 대한 API 테스트 가능
// · 스프링 MVC 테스트의 시작점

//mvc.perform(get("/hello"))
// · Mockmvc를 통해 /hello 주소로 HTTP GET 요청
// · 체이닝이 지원되어 여러 검증 기능을 이어서 선언 가능

//.andExpect(status().isOk())
// · mvc.perform의 결과를 검증함
// · HTTP Header의 Status를 검증함
// · 우리가 흔히 알고 있는 200, 404, 500 등의 상태를 검증

//.andExpect(content().string(hello))
// · mvc.perform의 결과를 검증함
// · 응답 본문의 내용을 검증함
// · Controller에서 "hello"를 리턴하기 때문에, 이 값이 맞는지 검증함