package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.comments.CommentService;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentIn;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentSearch;
import com.zerobase.instamilligramapi.domain.posts.dto.PostIn;
import com.zerobase.instamilligramapi.domain.posts.dto.PostMediaOut;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.dto.Paging;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @PostMapping(value="")
    public ResponseEntity<PostOut> createPost(PostIn post,
            @Parameter(description = "업로드할 이미지 파일") @RequestParam List<MultipartFile> images) {
        PostOut postOut = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOut);
    }
    @PostMapping(value="/{postId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPostImages(
            @PathVariable Integer postId,
            @Parameter(description = "업로드할 이미지 파일") @RequestParam List<MultipartFile> images) {
        List<PostMediaOut> postOutList = postService.uploadPostMedia(postId, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOutList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostOut> getPost(HttpServletRequest request, @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        PostOut postOut = postService.selectPost(postId, requester);
        return ResponseEntity.ok(postOut);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> createPostLike(HttpServletRequest request, @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        postService.insertPostLike(postId, requester);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> deletePostLike(HttpServletRequest request, @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
       postService.deletePostLike(postId, requester);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping("")
    public ResponseEntity<List<PostOut>> getManyPost(Paging page) {
        List<PostOut> postOuts = postService.selectManyPost(page);
        return ResponseEntity.ok(postOuts);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentOut>> getManyComment(HttpServletRequest request, @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        CommentSearch commentSearch = CommentSearch.fromPostId(postId, requester);
        List<CommentOut> comments = commentService.selectCommentsByCommentSearch(commentSearch);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentOut> createComment(@RequestBody @Valid CommentIn commentIn, @PathVariable Integer postId) {
        commentIn.setPostId(postId);
        CommentOut commentOut = commentService.insertComment(commentIn);
        return ResponseEntity.ok(commentOut);
    }
}
