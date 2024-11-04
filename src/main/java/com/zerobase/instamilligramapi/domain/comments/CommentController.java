package com.zerobase.instamilligramapi.domain.comments;

import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "전체 답글 조회 API", description = """
                    특정 댓글의 답글 리스트를 조회.
                    <pre>
                    [
                      {
                        "createdAt": 댓글 생성일자
                        "updatedAt": 댓글 변경일자
                        "commentId": 댓글 ID
                        "postId": 게시글 ID
                        "createdBy": 댓글 작성자
                        "profilePictureUrl": 댓글 작성자 프로필 URL
                        "commentText": 댓글 내용
                        "parentId": 상위 댓글 ID (댓글은 null, 답글은 상위 댓글의 ID)
                        "likeCount": 댓글 좋아요 수
                        "liked": 댓글 좋아요 여부
                        "likedAt": 댓글 좋아요 누른 시간
                        "replyCount": 답글 수
                      }
                    ]
                    </pre>
                    """)
    @ApiResponse(responseCode = "200", description = "답글 조회 성공")
    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentOut>> getRepliesByCommentId(HttpServletRequest request, @PathVariable int commentId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        List<CommentOut> replies = commentService.selectRepliesByCommentId(commentId, requester);
        return ResponseEntity.ok(replies);
    }

    @Operation(summary = "댓글 좋아요 API", description = "댓글 좋아요 누르기")
    @PostMapping("/{commentId}/like")
    public ResponseEntity<BaseResponse> createCommentLike(HttpServletRequest request, @PathVariable Integer commentId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        commentService.insertCommentLike(requester, commentId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @Operation(summary = "댓글 좋아요 취소 API", description = "댓글 좋아요 취소 누르기")
    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<BaseResponse> deleteCommentLike(HttpServletRequest request, @PathVariable Integer commentId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        commentService.deleteCommentLike(requester, commentId);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
