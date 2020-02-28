package com.nx.controller;

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

import com.nx.entity.Result;
import com.nx.service.ResultService;

@RestController
@RequestMapping("/result")
@CrossOrigin("*")
public class ResultController {
	
	@Autowired
	private ResultService resultService; 
	
	@GetMapping("/getresult/{examId)/{userId}")
	public Result getResultByExamIdandUserId(@PathVariable("examId") Long exam_id,@PathVariable("userId") Long user_id) {
		return resultService.getResultByExamIdandUserId(exam_id, user_id);
	}
	
	@GetMapping("/findAll")
	public List<Result> findAll() {
		return resultService.findAll();
	}
	
	@GetMapping()
	public Page<Result> findAll(Pageable pageable) {
		return resultService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Result> findById(@PathVariable("id") Long id) {
		return resultService.findById(id)
				.map(result -> ResponseEntity.ok().body(result))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public Result save(@RequestBody Result result) {
		return resultService.save(result);
	}
	
	@PutMapping()
	public Result update(@RequestBody Result result) {
		return resultService.save(result);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<Result> db = resultService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		resultService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
