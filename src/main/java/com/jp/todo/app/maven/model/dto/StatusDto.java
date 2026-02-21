package com.jp.todo.app.maven.model.dto;

/**
 * ステータス情報DTO
 */
public record StatusDto(
	
	// ステータスID
	Integer id,
	// ステータス名
	String name,
	// 表示順
	Integer sortOrder
){} 


