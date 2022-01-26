package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.PostLikeDao;

@Service
public class UserPostServiceImpl implements UserPostService {
	
	private PostLikeDao likeRepo;
	
	@Override
	public int getPostLikes(int postId) {
		return likeRepo.countLikes(postId);
	}
	
	@Autowired
	public void setLikeRepo(PostLikeDao likeRepo) {
		this.likeRepo = likeRepo;
	}
}
