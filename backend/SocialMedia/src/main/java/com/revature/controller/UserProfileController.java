package com.revature.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.UserProfile;
import com.revature.service.UserProfileService;

@RestController
public class UserProfileController {

	UserProfileService serv;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody Map<String, Object> json) throws IOException {
		UserProfile user = serv.authenticate((String)json.get("email"), (String)json.get("password"));
		return new ObjectMapper().writeValueAsString(user);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestBody UserProfile user) throws JsonProcessingException {
		UserProfile userModified = serv.save(user);
		return new ObjectMapper().writeValueAsString(userModified);
	}

	@Autowired
	public void setServ(UserProfileService serv) {
		this.serv = serv;
	}
}
