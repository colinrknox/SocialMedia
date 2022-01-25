package com.revature.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "post_likes")
public class PostLike implements Serializable {

	private static final long serialVersionUID = -4300187187354323663L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int profileId;
	int postId;
	
	public PostLike() {
		super();
	}
	
	public PostLike(int id, int profileId, int postId) {
		super();
		this.id = id;
		this.profileId = profileId;
		this.postId = postId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "PostLike [id=" + id + ", profileId=" + profileId + ", postId=" + postId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, postId, profileId);
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
		return id == other.id && postId == other.postId && profileId == other.profileId;
	}
}
