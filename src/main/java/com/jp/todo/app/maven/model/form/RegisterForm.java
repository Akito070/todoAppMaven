package com.jp.todo.app.maven.model.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー登録フォーム
 */
@Data
public class RegisterForm {

    // 氏名
    @NotEmpty(message = "{NotBlank.registerForm.name.ja}")
    @Size(max = 100, message = "{Size.registerForm.name.ja}")
    private String name;
    // ユーザー名
    @NotEmpty(message = "{NotBlank.registerForm.userName.ja}")
    @Size(max = 100, message = "{Size.registerForm.userName.ja}")
    private String userName;
    // パスワード
    @NotEmpty(message = "{NotBlank.registerForm.password.ja}")
    @Size(min = 6, message = "{Size.registerForm.password.ja}")
    private String password;
}
