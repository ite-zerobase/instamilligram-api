package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.posts.dto.PostIn;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
import com.zerobase.instamilligramapi.global.dto.Paging;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<PostOut> createPost(PostIn post) {
        PostOut postOut = postService.insertPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOut);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostOut> getPost(@PathVariable Integer postId) {
        PostOut postOut = postService.selectPost(postId);
        return ResponseEntity.ok(postOut);
    }

    @GetMapping("")
    public ResponseEntity<List<PostOut>> getManyPost(Paging page) {
        List<PostOut> postOuts = postService.selectManyPost(page);
        return ResponseEntity.ok(postOuts);
    }
}
