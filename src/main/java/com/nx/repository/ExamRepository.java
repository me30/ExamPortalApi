package com.nx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.nx.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long>, JpaSpecificationExecutor<Long>{

}
