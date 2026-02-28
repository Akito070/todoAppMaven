package com.jp.todo.app.maven.service.impl;

import java.util.Collections;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jp.todo.app.maven.repository.UserRepository;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ユーザー名を基にユーザー情報を取得する。
     *
     * Spring Security の認証処理で呼び出され、
     * 入力された username に対応するユーザー情報をデータベースから検索する。
     *
     * 該当ユーザーが存在しない場合は
     * {@link org.springframework.security.core.userdetails.UsernameNotFoundException}をスローする。
     *
     * 取得したユーザー情報は
     * {@link org.springframework.security.core.userdetails.UserDetails}として返却し、認証処理に利用される。
     *
     * @param username ログイン時に入力されたユーザー名
     * @return 認証処理に使用するユーザー情報（UserDetails）
     * @throws UsernameNotFoundException 指定されたユーザーが存在しない場合
     */
    public UserDetails loadUserByUsername(String username) {
        com.jp.todo.app.maven.model.entity.User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.emptyList());
    }
}
