package com.revature.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.UserProfile;
import com.revature.service.UserProfileService;

@RestController
public class UserProfileController {

	UserProfileService serv;

	@PostMapping(value = "/login")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public UserProfile login(HttpServletRequest req, @RequestBody Map<String, Object> json) {
		UserProfile user = serv.authenticate((String)json.get("email"), (String)json.get("password"));
		if (user != null)
			req.getSession().setAttribute("account", user);
		return user;
	}

	@PostMapping(value = "/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserProfile register(@RequestBody UserProfile user) {
		UserProfile userModified = serv.save(user);
		return userModified;
	}

	@Autowired
	public void setServ(UserProfileService serv) {
		this.serv = serv;
	}
}
