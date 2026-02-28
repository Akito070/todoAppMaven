package com.jp.todo.app.maven.model.dto;

/**
 * カテゴリー情報DTO
 */
public record CategoryDto(

                // カテゴリーID（主キー）
                Integer id,
                // カテゴリー名
                String name,
                // 表示順
                Integer sortOrder) {
}