package com.jp.todo.app.maven.model.dto;

/**
 * TODO情報DTO
 */
public record TodoDto(	
    // TODOリストID（主キー）
	Integer id, 
	// タイトル
	String title,
	// 詳細
    String description,
    // ステータス名
    String statusName,
    // カテゴリー名
    String categoryName
) {}