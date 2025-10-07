package com.soeun.book.springboot.web;

import com.soeun.book.springboot.config.auth.dto.SessionUser;
import com.soeun.book.springboot.service.posts.PostsService;
import com.soeun.book.springboot.web.dto.PostsResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
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

//(SessionUser) httpSession.getAttribute("user")
// · 앞서 작성된 CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성
// · 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있음

//if(user != null)
// · 세션에 저장된 값이 있을 때만 model에 userName으로 등록함
// · 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이므로,'로그인' 버튼이 보이게 됨