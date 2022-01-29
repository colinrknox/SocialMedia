package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.PostLike;
import com.revature.model.UserPost;
import com.revature.model.UserProfile;
import com.revature.service.UserPostService;

@RestController
public class UserPostController {
	
	UserPostService serv;
	
	@GetMapping("/api/allposts")
	public ResponseEntity<List<UserPost>> getFriendsPosts() {
		return new ResponseEntity<List<UserPost>>(serv.findAllPostsDesc(), HttpStatus.OK);
	}
	
	@PostMapping("/api/createpost")
	@ResponseStatus(value=HttpStatus.CREATED)
	public ResponseEntity<UserPost> createPost(HttpSession session, @RequestBody UserPost post ) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return new ResponseEntity<UserPost>(serv.createPost(user, post), HttpStatus.CREATED);
	}
	
	@PostMapping("/api/like/{postId}")
	@ResponseStatus(value=HttpStatus.CREATED)
	public ResponseEntity<PostLike> createLike(HttpSession session, @PathVariable Integer postId) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return new ResponseEntity<PostLike>(serv.createLike(user.getId(), postId), HttpStatus.OK);
	}
	
	//Added by LuisR
	@GetMapping("/api/posts/{author}")
	public ResponseEntity<List<UserPost>> getUserPosts(Integer author) {
		return new ResponseEntity<List<UserPost>>(serv.findUserPostsDesc(author), HttpStatus.OK);
	}
	
	@GetMapping("/api/myposts")
	public ResponseEntity<List<UserPost>> getMyPosts(HttpSession session) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return new ResponseEntity<List<UserPost>>(serv.findUserPostsDesc(user.getId()), HttpStatus.OK);
	}

	@Autowired
	public void setServ(UserPostService serv) {
		this.serv = serv;
	}
}
