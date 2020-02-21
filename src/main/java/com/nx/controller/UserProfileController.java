package com.nx.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.nx.entity.UserProfile;
import com.nx.service.UserProfileService;

@RestController
@RequestMapping("/userprofile")
@CrossOrigin("*")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;
	
	@GetMapping("/findAll")
	public List<UserProfile> findAll() {
		return userProfileService.findAll();
	}

	@GetMapping()
	public Page<UserProfile> findAll(Pageable pageable) {
		return userProfileService.findAll(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return userProfileService.findById(id)
				.map(userProfile -> ResponseEntity.ok().body(userProfile))
				.orElse(ResponseEntity.notFound().build());		
	}

	@PostMapping()
	public UserProfile save(@RequestBody UserProfile userProfile) {
		return userProfileService.save(userProfile);
	}

	@PostMapping("/upload/{id}")
	public UserProfile save(@RequestParam(value = "file")MultipartFile multipartFile,@PathVariable("id") Long user_id) throws IOException {
		return userProfileService.save(multipartFile, user_id);
	}
	
	@GetMapping("/getFileFromServer/{id}")
	public ResponseEntity<String> getUserProfile(@PathVariable("id") Long id) throws IOException {
		String images;
		String encodeBase64 = null;
		UserProfile userProfile = userProfileService.loadUserProfileByUserId(id).orElse(null);
		
		String extention = FilenameUtils.getExtension(userProfile.getFileName());
		File file = new File(userProfile.getFilePath());
		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes= new byte[(int)file.length()];
		inputStream.read(bytes);
		encodeBase64 = Base64.getEncoder().encodeToString(bytes);
		images = "data:image/"+extention+";base64,"+encodeBase64;
		inputStream.close();
		return new ResponseEntity<String>(images,HttpStatus.OK);	
	}
	
	@PutMapping()
	public UserProfile update(@RequestBody UserProfile userProfile) {
		return userProfileService.save(userProfile);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<UserProfile> db = userProfileService.findById(id);
		if(null == db){
			return new ResponseEntity<String>("Id not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		userProfileService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}