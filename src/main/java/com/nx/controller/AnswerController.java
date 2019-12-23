package com.nx.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nx.entity.Answer;
import com.nx.service.AnswerService;

@RestController
@RequestMapping("/answer")
@CrossOrigin("*")
public class AnswerController {
	
	@Autowired
	AnswerService answerService;

	@GetMapping()
	public Page<Answer> readAll(Pageable pageable) {
		return answerService.findAll(pageable);
	}
	
	@PostMapping()
	public Answer save(@RequestBody Answer answer){
		return answerService.save(answer);
	}
	
	@PutMapping()
	public Answer update(@RequestBody Answer answer){
		answer.setExamAttendantDate(new Date());
		return answerService.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Answer> db = answerService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		answerService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
