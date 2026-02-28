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

	AuthController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * ログイン画面を表示する。
	 * 
	 * @return ログイン画面表示
	 */
	@GetMapping("/todo/login")
	public String showLogin() {
		return "auth/login";
	}

	/**
	 * ユーザー登録画面を表示する。
	 * 
	 * @param model 画面表示用データを格納するモデル
	 * @return ユーザー登録画面表示
	 */
	@GetMapping("/todo/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "auth/register";
	}

	/**
	 * ユーザー登録処理
	 * 
	 * @param form          ユーザー登録フォーム
	 * @param bindingResult バリデーション結果、入力エラーがある場合は画面へ戻すために使用する
	 * @param model         画面表示用データを格納するモデル
	 * @return ユーザー情報登録
	 */
	@PostMapping("/todo/register")
	public String register(@ModelAttribute("registerForm") @Valid RegisterForm form,
			BindingResult bindingResult, Model model) {
		// 入力チェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("registerForm", form);
			return "auth/register";
		}
		// 登録処理
		userService.registerUser(form);
		return "redirect:/login";
	}
}