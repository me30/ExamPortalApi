package com.nx.serviceimpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nx.entity.User;
import com.nx.entity.UserProfile;
import com.nx.repository.UserProfileRepository;
import com.nx.repository.UserRepository;
import com.nx.service.BasicService;
import com.nx.service.UserProfileService;

@Service
public class UserProfileServiceImpl extends BasicService<UserProfile, UserProfileRepository> implements UserProfileService{

	@Autowired
	public UserRepository userRepository;

	@Override
	public UserProfile save(MultipartFile multipartFile,Long user_id) throws IOException {
		UserProfile doc = new UserProfile();
		doc.setFileName(multipartFile.getOriginalFilename());
		String extention = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		BufferedImage src = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
		File destination = new File("C:\\Users\\Admin\\Documents\\images\\"+multipartFile.getOriginalFilename());
		ImageIO.write(src, extention , destination);
		doc.setFilePath(destination.getPath());
		User user = userRepository.findById(user_id).orElse(null);
		doc.setUser(user);
		return repository.save(doc);
	}

	@Override
	public Optional<UserProfile> loadUserProfileByUserId(Long user_id) {
		return repository.loadUserProfileByUserId(user_id);
	}

}
