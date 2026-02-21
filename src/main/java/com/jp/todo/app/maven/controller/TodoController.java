package com.jp.todo.app.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jp.todo.app.maven.service.CategoryService;
import com.jp.todo.app.maven.service.StatusService;
import com.jp.todo.app.maven.service.TodoService;

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
}
