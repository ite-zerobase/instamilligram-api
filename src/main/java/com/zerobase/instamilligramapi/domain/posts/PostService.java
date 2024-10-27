package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.comments.CommentService;
import com.zerobase.instamilligramapi.domain.comments.dto.CommentOut;
import com.zerobase.instamilligramapi.domain.posts.dto.*;
import com.zerobase.instamilligramapi.domain.users.UserMapper;
import com.zerobase.instamilligramapi.domain.users.dto.UserSearch;
import com.zerobase.instamilligramapi.global.dto.Paging;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import com.zerobase.instamilligramapi.global.utils.FileUploader;
import com.zerobase.instamilligramapi.global.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final FileUploader fileUploader;

    public void insertPost(PostIn post) {
        userMapper.selectUserByUserSearch(UserSearch.username(post.getUsername()))
                        .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND, "username: " + post.getUsername()));
        postMapper.insertPost(post);
    }
    public PostOut createPost(PostIn post) {
        insertPost(post);
        return this.selectPost(post.getPostId(), post.getUsername());
    }

    public List<PostMediaOut> uploadPostMedia(int postId, List<MultipartFile> images) {
        this.selectPost(postId, null);
        if (images.isEmpty()) {
            throw ZbException.from(ErrorCode.POST_MEDIA_NOT_FOUND);
        }
        List<PostMediaIn> mediaList = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            MultipartFile file = images.get(i);
            String ext = fileUploader.extractExtension(file);
            String imageId = IdGenerator.generateId();
            String filename = "p_" + postId + "_" + i + "_" + imageId + "." + ext;

            String result = fileUploader.upload(file, filename);

            PostMediaIn media = new PostMediaIn();
            media.setPostId(postId);
            media.setMediaSeq(i + 1);
            media.setMediaType("image");
            media.setMediaUrl(result);

            mediaList.add(media);
        }
        for (PostMediaIn media: mediaList) {
            postMapper.insertPostMedia(media);
        }
        return postMapper.selectPostMedia(postId);
    }
    public PostOut selectPost(Integer postId, String username) {
        PostIn postIn = new PostIn();
        postIn.setPostId(postId);
        postIn.setRequestingUser(username);
        return postMapper.selectPost(postIn)
                .orElseThrow(ZbException.supplier(ErrorCode.POST_NOT_FOUND, "postId: " + postId));
    }

    public List<PostOut> selectManyPost(Paging page) {
        return postMapper.selectManyPost(page);
    }

    public void insertPostLike(Integer postId, String username) {
        PostMeta postMeta = new PostMeta();
        postMeta.setPostId(postId);
        postMeta.setUsername(username);
        postMeta.setSign(1);
        this.selectPost(postId, username);
        postMapper.selectPostLike(postMeta)
                .ifPresent(a -> {
                    throw ZbException.from(ErrorCode.POST_ALREADY_LIKED, "postId: " + postId);
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
