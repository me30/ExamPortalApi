package com.nx.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="exam_assign")
public class ExamsAssign implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="assign_to")
	private User assignTo;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn(name="exam_id")
	private Exam exam;
	
	@Column(name="date_of_assign")
	private Date dateOfAssign;
	
	@ManyToOne
	@JoinColumn(name="assign_by")
	private User assignBy;
	
	@ManyToOne
	@JoinColumn(name="result_id")
	private Result result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(User assignTo) {
		this.assignTo = assignTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Date getDateOfAssign() {
		return dateOfAssign;
	}

	public void setDateOfAssign(Date dateOfAssign) {
		this.dateOfAssign = dateOfAssign;
	}

	public User getAssignBy() {
		return assignBy;
	}

	public void setAssignBy(User assignBy) {
		this.assignBy = assignBy;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}