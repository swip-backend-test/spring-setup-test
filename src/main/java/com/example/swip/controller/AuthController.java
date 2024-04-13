package com.example.swip.controller;

import com.example.swip.dto.AddUserRequest;
import com.example.swip.dto.LoginRequest;
import com.example.swip.dto.LoginResponse;
import com.example.swip.service.AuthService;
import com.example.swip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/auth/login") // 'test@test.com', 'test' 입력시 로그인 토큰 반환 (UserService 내부 정의)
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest){  // @Validated
        return authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/auth/user")
    public String postUser(@RequestBody @Validated AddUserRequest addUserRequest) {
        if (!userService.isDuplicatedUserName(addUserRequest.getEmail())) {
            return authService.addUser(addUserRequest.getEmail(), addUserRequest.getPassword());
        }else
            return "duplicated User Name";
    }
}