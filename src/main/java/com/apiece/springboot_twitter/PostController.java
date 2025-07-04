package com.apiece.springboot_twitter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PostController {

//    @GetMapping("/posts")
//    public String getPosts() {
//        return "안녕하세요.";
//    }

    @GetMapping("/posts")
    public Post getPosts() {
        return new Post(1L, "안녕하세요.", LocalDateTime.now());
    }
}
