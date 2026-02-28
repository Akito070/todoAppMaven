package com.jp.todo.app.maven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jp.todo.app.maven.model.dto.TodoDto;
import com.jp.todo.app.maven.model.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	/**
	 * TODO情報の全件を取得する。
	 */
	@Query("""
			SELECT new com.jp.todo.app.maven.model.dto.TodoDto(
				t.id,
				t.title,
				t.description,
				s.name,
				c.name
			)
			   FROM Todo t
			   LEFT JOIN t.status s
			   LEFT JOIN t.category c
			   ORDER BY t.status ASC
			""")
	List<TodoDto> fetchTodos();

	/**
	 * Todo一覧を検索条件に応じて取得する。
	 *
	 * <p>
	 * 検索条件はすべて任意。
	 * 
	 * <ul>
	 * <li>title : 部分一致検索</li>
	 * <li>status : ステータス名の完全一致</li>
	 * <li>category : カテゴリー名の完全一致</li>
	 * </ul>
	 *
	 * @param title    タイトル（部分一致検索、null可）
	 * @param status   ステータス名（完全一致、null可）
	 * @param category カテゴリー名（完全一致、null可）
	 * @return 検索条件に一致するTodo一覧
	 */
	@Query("""
			SELECT new com.jp.todo.app.maven.model.dto.TodoDto(
				t.id,
				t.title,
				t.description,
				s.name,
				c.name
			)
			FROM Todo t
			JOIN t.status s
			JOIN t.category c
			WHERE (:title IS NULL OR t.title LIKE CONCAT('%', :title, '%'))
			AND (:status IS NULL OR s.name = :status)
			AND (:category IS NULL OR c.name = :category)
			""")
	List<TodoDto> findByTitleAndStatusAndCategory(
			@Param("title") String title,
			@Param("status") String status,
			@Param("category") String category);

	/**
	 * 指定されたIDのTODOを取得する。
	 *
	 * @param id 取得対象のTODO ID
	 */
	Optional<Todo> findById(Integer id);
}
