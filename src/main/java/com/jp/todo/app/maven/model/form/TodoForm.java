package com.jp.todo.app.maven.model.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * TODOフォーム
 */
@Data
public class TodoForm {

	// TODOリストID（主キー）
	private Integer id;
	// タイトル
	@NotEmpty(message = "{NotBlank.todoForm.title.ja}")
	private String title;
	// 詳細
	@NotEmpty(message = "{NotBlank.todoForm.description.ja}")
	private String description;
	// カテゴリーID（外部キー）
	@NotNull(message = "{NotNull.todoForm.categoryId.ja}")
	@Min(value = 1, message = "{Min.todoForm.categoryId.ja}")
	private Integer categoryId;
}
