package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.PostLikeDao;
import com.revature.dao.UserPostDao;
import com.revature.model.UserPost;

@Service
public class UserPostServiceImpl implements UserPostService {
	
	private PostLikeDao likeRepo;
	private UserPostDao postRepo;
	
	@Override
	public int getPostLikes(int postId) {
		return likeRepo.countLikes(postId);
	}
	
	@Override
	public List<UserPost> findAllPostsDesc() {
		return postRepo.findAllByOrderByCreationDateDesc();
	}
	
	@Autowired
	public void setLikeRepo(PostLikeDao likeRepo) {
		this.likeRepo = likeRepo;
	}

	//Added by LuisR
	@Override
	public List<UserPost> findUserPostDesc(int id) 
	{
		return postRepo.findAllByidOrderByCreationDateDesc(id);
	}
}
