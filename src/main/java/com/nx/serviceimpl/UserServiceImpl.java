package com.nx.serviceimpl;

import java.util.Date;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nx.entity.RoleName;
import com.nx.entity.User;
import com.nx.exception.AppException;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.ResetPasswordRequest;
import com.nx.payload.SignupRequest;
import com.nx.payload.UpdateUserPasswordRequest;
import com.nx.repository.UserRepository;
import com.nx.security.JwtTokenProvider;
import com.nx.service.BasicService;
import com.nx.service.EmailService;
import com.nx.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl extends BasicService<User, UserRepository> implements UserService {

	@Value("${mail.fromname}")
	private String fromName;

	@Value("${mail.subject}")
	private String mailSubject;

	@Value("${mail.text}")
	private String mailText;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Override
	public Page<User> search(Pageable pageable, String searchText) {
		String queriableText = new StringBuilder("%").append(searchText).append("%").toString();
		return repository.search(pageable, queriableText);
	}
	
	@Override
	public boolean existsByUsername(String username) throws Exception {
		return repository.existsByUserName(username);
	}

	@Override
	public boolean existsByEmail(String email) throws Exception {
		return repository.existsByEmail(email);
	}

	@Override
	public void registerUser(@Valid SignupRequest signUpRequest) throws Exception {
		// Creating user's account
		User user = new User();
		user.setUserName(signUpRequest.getUserName());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setGender(signUpRequest.getGender());
		user.setDob(signUpRequest.getDob());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(RoleName.User);
		repository.save(user);		
	}

	@Override
	public void processForgotPassword(ForgotPasswordRequest useremail) throws Exception {
		User user = repository.findByEmail(useremail.getemail());
		String tokenStr = Jwts.builder()
				/*.setAudience(useremail.getemail())
				.setSubject(Long.toString(1))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();*/
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();
		user.setResetToken(tokenStr);
		repository.save(user);
		emailForgotPassword(useremail, tokenStr);
	}

	@Async
	private void emailForgotPassword(ForgotPasswordRequest useremail, String tokenStr) throws Exception {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(useremail.getemail());
		passwordResetEmail.setSubject(mailSubject);
		passwordResetEmail.setText(mailText + tokenStr);
		emailService.sendEmail(passwordResetEmail);	
	}

	@Override
	public void resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception {
		
		User user = repository.findByResetToken(resetPasswordRequest.getToken());
		
		if(null!=user)
		{
			user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
			user.setResetToken("");
			repository.save(user);
			emailResetPassword(user.getEmail());
		}
		else
		{
			throw new AppException("User with token "+resetPasswordRequest.getToken()+" not found");
		}
	}

	@Async
	private void emailResetPassword(String useremail) throws Exception {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(useremail);
		passwordResetEmail.setSubject("Password reset successfully");
		passwordResetEmail.setText("Your password reset successfully..");
		emailService.sendEmail(passwordResetEmail);
	}

}
