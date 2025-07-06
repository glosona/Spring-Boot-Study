package com.apiece.springboot_twitter;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        return new ArrayList<>(posts.values());
    }

    // api/posts/1
    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id){
        return posts.get(id);
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postRequest) {
        Post post = posts.get(id);
        Post newPost = post.updateContent(postRequest.content());

        posts.put(id, newPost);

        return newPost;
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        posts.remove(id);
    }
}
