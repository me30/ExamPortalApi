package com.nx.service;

import com.nx.entity.User;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.SignupRequest;

public interface UserService extends IFinder<User> , IService<User>{

	boolean existsByUsername(String username) throws Exception;

	boolean existsByEmail(String email) throws Exception;

	void registerUser(SignupRequest signUpRequest) throws Exception;

	void processForgotPassword(ForgotPasswordRequest useremail) throws Exception;

	void resetPassword(String token,String newPassword) throws Exception;

}
