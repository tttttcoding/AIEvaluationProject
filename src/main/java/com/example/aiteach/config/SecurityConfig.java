package com.example.aiteach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll() // 允许访问 /public 路径下的所有端点
                        .requestMatchers("/api/**").authenticated() // 需要认证才能访问 /api 路径下的端点
                        .anyRequest().denyAll() // 其他所有请求拒绝访问
                )
                .csrf(csrf -> csrf.disable()); // 如果需要，可以禁用 CSRF 保护

        return http.build();
    }
}