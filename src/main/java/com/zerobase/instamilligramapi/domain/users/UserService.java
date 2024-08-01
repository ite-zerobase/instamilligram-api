package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.users.dto.*;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserMapper userMapper;

    public UserOut selectUserByUserSearch(UserSearch userSearch) {
        return userMapper.selectUserByUserSearch(userSearch)
                .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND, "username: " + userSearch.getUsername()));
    }
    public UserOut selectUserByUsername(String username) {
        UserSearch userSearch = new UserSearch();
        userSearch.setUsername(username);
        return this.selectUserByUserSearch(userSearch);
    }

    public UserOut selectUserByAuth(AuthIn auth) {
        auth.setPasswordHash(auth.getPassword());
        return userMapper.selectUserByAuth(auth)
                .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND));
    }
    public UserOut insertUser(UserIn userIn) {
        userIn.setPasswordHash(userIn.getPassword());
        userMapper.insertUser(userIn);
        return this.selectUserByUsername(userIn.getUsername());
    }
    public void followUser(Follow follow) {
        if (follow.getFollower().isBlank() || follow.getFollowee().isBlank()) throw ZbException.from(ErrorCode.EMPTY_FOLLOW_REQUEST);
        if (follow.getFollower().equals(follow.getFollowee())) throw ZbException.from(ErrorCode.SELF_FOLLOWING_REQUEST);
        this.selectUserByUsername(follow.getFollower());
        this.selectUserByUsername(follow.getFollowee());
        userMapper.insertFollow(follow);
    }
    public void unfollowUser(Follow follow) {
        userMapper.selectFollow(follow)
                .orElseThrow(ZbException.supplier(ErrorCode.FOLLOW_NOT_FOUND, follow.getFollower() + "-" + follow.getFollowee()));
        userMapper.deleteFollow(follow);
    }
}
