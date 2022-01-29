package com.revature.service;

import java.sql.Timestamp;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.PostLikeDao;
import com.revature.dao.UserPostDao;
import com.revature.model.PostLike;
import com.revature.model.UserPost;




@Service
public class UserPostServiceImpl implements UserPostService {
	
	private PostLikeDao likeRepo;
	private UserPostDao postRepo;
	
	public UserPostServiceImpl (final UserPostDao postRepo){
	        this.postRepo = postRepo;
	}
	
	@Override
	public int getPostLikes(int postId) {
		return likeRepo.countLikes(postId);
	}
	
	@Override
	public List<UserPost> findAllPostsDesc() {
		return postRepo.findAllByOrderByCreationDateDesc();
	}

	//Added by LuisR
	@Override
	public List<UserPost> findCertainUserPostDesc(int id) {
		return postRepo.findAllById(id);
	}
	
	//added by Luis R
	@Override
	public List<UserPost> findAllPostsOfUser(int author) {
		return postRepo.findAllByAuthor(author);
	}

	@Override
	public String createPost(int author, String text, String image, Timestamp creationDate) {
		int id = 0;
		UserPost post = new UserPost(id, author, text, image, creationDate);
		postRepo.save(post);
		return "Post Created";
	}

	@Override
	public String createLike(int profileId, int postId) {
		PostLike like = new PostLike(profileId, postId);
		likeRepo.save(like);
		return "Like Created";

	}
	
	@Autowired
	public void setLikeRepo(PostLikeDao likeRepo) {
		this.likeRepo = likeRepo;
	}
}
