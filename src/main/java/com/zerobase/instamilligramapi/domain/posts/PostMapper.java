package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.posts.dto.*;
import com.zerobase.instamilligramapi.global.dto.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    void insertPost(PostIn post);
    Optional<PostOut> selectPost(PostIn postIn);
    List<PostOut> selectManyPost(Paging page);
    void insertPostLike(PostMeta postMeta);
    void deletePostLike(PostMeta postMeta);
    void updatePostLikeCount(PostMeta postMeta);
    void updatePostCommentCount(PostMeta postMeta);
    void insertPostMedia(PostMediaIn postMedia);
    Optional<PostLike> selectPostLike(PostMeta postMeta);
}
