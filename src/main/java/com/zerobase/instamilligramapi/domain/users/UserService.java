package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.login.dto.AuthIn;
import com.zerobase.instamilligramapi.domain.users.dto.*;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import com.zerobase.instamilligramapi.global.security.JwtUtil;
import com.zerobase.instamilligramapi.global.utils.FileUploader;
import com.zerobase.instamilligramapi.global.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final FileUploader fileUploader;

    public String login(AuthIn authIn) {
        UserOut user = this.selectUserByAuth(authIn); //로그인 검증
        String token = jwtUtil.generateToken(user.getUsername());
        return token;
    }

    public UserOut selectUserByUserSearch(UserSearch userSearch) {
        return userMapper.selectUserByUserSearch(userSearch)
                .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND, "username: " + userSearch.getUsername()));
    }
    public UserOut selectUserByUsername(String username) {
        UserSearch userSearch = new UserSearch();
        userSearch.setUsername(username);
        return this.selectUserByUserSearch(userSearch);
    }
    public UserOut selectUserByUsername(String username, String requestingUser) {
        UserSearch userSearch = new UserSearch();
        userSearch.setUsername(username);
        userSearch.setRequestingUser(requestingUser);
        return this.selectUserByUserSearch(userSearch);
    }

    public UserOut selectUserByAuth(AuthIn auth) {
        auth.setPasswordHash(auth.getPassword());
        return userMapper.selectUserByAuth(auth)
                .orElseThrow(ZbException.supplier(ErrorCode.WRONG_USERNAME_OR_PASSWORD));
    }

    public UserOut createUser(UserIn userIn) {
        if (userMapper.selectDuplicateUsername(userIn.getUsername()).isPresent()) {
            throw ZbException.from(ErrorCode.DUPLICATE_USERNAME);
        };
        if (userMapper.selectDuplicateEmail(userIn.getUsername()).isPresent()) {
            throw ZbException.from(ErrorCode.DUPLICATE_EMAIL);
        };

        UserOut userOut = insertUser(userIn);
        String filename = uploadUserProfile(userIn);
        userIn.setProfilePictureUrl(filename);
        userMapper.updateUserProfileImageUrl(userIn);
        userOut.setProfilePictureUrl(filename);
        return userOut;
    }

    public String uploadUserProfile(UserIn user) {
        String ext = fileUploader.extractExtension(user.getProfileImage());
        String imageId = IdGenerator.generateId();
        String filename = user.getUsername() + "_" + imageId + "." + ext;
        fileUploader.upload(user.getProfileImage(), filename);
        return filename;
    }
    public UserOut insertUser(UserIn userIn) {
        userIn.setPasswordHash(userIn.getPassword());
        userMapper.insertUser(userIn);
        return this.selectUserByUsername(userIn.getUsername());
    }
    public Follow selectFollow(Follow follow) {
        return userMapper.selectFollow(follow)
                .orElseThrow(ZbException.supplier(ErrorCode.FOLLOW_NOT_FOUND));
    }
    public Follow insertFollow(String username, String requestingUser) {
        UserOut user = this.selectUserByUsername(username, requestingUser);
        if (user.getFollowing()) {
            throw ZbException.from(ErrorCode.USER_ALREADY_FOLLOWING);
        };
        Follow follow = new Follow();
        follow.setFollowee(username);
        follow.setFollower(requestingUser);
        userMapper.insertFollow(follow);
        return this.selectFollow(follow);
    }
    public void removeFollow(String username, String requestingUser) {
        this.selectUserByUsername(username, requestingUser);
        Follow follow = new Follow();
        follow.setFollowee(username);
        follow.setFollower(requestingUser);
        this.selectFollow(follow); // 팔로우가 없으면 삭제 미진행

        userMapper.deleteFollow(follow);
    }
}
