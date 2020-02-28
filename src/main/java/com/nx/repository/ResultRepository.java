package com.nx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nx.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>, JpaSpecificationExecutor<Result>{

	@Query("select r From Result r where r.exam.id = ?1 and r.user.id = ?2")
	Result getResultByExamIdandUserId(Long exam_id,Long user_id);
}