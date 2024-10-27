package com.zerobase.instamilligramapi.domain.comments;

import com.zerobase.instamilligramapi.domain.comments.dto.CommentIn;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "답글 조회 API",
            description = """
                    특정 댓글의 답글 리스트를 조회. 
                    <br><br>
                    사용 방법
                    1. 현재 로그인 되어있는 유저의 username을 요청 헤더 requestiong-user 값으로 전달
                    2. /comments/{commentId}/replies 형식으로 
                    
                    commentId로 조회 가능하며, 현재 로그인 되어있는 유저의 username을 requestiong-user로 전달해야 한다. 
                    <br><br>
                    응답값 설명
                    - createdAt: 댓글 생성 시간
                    - updatedAt: 댓글 수정 시간
                    - commentId: 댓글 id
                    - postId: 댓글이 위치한 게시글 id
                    - createdBy: 댓글 작성자 (username)
                    - commentText: 댓글 내용
                    - parentId: 부모 댓글 id (댓글일 경우 null, 답글일 경우 상위 댓글 id) 
                    - likeCount: 댓글 좋아요 수
                    - liked: requestiong-user가 좋아요를 눌렀는지 여부 (true, false)
                    - likedAt: 좋아요 누른 시간
                    - replyCount: 답글 수
                    """)
    @ApiResponse(responseCode = "200", description = "답글 조회 성공")
    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentOut>> getRepliesByCommentId(HttpServletRequest request, @PathVariable int commentId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        List<CommentOut> replies = commentService.selectRepliesByCommentId(commentId, requester);
        return ResponseEntity.ok(replies);
    }

    @Operation(summary = "답글 생성 API",
            description = """
        Retrieves user information by ID.
        This endpoint provides detailed user data including:
        - Personal information
        - Account status
        - Subscription details
        
        Use this endpoint when you need comprehensive user information.
        """
    )
    @PostMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentOut>> createReply(HttpServletRequest request, @RequestBody @Valid CommentIn commentIn, @PathVariable int commentId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        List<CommentOut> replies = commentService.selectRepliesByCommentId(commentId, requester);
        return ResponseEntity.ok(replies);
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<BaseResponse> createCommentLike(HttpServletRequest request, @PathVariable Integer commentId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        commentService.insertCommentLike(requester, commentId);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<BaseResponse> deleteCommentLike(HttpServletRequest request, @PathVariable Integer commentId) {
        String requester = jwtUtil.extractUsernameFromHeader(request);
        commentService.deleteCommentLike(requester, commentId);
        return ResponseEntity.ok(BaseResponse.success());
    }
}
