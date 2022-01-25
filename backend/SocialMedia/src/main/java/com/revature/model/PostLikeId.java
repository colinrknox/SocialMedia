package com.revature.model;

import java.io.Serializable;
import java.util.Objects;

public class PostLikeId implements Serializable {
	private int profileId;
	private int postId;
	
	public PostLikeId() {
		super();
	}

	public PostLikeId(int profileId, int postId) {
		super();
		this.profileId = profileId;
		this.postId = postId;
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
		PostLikeId other = (PostLikeId) obj;
		return postId == other.postId && profileId == other.profileId;
	}
	
	
}
