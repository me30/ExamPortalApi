package com.nx.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

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

import com.nx.entity.ExamsAssign;
import com.nx.service.ExamAssignService;


@RestController
@RequestMapping("/examAssign")
@CrossOrigin("*")
public class ExamAssignController {
	
	@Autowired
	ExamAssignService examAssignService;
	
	@GetMapping("/getUsers/{id}")
	public List<ExamsAssign> loadExamAssignByUserId(@PathVariable("id") Long user_id) {
		return examAssignService.loadExamAssignByUserId(user_id);
	}
	
	@GetMapping("/findAll")
	public List<ExamsAssign> findAll() {
		return examAssignService.findAll();
	}
	
	@GetMapping()
	public Page<ExamsAssign> findAll(Pageable pageable) {
		return examAssignService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ExamsAssign> findById(@PathVariable("id") Long id) {
		return examAssignService.findById(id)
				.map(examAssign -> ResponseEntity.ok().body(examAssign))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public ExamsAssign save(@RequestBody ExamsAssign examAssign) {
		return examAssignService.save(examAssign);
	}
	
	@PutMapping()
	public ExamsAssign update(@RequestBody ExamsAssign examAssign) {
		return examAssignService.save(examAssign);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<ExamsAssign> db = examAssignService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		examAssignService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
