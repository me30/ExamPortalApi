package com.nx.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.nx.entity.User;
import com.nx.entity.UserProfile;

public interface UserProfileService extends IFinder<UserProfile>,IService<UserProfile> {

	UserProfile save(MultipartFile multipartFile,Long user_id) throws IOException;
	
	Optional<UserProfile> loadUserProfileByUserId(Long user_id);
	
}
