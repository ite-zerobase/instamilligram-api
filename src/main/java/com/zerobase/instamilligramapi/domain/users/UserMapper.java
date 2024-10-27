package com.zerobase.instamilligramapi.domain.users;

import com.zerobase.instamilligramapi.domain.login.dto.AuthIn;
import com.zerobase.instamilligramapi.domain.users.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<UserOut> selectUserByAuth(AuthIn auth);
    Optional<UserOut> selectUserByUserSearch(UserSearch userSearch);
    Optional<UserOut> selectUserByUsername(String username);
    Optional<UserOut> selectDuplicateUsername(String username);
    Optional<UserOut> selectDuplicateEmail(String email);
    void insertUser(UserIn userIn);
    void insertFollow(Follow follow);
    Optional<Follow> selectFollow(Follow follow);
    void deleteFollow(Follow follow);
    void updateUserProfileImageUrl(String url);
}
