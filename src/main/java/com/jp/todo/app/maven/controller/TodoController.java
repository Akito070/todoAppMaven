package com.jp.todo.app.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TodoController {
    
    /**
     * TODOリストの一覧画面を表示
     * カテゴリー・ステータス情報もあわせて取得し、プルダウンなどに使用します。
     */
	@GetMapping("/todo/todoList")
	public String showTodoList(Model model) {
		// model.addAttribute("categories", categoryService.getAllCategories());
		// model.addAttribute("statuses", statusService.getAllStatuses());
		// model.addAttribute("todos", todoService.getAllTodos());
		return "todo/todo_list";
	}    
}
