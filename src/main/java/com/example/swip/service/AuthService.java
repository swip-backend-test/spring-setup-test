package com.example.swip.service;

import com.example.swip.config.JwtIssuer;
import com.example.swip.config.UserPrincipal;
import com.example.swip.dto.LoginResponse;
import com.example.swip.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public LoginResponse attemptLogin(String email, String password) {
        System.out.println("test : " + email + ", " + password);
        //SecurityConfig에서 구성한 SecurityFilterChain
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        System.out.println("authentication : " + authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    public String addUser(String email, String password) {
        System.out.println("enroll : " + email + ", " + password);
        // TODO : DB 연동 필요.
        var user = new User();
        var encode = passwordEncoder.encode(password);
        System.out.println(encode);
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(encode);
        user.setRole("ROLE_ADMIN");
        user.setExtraInfo("My nice admin");
        return "test success";
    }
}