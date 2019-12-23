package com.nx.serviceimpl;

import org.springframework.stereotype.Service;
import com.nx.entity.Exam;
import com.nx.repository.ExamRepository;
import com.nx.service.BasicService;
import com.nx.service.ExamService;

@Service
public class ExamServiceImpl extends BasicService<Exam, ExamRepository> implements ExamService{

}
