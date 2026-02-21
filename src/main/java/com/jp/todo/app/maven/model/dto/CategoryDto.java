package com.jp.todo.app.maven.model.dto;

/**
 * カテゴリー情報DTO
 */
public record CategoryDto(

    // カテゴリーID
    Integer id,
    // カテゴリー名
    String name,
    // 表示順
    Integer sortOrder
) {}
