package com.nx.controller;

import java.util.Date;
import java.util.List;
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

import com.nx.entity.Exam;
import com.nx.service.ExamService;

@RestController
@RequestMapping("/exam")
@CrossOrigin("*")
public class ExamController {
	
	@Autowired
	ExamService examService;
	
	@GetMapping("/findAll")
	public List<Exam> findAll() {
		return examService.findAll();
	}
	
	@GetMapping()
	public Page<Exam> findAll(Pageable pageable) {
		return examService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Exam> findById(@PathVariable("id") Long id) {
		return examService.findById(id)
				.map(exam -> ResponseEntity.ok().body(exam))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Exam save(@RequestBody Exam exam) {
		exam.setCreatedDate(new Date());
		return examService.save(exam);
	}
	
	@PutMapping()
	public Exam update(@RequestBody Exam exam) {
		exam.setCreatedDate(new Date());
		return examService.save(exam);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Exam> db = examService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		examService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
