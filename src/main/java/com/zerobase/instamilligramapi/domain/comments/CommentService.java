package com.zerobase.instamilligramapi.domain.comments;

import com.zerobase.instamilligramapi.domain.comments.dto.CommentIn;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentMeta;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentSearch;
import com.zerobase.instamilligramapi.domain.posts.PostMapper;
import com.zerobase.instamilligramapi.domain.posts.dto.PostIn;
import com.zerobase.instamilligramapi.domain.posts.dto.PostMeta;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
import com.zerobase.instamilligramapi.domain.users.UserMapper;
import com.zerobase.instamilligramapi.domain.users.dto.UserSearch;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    
    public CommentOut selectCommentByCommentId(Integer commentId) {
        return commentMapper.selectCommentByCommentId(commentId)
                .orElseThrow(ZbException.supplier(ErrorCode.COMMENT_NOT_FOUND, "commentId: " + commentId));
    }

    public List<CommentOut> selectRepliesByCommentId(int commentId, String requestingUser) {
        return this.selectCommentsByCommentSearch(CommentSearch.fromCommentId(commentId, requestingUser));
    }
    public List<CommentOut> selectCommentsByPost(PostIn postIn) {
        return this.selectCommentsByCommentSearch(CommentSearch.fromPostId(postIn.getPostId(), postIn.getRequestingUser()));
    }

    public List<CommentOut> selectCommentsByCommentSearch(CommentSearch commentSearch) {
        if (commentSearch.getCommentId() == null && commentSearch.getPostId() == null) {
            throw ZbException.from(ErrorCode.EMPTY_COMMENT_SEARCH_REQUEST);
        }
        if (commentSearch.getCommentId() != null && commentSearch.getPostId() != null) {
            throw ZbException.from(ErrorCode.BAD_COMMENT_SEARCH_REQUEST);
        }
        return commentMapper.selectCommentsByCommentSearch(commentSearch);
    }

    public CommentOut insertComment(CommentIn commentIn) {
        if (commentIn.getParentId() != null && commentIn.getParentId() <= 0) {
            commentIn.setParentId(null);
        }
        userMapper.selectUserByUserSearch(UserSearch.username(commentIn.getUsername()))
                .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND, "username: " + commentIn.getUsername()));
        PostIn postIn = new PostIn();
        postIn.setPostId(commentIn.getPostId());
        postMapper.selectPost(postIn)
                .orElseThrow(ZbException.supplier(ErrorCode.TARGET_POST_NOT_FOUND, "postId: " + commentIn.getPostId()));

        if (commentIn.getParentId() != null) {
            commentMapper.selectCommentByCommentId(commentIn.getParentId())
                    .orElseThrow(ZbException.supplier(ErrorCode.TARGET_COMMENT_NOT_FOUND, "parentId: " + commentIn.getParentId()));
        }
        commentMapper.insertComment(commentIn);

        PostMeta postMeta = new PostMeta();
        postMeta.setPostId(commentIn.getPostId());
        postMeta.setCommentId(commentIn.getCommentId());
        postMeta.setSign(1);
        postMapper.updatePostCommentCount(postMeta);
        if (commentIn.getParentId() != null) {
            CommentMeta commentMeta = new CommentMeta(null, commentIn.getParentId(), 1);
            commentMapper.updateReplyCount(commentMeta);
        }
        return this.selectCommentByCommentId(commentIn.getCommentId());
    }

    public void insertCommentLike(String username, Integer commentId) {
        CommentMeta commentMeta = new CommentMeta(username, commentId, 1);
        commentMapper.selectCommentLike(commentMeta)
                .ifPresent(a -> {
                    throw ZbException.from(ErrorCode.COMMENT_ALREADY_LIKED);
                });
        userMapper.selectUserByUserSearch(UserSearch.username(commentMeta.getUsername()))
                .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND, "username: " + username));
        commentMapper.selectCommentByCommentId(commentMeta.getCommentId())
                .orElseThrow(ZbException.supplier(ErrorCode.TARGET_COMMENT_NOT_FOUND, "commentId: " + commentId));
        commentMapper.insertCommentLike(commentMeta);
        commentMapper.updateCommentLikeCount(commentMeta);
    }
    public void deleteCommentLike(String username, Integer commentId) {
        CommentMeta commentMeta = new CommentMeta(username, commentId, -1);
        commentMapper.selectCommentLike(commentMeta)
                        .orElseThrow(ZbException.supplier(ErrorCode.LIKE_NOT_FOUND));
        commentMapper.deleteCommentLike(commentMeta);
        commentMapper.updateCommentLikeCount(commentMeta);
    }
}
