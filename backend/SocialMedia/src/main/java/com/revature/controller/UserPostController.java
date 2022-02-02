package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.PostComment;
import com.revature.model.PostLike;
import com.revature.model.UserPost;
import com.revature.model.UserProfile;
import com.revature.service.UserPostService;

@RestController
@RequestMapping("api")
@CrossOrigin
public class UserPostController {
	
	UserPostService serv;
	
	@GetMapping("/posts/all")
	public List<UserPost> getFriendsPosts() {
		return serv.findAllPostsDesc();
	}
	
	@PostMapping("/posts/create")
	public UserPost createPost(HttpSession session, @RequestBody UserPost post ) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return serv.createPost(user, post);
	}
	
	@PostMapping("/like/create/{postId}")
	public PostLike createLike(HttpSession session, @PathVariable Integer postId) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return serv.createLike(user.getId(), postId);
	}
	
	//Added by LuisR
	@GetMapping("/posts/{author}")

	public ResponseEntity<List<UserPost>> getAuthorPosts(@PathVariable Integer author) 
	{
		return new ResponseEntity<List<UserPost>>(serv.findUserPostsDesc(author), HttpStatus.OK);
	}
	
	@GetMapping("/posts/myposts")
	public List<UserPost> getMyPosts(HttpSession session) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return serv.findUserPostsDesc(user.getId());
	}
	
	@PostMapping("/comments/create")
	public PostComment createUserPostComment(HttpSession session, @RequestBody PostComment comment) {
		UserProfile user = (UserProfile) session.getAttribute("account");
		return serv.createComment(user, comment);
	}

	@Autowired
	public void setServ(UserPostService serv) {
		this.serv = serv;
	}
}
