package com.revature.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserProfileDao;
import com.revature.model.UserProfile;
import com.revature.utils.HashUtil;

@Service
public class UserProfileService {
	
	private UserProfileDao repo;
	
	public Optional<UserProfile> findById(int id) {
		return repo.findById((long) id);
	}
	
	public UserProfile authenticate(String email, String password) {
		UserProfile u = repo.findByEmail(email);
		if (u == null) {
			return null;
		}
		return HashUtil.validatePassword(password, u.getPassword()) ? u : null;
	}
	
	public UserProfile save(UserProfile user) {
		user.setCreationDate(new Date(System.currentTimeMillis()));
		user.setPassword(HashUtil.getHashedPassword(HashUtil.getSalt(), user.getPassword()));
		user.setEmail(user.getEmail().toLowerCase());
		return repo.save(user);
	}
	
	
	@Autowired
	public void setRepo(UserProfileDao repo) {
		this.repo = repo;
	}
}
