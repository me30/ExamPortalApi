package com.nx.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nx.entity.Question;
import com.nx.repository.QuestionRepository;
import com.nx.service.BasicService;
import com.nx.service.QuestionService;

@Service
public class QuestionServiceImpl extends BasicService<Question, QuestionRepository> implements QuestionService{

	@Override
	public List<Question> loadQuestionsByExamId(Long exam_id) {
		return repository.loadQuestionsByExamId(exam_id);
	}

	
	
}
