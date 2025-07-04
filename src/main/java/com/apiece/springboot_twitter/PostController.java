package com.apiece.springboot_twitter;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PostController {

    private final Map<Long, Post> posts = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody Post post){
        long newId = idGenerator.getAndIncrement();
        Post newPost = new Post(newId, post.content(), LocalDateTime.now());

        posts.put(newId, newPost);

        return newPost;
    }

    // api/posts/1
    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id){
        return posts.get(id);
    }
}
