package com.soeun.book.springboot.web;

import com.soeun.book.springboot.service.posts.PostsService;
import com.soeun.book.springboot.web.dto.PostsResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
    return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }


}

// Mustache Starter
// · 앞의 경로와 뒤의 파일 확장자를 자동으로 지정함
// · "index"를 src/main/resources/templates/index.mustache로 전환 되어 View Resolver가 처리 함

//Model
// · 서버 템플릿 엔진에서 사용할 수 있는 객체 저장 가능
// · 여기서는 PostsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달