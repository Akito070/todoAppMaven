package com.jp.todo.app.maven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jp.todo.app.maven.model.dto.StatusDto;
import com.jp.todo.app.maven.model.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

	/**
	 * ステータス情報の全件を取得する。
	 */
	@Query("""
			SELECT new com.jp.todo.app.maven.model.dto.StatusDto(
				s.id,
				s.name,
				s.sortOrder
			)
			   FROM Status s
			   ORDER BY s.sortOrder ASC
			""")
	List<StatusDto> findAllStatuses();

	/**
	 * 指定された表示順に一致するステータスを取得する。
	 *
	 * @param sortOrder 表示順
	 */
	Optional<Status> findBySortOrder(Integer sortOrder);
}
