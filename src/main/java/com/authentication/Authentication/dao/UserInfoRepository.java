package com.authentication.Authentication.dao;

import com.authentication.Authentication.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Query(value = "SELECT id, username, firstname, lastname, email,password " + "FROM users WHERE username= :USERNAME ", nativeQuery = true)
    Optional<UserInfo> findByUserName(@Param("USERNAME") String USERNAME);

    @Query(value = "SELECT id, username, firstname, lastname,email,password " + "FROM users WHERE email=:EMAIL ", nativeQuery = true)
    Optional<UserInfo> findByEmail(@Param("EMAIL") String EMAIL);

    @Query(value = "DELETE FROM users WHERE username= :USERNAME and password = :PASSWORD", nativeQuery = true)
    void deleteByUsernamePassword(@Param("USERNAME") String USERNAME, @Param("PASSWORD") String PASSWORD);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (username, firstname, lastname, email, password) " + "Values(:USERNAME, :FIRSTNAME, :LASTNAME, :EMAIL, :PASSWORD)",nativeQuery = true)
    void createUserProfile(@Param("USERNAME") String USERNAME, @Param("FIRSTNAME") String FIRSTNAME, @Param("LASTNAME") String LASTNAME,
                           @Param("EMAIL") String EMAIL, @Param("PASSWORD") String PASSWORD);



}
