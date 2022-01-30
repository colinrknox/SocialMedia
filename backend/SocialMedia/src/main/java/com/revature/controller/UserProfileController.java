package com.revature.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.UserProfile;
import com.revature.service.UserProfileService;

@RestController
public class UserProfileController {

	UserProfileService serv;

	/***
	 * Authenticates a user's credentials by comparing them with the stored
	 * credentials in the database.
	 * 
	 * @param req  the request object to get the session variable
	 * @param json the form values in a key: value pair object
	 * @return HttpStatus.OK with the UserProfile object in the body as JSON on
	 *         success otherwise UNAUTHORIZED
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<UserProfile> login(HttpServletRequest req, @RequestBody Map<String, Object> json) {
		UserProfile user = serv.authenticate((String) json.get("email"), (String) json.get("password"));
		if (user != null) {
			req.getSession().setAttribute("account", user);
			return new ResponseEntity<UserProfile>(user, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	/***
	 * Register a new user account
	 * 
	 * @param user the body of the request mapped to a UserProfile object
	 * @return status code of CREATED on success otherwise 500 for exceptions
	 */
	@PostMapping(value = "/register")
	public ResponseEntity<UserProfile> register(@RequestBody UserProfile user) {
		return new ResponseEntity<UserProfile>(serv.save(user), HttpStatus.OK);
	}

	/***
	 * Remove session when user logs out
	 * 
	 * @param session
	 */
	@GetMapping(value = "/logout")
	public ResponseEntity<Object> logout(HttpServletRequest req) {
		if (req.getSession(false) != null) {
			req.getSession(false).invalidate();
		}
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/about/save")
	public ResponseEntity<Object> saveAbout(HttpServletRequest req, @RequestBody String about) {
		UserProfile user = (UserProfile) req.getSession().getAttribute("account");
		req.getSession().setAttribute("account", serv.saveAbout(user, about));
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	
	/* 
	 * searches for the user in the database by their id(primary key)
	 * @author Luis R
	 */
	@GetMapping(value = "/user/{userId}")
	public ResponseEntity<Optional<UserProfile>> getUserProfileById(@PathVariable int userId) {
		return new ResponseEntity<Optional<UserProfile>>(serv.findById(userId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/myaccount")
	public ResponseEntity<UserProfile> getMyProfile(HttpSession session) {
		UserProfile user = (UserProfile) session.getAttribute("account");
		return new ResponseEntity<UserProfile>(user, HttpStatus.OK);
	}
	
	@PostMapping(value = "/photo/save")
	public ResponseEntity<Object> saveProfileImage(HttpServletRequest req, @RequestBody byte[] img) throws RuntimeException {
		UserProfile user = (UserProfile) req.getSession().getAttribute("account");
		req.getSession().setAttribute("account", serv.saveProfileImage(user, img, req.getContentType()));
		return new ResponseEntity<Object>(req.getSession().getAttribute("account"), HttpStatus.OK);
	}
	
	@PostMapping(value = "/resetpassword")
	public ResponseEntity<Object> resetPassword(@RequestBody String email) {
		System.out.println(email);
		serv.generateResetPassword(email);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	@Autowired
	public void setServ(UserProfileService serv) {
		this.serv = serv;
	}
}
