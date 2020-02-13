package com.nx.service;

import java.util.List;

import com.nx.entity.Question;

public interface QuestionService extends IFinder<Question>, IService<Question> {
	
	List<Question> loadQuestionsByExamId(Long exam_id);

}
