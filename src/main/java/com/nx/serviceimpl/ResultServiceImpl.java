package com.nx.serviceimpl;

import org.springframework.stereotype.Service;

import com.nx.entity.Result;
import com.nx.repository.ResultRepository;
import com.nx.service.BasicService;
import com.nx.service.ResultService;

@Service
public class ResultServiceImpl extends BasicService<Result, ResultRepository> implements ResultService {

	@Override
	public Result getResultByExamIdandUserId(Long exam_id, Long user_id) {
		return repository.getResultByExamIdandUserId(exam_id, user_id);
	}

}