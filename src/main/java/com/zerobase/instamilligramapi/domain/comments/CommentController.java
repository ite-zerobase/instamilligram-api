package com.zerobase.instamilligramapi.domain.comments;

import com.zerobase.instamilligramapi.domain.comments.dto.CommentIn;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentOut> createComment(@RequestBody @Valid CommentIn commentIn) {
        CommentOut commentOut = commentService.insertComment(commentIn);
        return ResponseEntity.ok(commentOut);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentOut> getCommentByCommentId(@PathVariable Integer commentId) {
        CommentOut comment = commentService.selectCommentByCommentId(commentId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentOut>> getRepliesByCommentId(@RequestHeader("current-username") String currentUsername, @PathVariable int commentId) {
        List<CommentOut> replies = commentService.selectRepliesByCommentId(commentId, currentUsername);
        return ResponseEntity.ok(replies);
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<BaseResponse> createCommentLike(@RequestHeader("current-username") String currentUsername, @PathVariable Integer commentId) {
        commentService.insertCommentLike(currentUsername, commentId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<BaseResponse> deleteCommentLike(@RequestHeader("current-username") String currentUsername, @PathVariable Integer commentId) {
        commentService.deleteCommentLike(currentUsername, commentId);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
