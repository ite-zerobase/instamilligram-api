package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.comments.CommentService;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentIn;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentSearch;
import com.zerobase.instamilligramapi.domain.posts.dto.PostIn;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
import com.zerobase.instamilligramapi.global.dto.BaseResponse;
import com.zerobase.instamilligramapi.global.dto.Paging;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final JwtUtil jwtUtil;


    @Operation(summary = "여러 게시글 보기 API", description = """
        게시글 다중 조회. <br>
        세부내역은 게시글 조회 API 참고"""
    )
    @GetMapping("")
    public ResponseEntity<List<PostOut>> getManyPost(HttpServletRequest request, @Valid @ParameterObject Paging page) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        page.setRequestingUser(requester);
        List<PostOut> postOuts = postService.selectManyPost(page);
        return ResponseEntity.ok(postOuts);
    }

    @Operation(summary = "게시글 만들기 API", description = "게시글 내용, 장소, 사진을 업로드")
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostOut> createPost(HttpServletRequest request, @ModelAttribute @Valid PostIn post) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        post.setUsername(requester);
        PostOut postOut = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postOut);
    }

//    @PostMapping(value="/{postId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadPostImages(
//            @PathVariable Integer postId,
//            @Parameter(description = "업로드할 이미지 파일") @RequestParam List<MultipartFile> images) {
//        List<PostMediaOut> postOutList = postService.uploadPostMedia(postId, images);
//        return ResponseEntity.status(HttpStatus.CREATED).body(postOutList);
//    }

    @Operation(summary = "게시글 상세보기 API", description = """
            게시글 조회 <br>
            현재 로그인한 사용자 기준으로 좋아요, 팔로잉 여부 확인 <br>
            <pre>
              "createdAt": 게시글 생성일자,
              "updatedAt": 게시글 변경일자,
              "postId": 게시글 ID,
              writer: {
                "username": 게시글 작성자명,
                "following": 게시글 작성자 팔로잉 여부,
                "profilePictureUrl": 게시글 작성자 프로필 사진 URL
              },
              "postContent": 게시글 내용,
              "place": 장소,
              "likeCount": 게시글 좋아요 수,
              "commentCount": 게시글 댓글 수,
              "liked": 게시글 좋아요 여부,
              "likedAt": 좋아요 누른 시간,
              "media": [
                {
                  "mediaUrl": 이미지 이름,
                  "mediaSeq": 이미지 순번
                }
              ]
              </pre>
            """
            )
    @GetMapping("/{postId}")
    public ResponseEntity<PostOut> getPost(HttpServletRequest request,
                                           @Schema(description = "게시글 ID") @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        PostOut postOut = postService.selectPost(postId, requester);
        return ResponseEntity.ok(postOut);
    }

    @Operation(summary = "전체 댓글 조회 API", description = """
            게시글의 댓글을 전체 조회 <br>
            <pre>
                [
                  {
                    "createdAt": 댓글 생성일자
                    "updatedAt": 댓글 변경일자
                    "commentId": 댓글 ID
                    "postId": 게시글 ID
                    "createdBy": 댓글 작성자
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
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentOut>> getManyComment(HttpServletRequest request,
                                                           @Schema(description = "게시글 ID") @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        CommentSearch commentSearch = CommentSearch.fromPostId(postId, requester);
        List<CommentOut> comments = commentService.selectCommentsByCommentSearch(commentSearch);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 작성 API", description = """
            게시글에 댓글 작성 <br><br>
            Path
            - postID (필수): 게시글 ID
            
            Request Body
            - commentText (필수): 댓글 내용
            - parentId: 상위 댓글 ID. (댓글일 경우 null, 답글일 경우 답글을 작성중인 댓글 ID 입력)
            """)
    @PostMapping(value = "/{postId}/comments")
    public ResponseEntity<CommentOut> createComment(@RequestBody @Valid CommentIn commentIn,
                                                    @Schema(description = "게시글 ID") @PathVariable Integer postId,
                                                    HttpServletRequest request
    ) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        commentIn.setPostId(postId);
        commentIn.setUsername(requester);
        CommentOut commentOut = commentService.insertComment(commentIn);
        return ResponseEntity.ok(commentOut);
    }

    @Operation(summary = "게시글 좋아요 API", description = "게시글 좋아요 누르기")
    @PostMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> createPostLike(HttpServletRequest request, @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
        postService.insertPostLike(postId, requester);
        return ResponseEntity.ok(BaseResponse.success());
    }

    @Operation(summary = "게시글 좋아요 취소 API", description = "게시글 좋아요 취소 누르기")
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> deletePostLike(HttpServletRequest request, @PathVariable Integer postId) {
        String requester = jwtUtil.extractUsernameFromRequest(request);
       postService.deletePostLike(postId, requester);
        return ResponseEntity.ok(BaseResponse.success());
    }

}
