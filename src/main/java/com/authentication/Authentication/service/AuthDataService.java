package com.authentication.Authentication.service;

import com.authentication.Authentication.entity.UserInfo;

import java.security.NoSuchAlgorithmException;

public interface AuthDataService {

    UserInfo findByUsername(String username);

    UserInfo findByEmail(String email);

    void deleteByUserNamePassword(String username, String password);

    void createUserProfile(UserInfo userInfo) throws NoSuchAlgorithmException;

}
