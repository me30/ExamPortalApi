package com.nx.serviceimpl;

import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nx.entity.RoleName;
import com.nx.entity.User;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.SignupRequest;
import com.nx.repository.UserRepository;
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
	
	@Override
	public boolean existsByUsername(String username) throws Exception {
		return repository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) throws Exception {
		return repository.existsByEmail(email);
	}

	@Override
	public void registerUser(@Valid SignupRequest signUpRequest) throws Exception {
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
        repository.save(user);		
	}

	@Override
	public void processForgotPassword(ForgotPasswordRequest useremail) throws Exception {
		User user = repository.findByEmail(useremail.getemail());
		String tokenStr = Jwts.builder()
				.setAudience(useremail.getemail())
				.setSubject(Long.toString(1))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();
		user.setResetToken(tokenStr);
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

}
