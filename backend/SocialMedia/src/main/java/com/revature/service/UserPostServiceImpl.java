package com.revature.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.PostCommentDao;
import com.revature.dao.PostLikeDao;
import com.revature.dao.UserPostDao;
import com.revature.model.PostComment;
import com.revature.model.PostLike;
import com.revature.model.UserPost;
import com.revature.model.UserProfile;
import com.revature.utils.ProfanityFilter;

@Service
public class UserPostServiceImpl implements UserPostService {
	
	private PostLikeDao likeRepo;
	private UserPostDao postRepo;
	private PostCommentDao commentRepo;
	
	@Override
	public int getPostLikes(Integer postId) {
		return likeRepo.countLikes(postId);
	}
	
	@Override
	public List<UserPost> findAllPostsDesc() {
		return postRepo.findAllByOrderByCreationDateDesc();
	}

	//Added by LuisR
	@Override
	public List<UserPost> findUserPostsDesc(Integer author) {
		return postRepo.findByAuthorOrderByCreationDateDesc(author);
	}

	@Override
	public UserPost createPost(UserProfile user, UserPost post) {
		ProfanityFilter filter = new ProfanityFilter(post.getText());
		post.setCreationDate(Instant.now());
		post.setAuthor(user.getId());
		post.setText(filter.getFiltered());
		return postRepo.save(post);
	}

	@Override
	public PostLike createLike(Integer profileId, Integer postId) {
		PostLike like = new PostLike(profileId, postId);
		return likeRepo.save(like);
	}
	
	@Override
	public PostComment createComment(UserProfile user, PostComment comment) {
		comment.setAuthor(user.getId());
		comment.setCreationDate(Instant.now());
		return commentRepo.save(comment);
	}
	
	@Override
	public List<PostComment> getCommentsDesc(Integer postId)
	{
		return commentRepo.findByPostIdOrderByCreationDateDesc(postId);
	}
	
	@Autowired
	public void setLikeRepo(PostLikeDao likeRepo) {
		this.likeRepo = likeRepo;
	}
	
	@Autowired
	public void setPostRepo(UserPostDao postRepo) {
		this.postRepo = postRepo;
	}
	
	@Autowired
	public void setCommentRepo(PostCommentDao commentRepo) {
		this.commentRepo = commentRepo;
	}
}
