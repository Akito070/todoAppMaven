package com.jp.todo.app.maven.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jp.todo.app.maven.common.Constant;
import com.jp.todo.app.maven.model.dto.TodoDto;
import com.jp.todo.app.maven.model.entity.Todo;
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
	 * TODOリストの一覧画面を表示する。<br>
	 * カテゴリー・ステータス情報もあわせて取得し、プルダウンなどに使用している。
	 * 
	 * @param model 画面表示用データを格納するモデル
	 * @return TODOリストの一覧画面表示
	 */
	@GetMapping("/todo/todoList")
	public String showTodoList(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("statuses", statusService.getAllStatuses());
		model.addAttribute("todos", todoService.getAllTodos());
		return "todo/todo_list";
	}

	/**
	 * TODOリストを検索する。<br>
	 * 検索条件（タイトル・ステータス・カテゴリー）に基づいてTODOリストを検索し、<br>
	 * 一覧画面に表示する。検索結果に加え、プルダウン用のステータス・カテゴリー情報も返します。
	 * 
	 * @param title    タイトル
	 * @param status   ステータス
	 * @param category カテゴリー
	 * @param model    画面表示用データを格納するモデル
	 * @return 検索条件の結果
	 */
	@GetMapping("/todo/search")
	public String searchTodos(@RequestParam(required = false) String title,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String category,
			Model model) {
		// 検索結果を取得
		List<TodoDto> result = todoService.search(title, status, category);

		model.addAttribute("todos", result);
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("statuses", statusService.getAllStatuses());
		return "todo/todo_list";
	}

	/**
	 * TODOの登録画面を表示する。
	 * 
	 * @param model 画面表示用データを格納するモデル
	 * @return TODO登録画面表示
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
	 * @param form          TODOフォーム
	 * @param bindingResult バリデーション結果、入力エラーがある場合は画面へ戻すために使用する
	 * @param model         画面表示用データを格納するモデル
	 * @return TODO情報を登録
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

	/**
	 * TODOの編集画面を表示する。
	 * 
	 * @param id    TODOリストID
	 * @param model 画面表示用データを格納するモデル
	 * @return TODOの編集画面表示
	 */
	@GetMapping("/todo/edit/{id}")
	public String showEdit(@PathVariable("id") Integer id, Model model) {
		Todo todo = todoService.getTodoId(id);

		TodoForm form = new TodoForm();
		form.setId(todo.getId());
		form.setTitle(todo.getTitle());
		form.setDescription(todo.getDescription());
		form.setCategoryId(todo.getCategory().getId());

		model.addAttribute("todoEditForm", form);
		model.addAttribute("categories", categoryService.getAllCategories());

		return "todo/todo_edit";
	}

	/**
	 * TODOの更新処理
	 * 
	 * @param form          TODOフォーム
	 * @param bindingResult バリデーション結果、入力エラーがある場合は画面へ戻すために使用する
	 * @param model         画面表示用データを格納するモデル
	 * @return TODO情報を更新
	 */
	@PostMapping("/todo/edit")
	public String updateTodo(@ModelAttribute("todoEditForm") @Valid TodoForm form,
			BindingResult bindingResult, Model model) {
		// 入力チェック
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.getAllCategories());
			return "todo/todo_edit";
		}
		// 更新処理
		todoService.todoUpdate(form);
		return "redirect:/todo/todoList";
	}

	/**
	 * ステータス更新機能
	 * 
	 * @param id TODOリストID
	 * @return ステータス更新
	 */
	@PostMapping("/todo/toggleStatus/{id}")
	public String updateStatus(@PathVariable("id") Integer id) {
		// 対象のToDo情報を取得
		Todo todo = todoService.getTodoId(id);

		// 既に「処理済み」の場合は何もしないで一覧へ戻る
		if (todo.getStatus().getId() == Constant.STATUS_COMPLETED) {
			return "redirect:/todo/todoList";
		}

		// ステータスを次の状態へ更新
		todoService.upStatus(todo);

		return "redirect:/todo/todoList";
	}
}
