package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.UserPost;
import com.revature.service.UserPostService;

@RestController
public class UserPostController {
	
	UserPostService serv;
	
	@GetMapping("/api/posts")
	public List<UserPost> getFriendsPosts() {
		return serv.findAllPostsDesc();
	}
	
	@Autowired
	public void setServ(UserPostService serv) {
		this.serv = serv;
	}
	
	//Added by LuisR
	@GetMapping("/api/userPosts")
	public List<UserPost> getUserPosts(int id)
	{
		return serv.findUserPostDesc(id);
	}
}
