package com.jp.todo.app.maven.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jp.todo.app.maven.model.dto.CategoryDto;
import com.jp.todo.app.maven.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;

	CategoryService(CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
	}
	
	/*
	 *  カテゴリー情報の全件を取得する
	 */
	public List<CategoryDto> getAllCategories(){
		return categoryRepository.findAllCategories();
	}
}
