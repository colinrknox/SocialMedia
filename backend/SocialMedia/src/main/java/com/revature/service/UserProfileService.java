package com.revature.service;

import java.util.Optional;

import com.revature.model.UserProfile;

public interface UserProfileService {

	Optional<UserProfile> findById(int id);

	UserProfile authenticate(String email, String password);

	UserProfile save(UserProfile user);
	
	void saveAbout(UserProfile user, String about);
	
	UserProfile saveProfileImage(UserProfile user, byte[] img, String contentType) throws RuntimeException;
}