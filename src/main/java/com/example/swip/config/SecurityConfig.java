package com.example.swip.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityUserDetailService securityUserDetailService;
    private final UnauthorizedHandler unauthorizedHandler;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors(AbstractHttpConfigurer::disable)  //cors 기능 비활성화
                .csrf(AbstractHttpConfigurer::disable)  //csrf 기능 비활성화
                .securityMatcher("/**") // map current config to given resource path
                .sessionManagement(sessionManagementConfigurer
                        -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable) // 기본 인증 기능 비활성화
                .exceptionHandling(e-> e.authenticationEntryPoint(unauthorizedHandler));

        http
                .authorizeHttpRequests(authorize -> authorize // 요청에 대한 권한 설정 메서드
                        .requestMatchers("/").permitAll() // / 경로 요청에 대한 권한을 설정. permitAll() 모든 사용자, 인증되지않은 사용자에게 허용
                        .requestMatchers("/auth/**").permitAll() // 모든 사용자에게 허용
                        .requestMatchers("/board/**").permitAll() // 모든 사용자에게 허용
                        .requestMatchers(HttpMethod.GET, "/board/{boardId}/edit").hasRole("@webSecurity.checkUserHasAccessToProjectId(authentication,#boardId)") //("@webSecurity.checkUserHasAccessToProjectId(authentication,#boardId)")
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN 에게만 허용
                        .anyRequest().authenticated() // 다른 나머지 모든 요청에 대한 권한 설정, authenticated()는 인증된 사용자에게만 허용, 로그인해야만 접근 가
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(securityUserDetailService).passwordEncoder(passwordEncoder());

        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

}