package com.nx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.nx.entity.User;
import com.nx.repository.UserRepository;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping()
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@PostMapping()
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUsersById(@PathVariable("id") Long id) {
		return userRepository.findById(id)
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") Long id,	@RequestBody User user) {
		return userRepository.save(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		return userRepository.findById(id)
				.map(user -> {
					userRepository.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}
}
