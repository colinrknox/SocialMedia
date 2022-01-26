package com.revature.service;

import java.util.List;

import com.revature.model.UserPost;

public interface UserPostService {

	int getPostLikes(int postId);
	
	List<UserPost> findAllPostsDesc();
}