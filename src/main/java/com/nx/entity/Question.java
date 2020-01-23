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
@Table(name="question")
public class Question implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String question;
	
	@ManyToOne
	@JoinColumn(name="exam_id")
	private Exam exam;
	
	@Column(name="ans_category")
	private String ansCategory;
	
	@Column(name="correct_ans")
	private String correct_ans;
	
	@Column(name="option1")
	private String option1;
	
	@Column(name="option2")
	private String option2;
	
	@Column(name="option3")
	private String option3;
	
	@Column(name="option4")
	private String option4;
	
	@Column(name="option1_is_ans")
	private Boolean option1IsAns;
	
	@Column(name="option2_is_ans")
	private Boolean option2IsAns;
	
	@Column(name="option3_is_ans")
	private Boolean option3IsAns;
	
	@Column(name="option4_is_ans")
	private Boolean option4IsAns;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public String getAnsCategory() {
		return ansCategory;
	}

	public void setAnsCategory(String ansCategory) {
		this.ansCategory = ansCategory;
	}

	public String getCorrect_ans() {
		return correct_ans;
	}

	public void setCorrect_ans(String correct_ans) {
		this.correct_ans = correct_ans;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public Boolean getOption1IsAns() {
		return option1IsAns;
	}

	public void setOption1IsAns(Boolean option1IsAns) {
		this.option1IsAns = option1IsAns;
	}

	public Boolean getOption2IsAns() {
		return option2IsAns;
	}

	public void setOption2IsAns(Boolean option2IsAns) {
		this.option2IsAns = option2IsAns;
	}

	public Boolean getOption3IsAns() {
		return option3IsAns;
	}

	public void setOption3IsAns(Boolean option3IsAns) {
		this.option3IsAns = option3IsAns;
	}

	public Boolean getOption4IsAns() {
		return option4IsAns;
	}

	public void setOption4IsAns(Boolean option4IsAns) {
		this.option4IsAns = option4IsAns;
	}

}