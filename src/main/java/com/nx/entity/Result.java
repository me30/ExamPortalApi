package com.nx.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="result")
public class Result implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="total_correct_answer")
	private Long totalCorrectAnswer;
	
	@Column(name="out_off")
	private Long outOff;
	
	@Column(name="result")
	private String result;
	
	@Column(name="cut_off")
	private Long cutOff;
	
	@ManyToOne
	@JoinColumn(name="exam_id")
	private Exam exam;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}

	public void setTotalCorrectAnswer(Long totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
	}

	public Long getOutOff() {
		return outOff;
	}

	public void setOutOff(Long outOff) {
		this.outOff = outOff;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getCutOff() {
		return cutOff;
	}

	public void setCutOff(Long cutOff) {
		this.cutOff = cutOff;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}