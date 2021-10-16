package com.authentication.Authentication.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountCreationRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;

}
