package com.authentication.Authentication.service;

import com.authentication.Authentication.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthDataService authDataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = authDataService.findByUsername(username);

        System.out.println("User found with UserName: " + userInfo.getUsername() + " Password: " + userInfo.getPassword());


        return new User(userInfo.getUsername(), userInfo.getPassword(), List.of());
    }


}
