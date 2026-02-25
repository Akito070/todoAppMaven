package com.jp.todo.app.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jp.todo.app.maven.model.form.TodoForm;
import com.jp.todo.app.maven.service.CategoryService;
import com.jp.todo.app.maven.service.StatusService;
import com.jp.todo.app.maven.service.TodoService;

import jakarta.validation.Valid;

@Controller
public class TodoController {

	private final CategoryService categoryService;
	private final StatusService statusService;
	private final TodoService todoService;

	public TodoController(CategoryService categoryService, StatusService statusService,
			TodoService todoService) {
		this.categoryService = categoryService;
		this.statusService = statusService;
		this.todoService = todoService;
	}

	/**
	 * TODOリストの一覧画面を表示する。
	 * カテゴリー・ステータス情報もあわせて取得し、プルダウンなどに使用している。
	 */
	@GetMapping("/todo/todoList")
	public String showTodoList(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("statuses", statusService.getAllStatuses());
		model.addAttribute("todos", todoService.getAllTodos());
		return "todo/todo_list";
	}

	/**
	 * TODOの登録画面を表示する。
	 */
	@GetMapping("/todo/create")
	public String showCreate(Model model) {
		model.addAttribute("todoRegisterForm", new TodoForm());
		model.addAttribute("categories", categoryService.getAllCategories());
		return "todo/todo_create";
	}

	/**
	 * TODOの登録処理
	 * 
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping("/todo/create")
	public String registerTodo(@ModelAttribute("todoRegisterForm") @Valid TodoForm form,
			BindingResult bindingResult, Model model) {
		// 入力チェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("todoRegisterForm", form);
			model.addAttribute("categories", categoryService.getAllCategories());
			return "todo/todo_create";
		}
		// 登録処理
		todoService.todoRegister(form);
		return "redirect:/todo/todoList";
	}
}
