package com.revature.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserProfileDao;
import com.revature.model.UserProfile;
import com.revature.utils.PasswordHash;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	private UserProfileDao repo;
	private PasswordHash hash;
	
	@Override
	public Optional<UserProfile> findById(int id) {
		return repo.findById(id);
	}
	
	@Override
	public UserProfile authenticate(String email, String password) {
		UserProfile u = repo.findByEmail(email);
		if (u == null) {
			return null;
		}
		String[] tokens = u.getPassword().split(":");
		hash = PasswordHash.builder()
				.setSalt(tokens[1])
				.setPassword(password)
				.setIterations(tokens[0])
				.build();
		return hash.validate(tokens[2]) ? u : null;
	}
	
	@Override
	public UserProfile save(UserProfile user) {
		hash = PasswordHash.builder()
				.setPassword(user.getPassword())
				.build();
		user.setCreationDate(Instant.now());
		user.setPassword(hash.getDbPassword());
		user.setEmail(user.getEmail().toLowerCase());
		return repo.save(user);
	}
	
	@Override
	public void saveAbout(UserProfile user, String about) {
		user.setAbout(about);
	}

	@Override
	public UserProfile saveProfileImage(UserProfile user, byte[] img, String contentType) throws RuntimeException {
		S3Client client = S3Client.builder()
				.region(Region.US_EAST_2)
				.build();
		
		List<Bucket> buckets = client.listBuckets().buckets();
		if (buckets.isEmpty()) {
			throw new RuntimeException("No AWS buckets found");
		}
		String key = "profile/" + String.valueOf(user.getId());
		PutObjectRequest req = PutObjectRequest.builder()
				.bucket(buckets.get(0).name())
				.key(key)
				.contentType(contentType)
				.build();
		
		client.putObject(req, RequestBody.fromBytes(img));
		user.setPhoto("https://" + buckets.get(0).name() + ".s3." + Region.US_EAST_2.toString() + ".amazonaws.com/" + key);
		return repo.save(user);
	}
	
	public void setHash(PasswordHash hash) {
		this.hash = hash;
	}
	
	@Autowired
	public void setRepo(UserProfileDao repo) {
		this.repo = repo;
	}
}
