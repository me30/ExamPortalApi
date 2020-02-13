package com.nx.service;

import java.util.List;

import com.nx.entity.ExamsAssign;

public interface ExamAssignService extends IFinder<ExamsAssign>,IService<ExamsAssign>{
	
	List<ExamsAssign> loadExamAssignByExamId(Long user_id);
}
