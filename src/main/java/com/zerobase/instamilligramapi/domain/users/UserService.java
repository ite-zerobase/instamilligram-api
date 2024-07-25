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
                .orElseThrow(ZbException.supplier(ErrorCode.USER_NOT_FOUND));
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
    public void insertFollow(Follow follow) {
        this.selectUserByUsername(follow.getUsername());
    }
    public void removeFollow(Follow follow) {
        userMapper.selectFollow(follow)
                .orElseThrow();
    }
}
