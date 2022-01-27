package com.revature.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
		serv.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/***
	 * Remove session when user logs out
	 * 
	 * @param session
	 */
	@PostMapping(value = "/logout")
	@ResponseStatus(HttpStatus.OK)
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@Autowired
	public void setServ(UserProfileService serv) {
		this.serv = serv;
	}

	@PostMapping(value = "/saveabout")
	public void saveAbout(HttpServletRequest req, String about) {
		UserProfile user = (UserProfile) req.getSession().getAttribute("account");
		serv.saveAbout(user, about);
	}
}
