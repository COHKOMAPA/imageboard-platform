package com.cohkomapa.imageboard.board.controller;

import com.cohkomapa.imageboard.board.dto.post.*;
import com.cohkomapa.imageboard.board.service.PostService;
import com.cohkomapa.imageboard.board.validator.PostValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostValidator postValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDetailsDto createPost(
            @RequestBody @Valid PostCreateDto postCreateDto
    ) {
        return postService.createPost(postCreateDto);
    }

    @GetMapping
    public Page<PostShortDto> getPosts(
            @ParameterObject PostFilter postFilter,
            @ParameterObject
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        postValidator.validateSort(pageable);
        return postService.getPostsByFilter(postFilter, pageable);
    }

    @GetMapping("/{postId}")
    public PostDetailsDto getPostById(@PathVariable UUID postId) {
        return postService.getById(postId);
    }

    @PutMapping("/{postId}")
    public PostDetailsDto updatePostById(
            @PathVariable UUID postId,
            @RequestBody @Valid PostUpdateDto postUpdateDto
    ) {
        return postService.updateById(postId, postUpdateDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
    }
}