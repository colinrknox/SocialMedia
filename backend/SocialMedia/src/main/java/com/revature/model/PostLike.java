package com.revature.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "post_likes")
@IdClass(PostLikeId.class)
public class PostLike implements Serializable {
	
	@Id
	int profileId;
	@Id
	int postId;
	
	public PostLike() {
		super();
	}
	
	public PostLike(int id, int profileId, int postId) {
		super();
		this.profileId = profileId;
		this.postId = postId;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "PostLike [profileId=" + profileId + ", postId=" + postId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, profileId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostLike other = (PostLike) obj;
		return postId == other.postId && profileId == other.profileId;
	}
}
