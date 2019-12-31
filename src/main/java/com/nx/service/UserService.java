package com.nx.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nx.entity.User;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.ResetPasswordRequest;
import com.nx.payload.SignupRequest;

public interface UserService extends IFinder<User> , IService<User>{

	Page<User> search(Pageable pageable, String searchText);

	boolean existsByUsername(String username) throws Exception;

	boolean existsByEmail(String email) throws Exception;

	void registerUser(SignupRequest signUpRequest) throws Exception;

	void processForgotPassword(ForgotPasswordRequest useremail) throws Exception;

	void resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception;

}
