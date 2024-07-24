package com.zerobase.instamilligramapi.domain.posts;

import com.zerobase.instamilligramapi.domain.posts.dto.PostIn;
import com.zerobase.instamilligramapi.domain.posts.dto.PostOut;
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

    public PostOut insertPost(PostIn post) {
        postMapper.insertPost(post);
        return this.selectPost(post.getPostId());
    }
    public PostOut selectPost(Integer postId) {
        return postMapper.selectPost(postId)
                .orElseThrow(ZbException.supplier(ErrorCode.POST_NOT_FOUND));
    }
    public List<PostOut> selectManyPost(Paging page) {
        return postMapper.selectManyPost(page);
    }
}
