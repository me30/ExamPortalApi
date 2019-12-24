package com.nx.controller;

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

import com.nx.entity.Question;
import com.nx.service.QuestionService;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@GetMapping()
	public Page<Question> findAll(Pageable pageable) {
		return questionService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Question> findById(@PathVariable("id") Long id) {
		return questionService.findById(id)
				.map(question -> ResponseEntity.ok().body(question))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Question save(@RequestBody Question question) {
		return questionService.save(question);
	}
	
	@PutMapping()
	public Question update(@RequestBody Question question) {
		return questionService.save(question);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Question> db = questionService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Question not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		questionService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
