package com.jp.todo.app.maven.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * ステータス情報
 */
@Entity
@Data
@Table(name = "statuses")
public class Status {
	
    // ステータスID（主キー）
    @Id
    @Column(name = "id")
	private Integer id; 
    // ステータス名
    @Column(name = "name")
	private String name; 
    // 表示順
    @Column(name = "sort_order")
	private Integer sortOrder; 
}
