package com.jp.todo.app.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jp.todo.app.maven.model.dto.StatusDto;
import com.jp.todo.app.maven.repository.StatusRepository;

@Service
public class StatusService {
	
	private final StatusRepository statusRepository;
	
	StatusService(StatusRepository statusRepository){
		this.statusRepository = statusRepository;
	}

	/*
	 *  ステータス情報の全件を取得する
	 */
	public List<StatusDto> getAllStatuses(){
		return statusRepository.findAllStatuses();
	}
}
