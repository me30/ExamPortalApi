package com.nx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nx.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>,JpaSpecificationExecutor<Answer>{

}
