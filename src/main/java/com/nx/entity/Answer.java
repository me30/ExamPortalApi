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
@Table(name="answer")
public class Answer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String ans;
	
	@Column(name="answer_is_given")
	private Boolean answerIsGiven;
	
	@ManyToOne
	@JoinColumn(name="que_id")
	private Question queations;
	
	@Column(name="exam_attendant_date")
	private Date examAttendantDate;
	
	@ManyToOne
	@JoinColumn(name="exam_id")
	private Exam exam;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public Boolean getAnswerIsGiven() {
		return answerIsGiven;
	}

	public void setAnswerIsGiven(Boolean answerIsGiven) {
		this.answerIsGiven = answerIsGiven;
	}

	public Question getQueations() {
		return queations;
	}

	public void setQueations(Question queations) {
		this.queations = queations;
	}

	public Date getExamAttendantDate() {
		return examAttendantDate;
	}

	public void setExamAttendantDate(Date examAttendantDate) {
		this.examAttendantDate = examAttendantDate;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}
