package com.soeun.book.springboot.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}

// Mustache Starter
// · 앞의 경로와 뒤의 파일 확장자를 자동으로 지정함
// · "index"를 src/main/resources/templates/index.mustache로 전환 되어 View Resolver가 처리 함