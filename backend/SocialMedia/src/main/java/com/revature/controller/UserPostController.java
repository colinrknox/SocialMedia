package com.revature.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
	
//	@PostMapping("/posts/create")
//	public UserPost createPost(HttpSession session, @RequestBody UserPost post ) {
//		System.out.println("controller>/posts/create===>");
//		UserProfile user  = (UserProfile) session.getAttribute("account");
//		return serv.createPost(user, post);
//	}
	
	
	@PostMapping(path = "/posts/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public UserPost createPost(HttpServletRequest req, @RequestPart UserPost post, @RequestPart MultipartFile file) throws IOException {
			System.out.println("	@PostMapping post =>");
//			@RequestBody UserPost post, @RequestBody byte[] img) throws RuntimeException  {
//		UserPost post, @RequestBody byte[] img, @RequestBody String contentType
//		byte[] img = (byte[]) json.get("image");
//		UserPost post = (UserPost) json.get("post");
		System.out.println("UserPostController > post=>"+ post);
//		UserProfile user  = (UserProfile) session.getAttribute("account");
		
		
		
		byte[] img = file.getBytes();
		UserProfile user = (UserProfile) req.getSession().getAttribute("account");
		String contentType = req.getContentType();
		
		System.out.println("UserPostController > contentType=>"+ contentType);

		
		return serv.createPost(user, post, img, contentType);
		
//		UserProfile user, UserPost post, byte[] img, String contentType
	}
	
	@PostMapping("/posts/add/photo/{postId}")
	public void savePostImage(HttpServletRequest req, @RequestParam Integer postId, @RequestBody byte[] img) {
		if (img == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No image provided");
		}
		serv.addPostImage(postId, img, req.getContentType());
	}
	
	@PostMapping("/like/create/{postId}")
	public PostLike createLike(HttpSession session, @PathVariable Integer postId) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return serv.createLike(user.getId(), postId);
	}
	
	//Added by LuisR
	@GetMapping("/posts/{author}")

	public List<UserPost> getAuthorPosts(@PathVariable Integer author) {
		return serv.findUserPostsDesc(author);
	}
	
	@GetMapping("/posts/myposts")
	public List<UserPost> getMyPosts(HttpSession session) {
		UserProfile user  = (UserProfile) session.getAttribute("account");
		return serv.findUserPostsDesc(user.getId());
	}
	
	@PostMapping("/comments/create")
	public PostComment createUserPostComment(HttpSession session, @RequestBody PostComment comment) {
		System.out.println("controller > post ===>");
		UserProfile user = (UserProfile) session.getAttribute("account");
		return serv.createComment(user, comment);
	}
	
	@GetMapping("/comments/{postId}")
	public List<PostComment> getComments(@PathVariable Integer postId) {
		return serv.getCommentsDesc(postId);
	}
	
	@GetMapping("/likes/{postId}")
	public Integer getLikes(@PathVariable Integer postId) {
		return serv.getPostLikes(postId);
	}

	@Autowired
	public void setServ(UserPostService serv) {
		this.serv = serv;
	}
}
