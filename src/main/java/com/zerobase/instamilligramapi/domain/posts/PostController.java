package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.posts.dto.PostIn;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.dto.Paging;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import jakarta.validation.Valid;
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
    public ResponseEntity<PostOut> createPost(@RequestBody @Valid PostIn post) {
        PostOut postOut = postService.insertPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOut);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostOut> getPost(@RequestHeader("current-username") String currentUsername, @PathVariable Integer postId) {
        PostOut postOut = postService.selectPost(postId, currentUsername);
        return ResponseEntity.ok(postOut);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> createPostLike(@RequestHeader("current-username") String currentUsername, @PathVariable Integer postId) {
        postService.insertPostLike(postId, currentUsername);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> deletePostLike(@RequestHeader("current-username") String currentUsername, @PathVariable Integer postId) {
       postService.deletePostLike(postId, currentUsername);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping("")
    public ResponseEntity<List<PostOut>> getManyPost(Paging page) {
        List<PostOut> postOuts = postService.selectManyPost(page);
        return ResponseEntity.ok(postOuts);
    }
}
