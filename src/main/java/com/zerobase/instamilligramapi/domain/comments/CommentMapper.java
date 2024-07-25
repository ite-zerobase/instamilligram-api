package com.zerobase.instamilligramapi.domain.comments;

import com.zerobase.instamilligramapi.domain.comments.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    Optional<CommentOut> selectCommentByCommentId(Integer commentId);
    List<CommentOut> selectCommentsByCommentSearch(CommentSearch commentSearch);
    void insertComment(CommentIn commentIn);
    void insertCommentLike(CommentMeta commentMeta);
    void deleteCommentLike(CommentMeta commentMeta);
    void updateCommentLikeCount(CommentMeta commentMeta);
    void updateReplyCount(CommentMeta commentMeta);
    Optional<CommentLike> selectCommentLike(CommentMeta commentMeta);
}
