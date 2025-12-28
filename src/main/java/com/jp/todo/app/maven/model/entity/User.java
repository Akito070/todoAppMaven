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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // ユーザーID（主キー）
    private Integer userId; 

    @Column(name = "name")
    // 氏名
    private String name; 
    
    @Column(name = "username")
    // ユーザ名
    private String userName; 

    @Column(name = "password")
    // ハッシュ化されたパスワード
    private String password; 
    
    @Column(name = "created_at")
    // 作成日時
    private LocalDateTime createdAt; 
}
