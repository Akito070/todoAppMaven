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
	 * TODOリストを検索する。
	 * 
	 * @param title    検索対象のタイトル（部分一致）
	 * @param status   ステータス名（完全一致）
	 * @param category カテゴリ名（完全一致）
	 * @return 条件に一致するTODOのDTOリスト
	 */
	public List<TodoDto> search(String title, String status, String category) {
		// 空文字や空白文字列を null に変換し、JPQLの IS NULL 条件に対応させる
		if (isBlank(title))
			title = null;
		if (isBlank(status))
			status = null;
		if (isBlank(category))
			category = null;

		// リポジトリメソッドで条件検索を実行
		return todoRepository.findByTitleAndStatusAndCategory(title, status, category);
	}

	/**
	 * 文字列が null または空白（スペースやタブなど）だけかどうかを判定する。
	 * 
	 * @param value チェック対象の文字列
	 * @return null または空白のみの文字列であれば true、それ以外は false
	 */
	private boolean isBlank(String value) {
		// null または空白のみで構成されていれば true
		return value == null || value.trim().isEmpty();
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

		// カテゴリをIDで取得し、TODOに設定（存在しない場合は例外）
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

	/**
	 * 指定IDのTODO情報を取得する。
	 * 
	 * @param id TODOのID
	 * @return 指定されたIDに該当するTODOエンティティ（存在しない場合は例外）
	 */
	public Todo getTodoId(Integer id) {
		return todoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid todo ID"));
	}

	/**
	 * TODO更新処理
	 * 
	 * @param form 画面から送られたTODO登録用フォーム
	 * @return 登録されたTODOエンティティ
	 */
	public Todo todoUpdate(TodoForm form) {

		// TODO情報を取得
		Todo todo = getTodoId(form.getId());
		// フォームの値を設定
		todo.setTitle(form.getTitle());
		todo.setDescription(form.getDescription());

		// カテゴリをIDで取得し、TODOに設定（存在しない場合は例外）
		Category category = categoryRepository.findById(form.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		todo.setCategory(category);

		// 登録・更新日時を現在時刻で設定
		LocalDateTime now = LocalDateTime.now();
		todo.setUpdatedAt(now);

		// データベースに保存し、保存後のエンティティを返却
		return todoRepository.save(todo);
	}

	/**
	 * ToDoのステータスを1つ先に進める（未処理 → 処理中 → 処理済）
	 *
	 * @param todo 対象のTODOエンティティ
	 */
	public void upStatus(Todo todo) {

		// 現在のステータスの並び順を取得
		int currentSortOrder = todo.getStatus().getSortOrder();

		// 次のステータスを sortOrder で取得
		Status nextStatus = statusRepository.findBySortOrder(currentSortOrder + 1)
				.orElseThrow(() -> new IllegalArgumentException("次のステータスが存在しません"));

		// ステータスを更新
		todo.setStatus(nextStatus);

		// 更新日時を現在時刻で設定
		todo.setUpdatedAt(LocalDateTime.now());

		// DBに保存
		todoRepository.save(todo);
	}
}
