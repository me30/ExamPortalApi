package com.nx.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nx.entity.RoleName;
import com.nx.entity.User;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.JwtAuthenticationResponse;
import com.nx.payload.LoginRequest;
import com.nx.payload.SignupRequest;
import com.nx.repository.UserRepository;
import com.nx.security.JwtTokenProvider;
import com.nx.service.EmailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
	
	@Value("${mail.fromname}")
	private String fromName;
	
	@Value("${mail.subject}")
	private String mailSubject;
	
	@Value("${mail.text}")
	private String mailText;
	
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

        userRepository.save(user);

        return new ResponseEntity<String>("User registered successfully", HttpStatus.OK);
    }

	@PostMapping("/forgotpassword")
	public String processForgotPasswordForm(@Valid @RequestBody ForgotPasswordRequest userEmail,HttpServletRequest request) {
		
		User user = userRepository.findByEmail(userEmail.getemail());
		
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 300000);

		String tokenStr = Jwts.builder()
				.setAudience(userEmail.getemail())
				.setSubject(Long.toString(1))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();
		
		user.setResetToken(tokenStr);
				
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(user.getEmail());
		passwordResetEmail.setSubject(mailSubject);
		passwordResetEmail.setText(mailText + tokenStr);
		emailService.sendEmail(passwordResetEmail);	
		return user.getEmail();
	}
	
	@PutMapping(value = "/reset/{token}")
	public ResponseEntity<?> resetPassword(@RequestBody String newPassword,@PathVariable("token") String tokenStr) {
		
		Claims claims = Jwts.parser()
				.setSigningKey("926D96C90030DD58429D2751AC1BDBBC")
				.parseClaimsJws(tokenStr)
				.getBody();
		
		System.out.println("claims:"+claims.getAudience());
		
		String email = claims.getAudience();
		
		//Long userId = Long.parseLong(claims.getSubject());		to get userId
		
		User user = userRepository.findByEmail(email);
		
		if(null!=user)
		{
			user.setPassword(null!=user.getPassword()?passwordEncoder.encode(newPassword):user.getPassword());
			
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom(fromName);
			passwordResetEmail.setTo(email);
			passwordResetEmail.setSubject("Password reset successfully");
			passwordResetEmail.setText("Your password reset successfully..");
			emailService.sendEmail(passwordResetEmail);	
			
			return new ResponseEntity<String>("Password reset successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("User not found!!!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*@PutMapping(value = "/reset")
	public User setNewPassword(RedirectAttributes redir,@RequestBody String userEmail) {
		User user = userRepository.findByEmail(userEmail);
		if(null!=user){
			user.setEmail(userEmail);
		}
		return user;
	}*/
}
