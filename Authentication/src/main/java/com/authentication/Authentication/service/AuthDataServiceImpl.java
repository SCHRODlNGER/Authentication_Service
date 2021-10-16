package com.authentication.Authentication.service;

import com.authentication.Authentication.dao.UserInfoRepository;
import com.authentication.Authentication.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthDataServiceImpl implements AuthDataService{

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Override
    public UserInfo findByUsername(String username) {
        Optional<UserInfo> result = userInfoRepository.findByUserName(username);

        return result.orElse(null);
    }

    @Override
    public UserInfo findByEmail(String email) {
        Optional<UserInfo> result = userInfoRepository.findByEmail(email);
        return result.orElse(null);

    }

    @Override
    public void deleteByUserNamePassword(String username, String password) {
        userInfoRepository.deleteByUsernamePassword(username, password);
    }

    @Override
    public void createUserProfile(UserInfo userInfo) {
        userInfoRepository.createUserProfile(userInfo.getUsername(), userInfo.getFirstname(), userInfo.getLastname(), userInfo.getEmail(), userInfo.getPassword());
    }
}
