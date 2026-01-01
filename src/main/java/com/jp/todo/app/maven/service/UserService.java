package com.jp.todo.app.maven.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jp.todo.app.maven.model.entity.User;
import com.jp.todo.app.maven.model.form.RegisterForm;
import com.jp.todo.app.maven.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * ユーザ情報設定
     * @param form ユーザ情報入力
     */
    public void registerUser(RegisterForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setUserName(form.getUserName());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
