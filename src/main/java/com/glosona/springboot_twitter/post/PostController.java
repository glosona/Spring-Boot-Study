package com.glosona.springboot_twitter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody Post post) {
        Post newPost = new Post(null, post.getContent(), LocalDateTime.now());

        postRepository.save(newPost);

        return newPost;
    }

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // api/posts/1
    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postRequest) {
        return postRepository.findById(id)
                .map(post -> {
                    post.updateContent(postRequest.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow();
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    // /api/posts/search?page=1&size=3
    @GetMapping("/api/posts/search")
    public Page<Post> searchPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return postRepository.findAll(pageable);
    }
}
