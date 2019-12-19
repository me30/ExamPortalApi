package com.nx.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nx.entity.User;
import com.nx.repository.UserRepository;
import com.nx.service.EmailService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/authenticate")
	public ResponseEntity<User> getUserbyUsernameandPassword(@RequestBody User user ) {
		User response = userRepository.getUserbyUsernameandPassword(user.getUsername(), user.getPassword());
		if (response == null)
	            return new ResponseEntity("Password not matched!!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(response, HttpStatus.OK);
	}

	@GetMapping("/forgotpass/{email}")
	public String processForgotPasswordForm(@PathVariable("email") String userEmail,HttpServletRequest request) {
		User user = userRepository.findByEmail(userEmail);
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom("krupa.j.java@gmail.com");
		passwordResetEmail.setTo(user.getEmail());
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("To reset your password, click the link below:\n" + 
				"http://localhost:4200/reset?token=" + user.getEmail());
		emailService.sendEmail(passwordResetEmail);	
		return userEmail;
	}

	@PutMapping(value = "/reset")
	public User setNewPassword(RedirectAttributes redir,@RequestBody String userEmail) {
		System.out.println("data" + userEmail);
		User user = userRepository.findByEmail(userEmail);
		return user;
	}
}
