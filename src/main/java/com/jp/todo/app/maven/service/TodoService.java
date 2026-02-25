package com.jp.todo.app.maven.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jp.todo.app.maven.model.dto.TodoDto;
import com.jp.todo.app.maven.model.entity.Category;
import com.jp.todo.app.maven.model.entity.Status;
import com.jp.todo.app.maven.model.entity.Todo;
import com.jp.todo.app.maven.model.entity.User;
import com.jp.todo.app.maven.model.form.TodoForm;
import com.jp.todo.app.maven.repository.CategoryRepository;
import com.jp.todo.app.maven.repository.StatusRepository;
import com.jp.todo.app.maven.repository.TodoRepository;
import com.jp.todo.app.maven.repository.UserRepository;

@Service
public class TodoService {

	private final TodoRepository todoRepository;
	private final CategoryRepository categoryRepository;
	private final StatusRepository statusRepository;
	private final UserRepository userRepository;

	TodoService(TodoRepository todoRepository, CategoryRepository categoryRepository,
			StatusRepository statusRepository, UserRepository userRepository) {
		this.todoRepository = todoRepository;
		this.categoryRepository = categoryRepository;
		this.statusRepository = statusRepository;
		this.userRepository = userRepository;
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

	/**
	 * TODO登録処理
	 * 
	 * @param form TODO登録用フォーム
	 * @return 登録されたTodoエンティティ
	 */
	public Todo todoRegister(TodoForm form) {

		// ログイン中のユーザー名（Spring Securityから取得）
		String username = getLoggedInUsername();

		// ユーザー名からUser情報を取得（存在しない場合は例外）
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

		// 新規Todoエンティティを作成し、フォームの値を設定
		Todo todo = new Todo();
		// ユーザーIDを紐付け
		todo.setUserId(user.getUserId());
		todo.setTitle(form.getTitle());
		todo.setDescription(form.getDescription());

		// カテゴリをIDで取得し、Todoに設定（存在しない場合は例外）
		Category category = categoryRepository.findById(form.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		todo.setCategory(category);

		// ステータスは固定値（例: ID=1のステータス）を設定
		Status status = statusRepository.findById(1)
				.orElseThrow(() -> new IllegalArgumentException("Status ID 1 not found"));
		todo.setStatus(status);

		// 登録・更新日時を現在時刻で設定
		LocalDateTime now = LocalDateTime.now();
		todo.setCreatedAt(now);
		todo.setUpdatedAt(now);

		// データベースに保存し、保存後のエンティティを返却
		return todoRepository.save(todo);
	}

	/**
	 * ログイン中のユーザー名を取得する。
	 * 
	 * @return Spring Securityから取得したログインユーザー名
	 */
	private String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 認証されたユーザー名を返す
		return authentication.getName();
	}
}
