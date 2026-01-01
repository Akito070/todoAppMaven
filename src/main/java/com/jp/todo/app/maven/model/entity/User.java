package com.jp.todo.app.maven.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    // ユーザーID（主キー）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId; 

    // 氏名
    @Column(name = "name")
    private String name; 
    
    // ユーザ名
    @Column(name = "username")
    private String userName; 

    // ハッシュ化されたパスワード
    @Column(name = "password")
    private String password; 
    
    // 作成日時
    @Column(name = "created_at")
    private LocalDateTime createdAt; 
}
