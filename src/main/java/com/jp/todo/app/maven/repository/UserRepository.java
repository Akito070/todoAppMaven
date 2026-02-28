package com.jp.todo.app.maven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jp.todo.app.maven.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * ユーザー名を取得する。
	 * 
	 * @param userName 氏名
	 */
	Optional<User> findByUserName(String userName);
}
