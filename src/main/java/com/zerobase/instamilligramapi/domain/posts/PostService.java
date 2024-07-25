package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.comments.CommentService;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.domain.posts.dto.*;
import com.zerobase.instamilligramapi.domain.users.UserMapper;
import com.zerobase.instamilligramapi.global.dto.Paging;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final CommentService commentService;

    public PostOut insertPost(PostIn post) {
        userMapper.selectUserByUsername(post.getUsername())
                        .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND));
        if (post.getMedia().isEmpty()) {
            throw ZbException.from(ErrorCode.POST_MEDIA_NOT_FOUND);
        }
        postMapper.insertPost(post);
        for (PostMediaIn media: post.getMedia()) {
            media.setPostId(post.getPostId());
            postMapper.insertPostMedia(media);
        }
        return this.selectPost(post.getPostId(), post.getUsername());
    }
    public PostOut selectPost(Integer postId, String username) {
        PostIn postIn = new PostIn();
        postIn.setPostId(postId);
        postIn.setCurrentUsername(username);
        PostOut postOut = postMapper.selectPost(postIn)
                .orElseThrow(ZbException.supplier(ErrorCode.POST_NOT_FOUND));
        List<CommentOut> comments = commentService.selectCommentsByPost(postIn);
        postOut.setComments(comments);
        return postOut;
    }
    public List<PostOut> selectManyPost(Paging page) {
        return postMapper.selectManyPost(page);
    }

    public void insertPostLike(Integer postId, String username) {
        PostMeta postMeta = new PostMeta();
        postMeta.setPostId(postId);
        postMeta.setUsername(username);
        postMeta.setSign(1);
        postMapper.selectPostLike(postMeta)
                .ifPresent(a -> {
                    throw ZbException.from(ErrorCode.POST_ALREADY_LIKED);
                });
        postMapper.insertPostLike(postMeta);
        postMapper.updatePostLikeCount(postMeta);
    }
    public void deletePostLike(Integer postId, String username) {
        PostMeta postMeta = new PostMeta();
        postMeta.setPostId(postId);
        postMeta.setUsername(username);
        postMeta.setSign(-1);
        postMapper.selectPostLike(postMeta)
                .orElseThrow(ZbException.supplier(ErrorCode.LIKE_NOT_FOUND));
        postMapper.deletePostLike(postMeta);
        postMapper.updatePostLikeCount(postMeta);
    }
}
