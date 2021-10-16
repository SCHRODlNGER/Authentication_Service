package com.authentication.Authentication.controller;


import com.authentication.Authentication.entity.UserInfo;
import com.authentication.Authentication.model.AccountCreationRequest;
import com.authentication.Authentication.model.AccountCreationResponse;
import com.authentication.Authentication.model.AuthenticationRequest;
import com.authentication.Authentication.model.AuthenticationResponse;
import com.authentication.Authentication.service.AuthDataService;
import com.authentication.Authentication.service.CustomUserDetailsService;
import com.authentication.Authentication.util.JwtUtil;
import com.authentication.Authentication.util.SHAEncodingUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthDataService authDataService;


    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("success");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@RequestBody AccountCreationRequest accountCreationRequest) throws  Exception{


        if(authDataService.findByUsername(accountCreationRequest.getUsername()) != null){
            return ResponseEntity.ok(
                    new AccountCreationResponse("failure", "Username already Exists")
            );

        }



        if(authDataService.findByEmail(accountCreationRequest.getEmail()) != null){
            return ResponseEntity.ok(
                    new AccountCreationResponse("failure", "Email already Exists")
            );
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setEmail(accountCreationRequest.getEmail());
        userInfo.setUsername(accountCreationRequest.getUsername());
        userInfo.setFirstname(accountCreationRequest.getFirstname());
        userInfo.setLastname(accountCreationRequest.getLastname());
        userInfo.setPassword(SHAEncodingUtil.getInstance().getSHAHash(accountCreationRequest.getPassword()));

        authDataService.createUserProfile(userInfo);


        return ResponseEntity.status(HttpStatus.CREATED).body( new AccountCreationResponse("success", "Account created"));

    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String headerData) throws Exception{
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        String[] data = headerData.split(" ");
        byte[] decoded = Base64.decodeBase64(data[1]);


        String decodedStr = new String(decoded, StandardCharsets.UTF_8);

        data = decodedStr.split(":");


        authenticationRequest.setUsername(data[0]);
        authenticationRequest.setPassword(data[1]);

        System.out.println(SHAEncodingUtil.getInstance().getSHAHash(authenticationRequest.getPassword()));


        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), SHAEncodingUtil.getInstance().getSHAHash(authenticationRequest.getPassword())));
        }
        catch (BadCredentialsException e){

            return ResponseEntity.ok(new AuthenticationResponse(null, "Incorrect UserName or Password", null));
        }
        catch (Exception e){
            return ResponseEntity.ok(new AuthenticationResponse(null, "UserName does not Exist",null));
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        UserInfo userInfo = authDataService.findByUsername(authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt, null, userInfo.getFirstname() ));
    }

}
