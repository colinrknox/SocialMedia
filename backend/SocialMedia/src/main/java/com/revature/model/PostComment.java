package com.revature.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "post_comments")
public class PostComment implements Serializable {

	private static final long serialVersionUID = 5685376749684752639L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int author;
	String text;
	int postId;
	Date creationDate;
	
	public PostComment() {
		super();
	}

	public PostComment(int id, int author, String text, int postId, Date creationDate) {
		super();
		this.id = id;
		this.author = author;
		this.text = text;
		this.postId = postId;
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "PostComment [id=" + id + ", author=" + author + ", text=" + text + ", postId=" + postId + ", creationDate=" + creationDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, id, postId, text, creationDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostComment other = (PostComment) obj;
		return author == other.author && id == other.id && postId == other.postId && Objects.equals(text, other.text) && Objects.equals(creationDate, other.creationDate);
	}
}
