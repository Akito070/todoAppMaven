package com.jp.todo.app.maven.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jp.todo.app.maven.service.impl.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ログインページは誰でもアクセス可
                        .requestMatchers("/css/**", "/js/**",
                                "/todo/login", "/todo/register")
                        .permitAll()
                        // それ以外は認証が必要
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        // カスタムログインページ
                        .loginPage("/todo/login")
                        // ログイン成功後のリダイレクト先
                        .defaultSuccessUrl("/todo/todoList", true)
                        .permitAll())
                .logout(logout -> logout
                        // ログアウト後のリダイレクト先
                        .logoutSuccessUrl("/todo/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}