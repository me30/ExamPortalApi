package com.nx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nx.entity.ExamsAssign;
import com.nx.entity.Question;

@Repository
public interface ExamAssignRepository extends JpaRepository<ExamsAssign, Long>, JpaSpecificationExecutor<ExamsAssign>{

	@Query("select e From ExamsAssign e where e.assignTo.id = ?1")
	List<ExamsAssign> loadExamAssignByUserId(Long user_id);
	
	
}
