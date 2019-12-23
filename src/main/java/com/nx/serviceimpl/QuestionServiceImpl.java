package com.nx.serviceimpl;

import org.springframework.stereotype.Service;

import com.nx.entity.Question;
import com.nx.repository.QuestionRepository;
import com.nx.service.BasicService;
import com.nx.service.QuestionService;

@Service
public class QuestionServiceImpl extends BasicService<Question, QuestionRepository> implements QuestionService{

}
