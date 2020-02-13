package com.nx.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nx.entity.ExamsAssign;
import com.nx.repository.ExamAssignRepository;
import com.nx.service.BasicService;
import com.nx.service.ExamAssignService;

@Service
public class ExamsAssignServiceImpl extends BasicService<ExamsAssign, ExamAssignRepository> implements ExamAssignService{

	@Override
	public List<ExamsAssign> loadExamAssignByExamId(Long user_id) {
		return repository.loadExamAssignByExamId(user_id);
	}

}
