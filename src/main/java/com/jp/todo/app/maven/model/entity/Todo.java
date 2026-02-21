package com.jp.todo.app.maven.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * TODO情報
 */
@Entity
@Data
@Table(name = "todos")
public class Todo {
	
    // TODOリストID（主キー）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id; 
    // ユーザーID（外部キー）
    @Column(name = "user_id")
	private Integer userId; 
    // タイトル
    @Column(name = "title")
	private String title; 
    // 詳細
    @Column(name = "description")
    private String description; 
    // ステータス（外部キー）
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status; 
    // カテゴリー（外部キー）
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 
    // 作成日時
    @Column(name = "created_at")
    private LocalDateTime createdAt; 
    // 更新日時
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; 

}
