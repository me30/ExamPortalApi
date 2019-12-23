package com.nx.serviceimpl;

import org.springframework.stereotype.Service;

import com.nx.entity.Answer;
import com.nx.repository.AnswerRepository;
import com.nx.service.AnswerService;
import com.nx.service.BasicService;

@Service
public class AnswerServiceImpl extends BasicService<Answer, AnswerRepository> implements AnswerService{

}
