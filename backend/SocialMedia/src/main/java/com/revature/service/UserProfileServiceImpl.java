package com.revature.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.revature.dao.ResetTokenDao;
import com.revature.dao.UserProfileDao;
import com.revature.model.ResetToken;
import com.revature.model.UserProfile;
import com.revature.utils.PasswordHash;
import com.revature.utils.S3SavePhoto;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private UserProfileDao repo;
	private PasswordHash hash;

	@Autowired
	private JavaMailSender mailSender;
	private ResetTokenDao tokenRepo;

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
	public UserProfile saveAbout(UserProfile user, String about) {
		user.setAbout(about);
		return repo.save(user);
	}

	@Override
	public UserProfile saveProfileImage(UserProfile user, byte[] img, String contentType) throws RuntimeException {
		S3SavePhoto s3Bucket = new S3SavePhoto(user);
		user = s3Bucket.savePhoto(img, contentType);
		return repo.save(user);
	}

	@Override
	public UserProfile generateResetPassword(String email) {
		UserProfile user = repo.findByEmail(email);
		if (user == null) {
			return null;
		}
		
		String token = UUID.randomUUID().toString();
		String subject = "Password Reset";
		String resetUrl = "http://localhost:9001/resetpassword.html?token=" + token;
		String body = "Please reset your email here:\r\n";
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper msgHelper = new MimeMessageHelper(message, true);
			msgHelper.setTo(email);
			msgHelper.setSubject(subject);
			msgHelper.setText(body + resetUrl);
			msgHelper.setFrom(System.getenv("SPRING_EMAIL"));
			mailSender.send(message);
			tokenRepo.save(new ResetToken(token, user));
		} catch (MailException e) {
			e.printStackTrace();
			return null;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	@Override
	public UserProfile changeUserPassword(String uuid, String newPassword) {
		// Get reset token
		ResetToken token = tokenRepo.getById(uuid);
		// Get user profile
		UserProfile user = token.getUser();
		String[] tokens = user.getPassword().split(":");
		// Hash the password
		hash = PasswordHash.builder()
				.setSalt(tokens[0])
				.setPassword(newPassword)
				.setIterations(tokens[2])
				.build();
		user.setPassword(hash.getDbPassword());
		// Set user profile's password to the new hash
		return repo.save(user);
	}

	public void setHash(PasswordHash hash) {
		this.hash = hash;
	}

	@Autowired
	public void setRepo(UserProfileDao repo) {
		this.repo = repo;
	}

	@Autowired
	public void setTokenRepo(ResetTokenDao tokenRepo) {
		this.tokenRepo = tokenRepo;
	}
}