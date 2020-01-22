package com.nx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import com.nx.exception.AppException;
import com.nx.payload.UpdateUserPasswordRequest;
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

	@GetMapping("/getOnlyUsres")
	public List<User> getOnlyUsers(){
		return userService.getOnlyUsers();
	} 
	
	
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

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody User web) throws Exception {
		try {
			String token = req.getHeader("Authorization").substring(7, req.getHeader("Authorization").length());
			Long userId = tokenProvider.getUserIdFromJWT(token);

			User entity = userService.findById(userId).orElse(null);
			if (entity == null) {
				return new ResponseEntity<AppException>(new AppException("User not found"), HttpStatus.BAD_REQUEST);
			}
			
			entity.setUserName(null!=web.getUserName()?web.getUserName():entity.getUserName());
			entity.setFirstName(null!=web.getFirstName()?web.getFirstName():entity.getFirstName());
			entity.setLastName(null!=web.getLastName()?web.getLastName():entity.getLastName());
			entity.setGender(null!=web.getGender()?web.getGender():entity.getGender());
			entity.setPassword(null!=web.getPassword()? passwordEncoder.encode(web.getPassword()):entity.getPassword());
			entity.setEmail(null!=web.getEmail()?web.getEmail():entity.getEmail());
			entity.setDob(null!=web.getDob()?web.getDob():entity.getDob());

			userService.save(entity);
			return new ResponseEntity<String>("User updated successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<AppException>(new AppException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/changepassword")
	public ResponseEntity<?> changepassword(@RequestBody UpdateUserPasswordRequest passwordRequest) throws Exception{
		try {
			String token = req.getHeader("Authorization").substring(7, req.getHeader("Authorization").length());
			Long userId = tokenProvider.getUserIdFromJWT(token);

			User entity = userService.findById(userId).orElse(null);
			if (entity == null) {
				return new ResponseEntity<AppException>(new AppException("User not found"), HttpStatus.BAD_REQUEST);
			}
			entity.setPassword(null!=passwordRequest.getNewpassword()? passwordEncoder.encode(passwordRequest.getNewpassword()):"");
			userService.save(entity);
			return new ResponseEntity<String>("User password updated successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<AppException>(new AppException(e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
