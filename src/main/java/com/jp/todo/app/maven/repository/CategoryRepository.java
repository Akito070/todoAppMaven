package com.jp.todo.app.maven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jp.todo.app.maven.model.dto.CategoryDto;
import com.jp.todo.app.maven.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	// カテゴリー情報の全件を取得
	@Query("""
		SELECT new com.jp.todo.app.maven.model.dto.CategoryDto(
			c.id,
			c.name,
			c.sortOrder
		)
		FROM Category c
		ORDER BY c.sortOrder ASC
		""")
	List<CategoryDto> findAllCategories();

}
