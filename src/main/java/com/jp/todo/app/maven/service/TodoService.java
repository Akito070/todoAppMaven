package com.jp.todo.app.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jp.todo.app.maven.model.dto.TodoDto;
import com.jp.todo.app.maven.repository.TodoRepository;

@Service
public class TodoService {
	
	private final TodoRepository todoRepository;

	TodoService(TodoRepository todoRepository){
		this.todoRepository = todoRepository;
	}

	/**
	 * TODOリスト情報を取得する。（TODOリスト一覧用の項目）
	 * 
	 * @return TODOリストを取得
	 */
	public List<TodoDto> getAllTodos() {
	    // リポジトリから全てのTODOを取得し、DTOリストとして返す
	    return todoRepository.fetchTodos();
	}
}
