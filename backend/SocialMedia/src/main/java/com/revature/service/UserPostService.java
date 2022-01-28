package com.revature.service;


import java.sql.Timestamp;

import java.util.List;

import com.revature.model.UserPost;


public interface UserPostService {

	int getPostLikes(int postId);
	String createPost(int author, String text, String image, Timestamp creationDate);
	String createLike(int profileId, int postId);
	List<UserPost> findAllPostsDesc();

}