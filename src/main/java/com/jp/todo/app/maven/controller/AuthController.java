package com.jp.todo.app.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jp.todo.app.maven.model.form.RegisterForm;
import com.jp.todo.app.maven.service.UserService;

import jakarta.validation.Valid;


@Controller
public class AuthController {

	private final UserService userService;
    
    AuthController(UserService userService){
    	this.userService = userService;
    }

	/**
	 * ログイン画面表示
	 * @return ログイン画面表示
	 */
	@GetMapping("/todo/login")
	public String showLogin() {
		return "auth/login";
	}

	/**
	 * ユーザ登録画面表示
	 * @return ユーザ登録画面表示
	 */
	@GetMapping("/todo/register")
	public String showRegisterForm(Model model) {
	    model.addAttribute("registerForm", new RegisterForm());
	    return "auth/register"; 
	}
	
	/**
	 * ユーザ登録処理
	 * @param name 氏名
	 * @param username ユーザ名
	 * @param password パスワード
	 * @return ユーザ情報登録
	 */
    @PostMapping("/todo/register")
    public String register(@ModelAttribute("registerForm") @Valid RegisterForm form,
            BindingResult bindingResult, Model model) {
    	// 入力チェック
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("registerForm", form);
    		return "auth/register";
    	}
    	// 登録処理
    	userService.registerUser(form);
        return "redirect:/login";
    }
}