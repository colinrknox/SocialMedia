package com.revature.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/api/posts")
	public List<UserPost> getFriendsPosts() {
		return serv.findAllPostsDesc();
	}
	
	@PostMapping("/api/createpost")
	@ResponseStatus(value=HttpStatus.CREATED)
	public String createPost(HttpSession session, @RequestBody UserPost post ) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		UserProfile user  = (UserProfile) session.getAttribute("account");
		String text = post.getText();
		String image = post.getImage();
		return serv.createPost(user.getId(), text, image, timestamp);
	}
	
	@PostMapping("/api/createlike")
	@ResponseStatus(value=HttpStatus.CREATED)
	public String createLike(HttpSession session, @RequestBody PostLike like ) {
		
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return serv.createLike(user.getId(), like.getPostId());
	}
	
	@Autowired
	public void setServ(UserPostService serv) {
		this.serv = serv;
	}
	
	//Added by LuisR
	@GetMapping("/api/userPosts")
	public List<UserPost> getUserPosts(int id)
	{
		return serv.findCertainUserPostDesc(id);
	}
	
	//added by Luis R
	@GetMapping("/api/allUserPosts")
	public List<UserPost> getAllPostsByAuthor(int author)
	{
		return serv.findAllPostsOfUser(author);
	}
}
