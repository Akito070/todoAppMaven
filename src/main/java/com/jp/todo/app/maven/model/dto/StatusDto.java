package com.jp.todo.app.maven.model.dto;

/**
 * ステータス情報DTO
 */
public record StatusDto(

		// ステータスID（主キー）
		Integer id,
		// ステータス名
		String name,
		// 表示順
		Integer sortOrder) {
}
