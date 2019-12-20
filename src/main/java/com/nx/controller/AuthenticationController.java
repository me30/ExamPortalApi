package com.nx.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nx.entity.RoleName;
import com.nx.entity.User;
import com.nx.exception.AppException;
import com.nx.payload.JwtAuthenticationResponse;
import com.nx.payload.LoginRequest;
import com.nx.payload.SignupRequest;
import com.nx.repository.UserRepository;
import com.nx.security.JwtTokenProvider;
import com.nx.service.EmailService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	
	@PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		
		/*try{
			if(loginRequest.getUsernameOrEmail().equals("") || loginRequest.getPassword().equals("")){
				throw new AppException("Invalid Username or password");
			}
			else
			{
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
						(loginRequest.getUsernameOrEmail(),
								loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				String jwt = tokenProvider.generateToken(authentication);
				return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	     }
		}
		catch(AppException e){
			return new ResponseEntity<String>("Custom Exception: "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}*/
    }
	
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if(userRepository.existsByUsername(signUpRequest.getUserName())) {
            return new ResponseEntity<String>("Username is already taken!",HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Email Address already in use!",HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        
        user.setUsername(signUpRequest.getUserName());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setGender(signUpRequest.getGender());
        user.setDob(signUpRequest.getDob());
        user.setEmail(signUpRequest.getEmail());

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        
        user.setRole(RoleName.User);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new String("User registered successfully"));
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
