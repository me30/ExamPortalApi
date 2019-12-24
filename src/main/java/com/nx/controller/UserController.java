package com.nx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nx.entity.RoleName;
import com.nx.entity.User;
import com.nx.security.JwtTokenProvider;
import com.nx.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	@Autowired
	HttpServletRequest req;
	
	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@GetMapping("/find")
	public ResponseEntity<User> findByToken() {
		String token = req.getHeader("Authorization").substring(7, req.getHeader("Authorization").length());
		return userService.findById(tokenProvider.getUserIdFromJWT(token))
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/search/")
	public Page<User> search(Pageable pageable,@RequestParam("searchText") String searchText) {
		return  userService.search(pageable,searchText);
	}

	@GetMapping()
	public Page<User> findAll(Pageable pageable) {
		return userService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") Long id) {
		return userService.findById(id)
				.map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping()
	public User save(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleName.User);
		return userService.save(user);
	}

	@PutMapping("/{id}")
	public User update(@PathVariable("id") Long id,	@RequestBody User user) {
		return userService.save(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		return userService.findById(id)
				.map(user -> {
					userService.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}
}
