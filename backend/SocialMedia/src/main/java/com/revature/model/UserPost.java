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
@Table(name = "user_posts")
public class UserPost implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int author;
	String text;
	String image;
	Date creationDate;
	
	public UserPost() {
		super();
	}

	public UserPost(int id, int author, String text, String image, Date creationDate) {
		super();
		this.id = id;
		this.author = author;
		this.text = text;
		this.image = image;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "UserPost [id=" + id + ", author=" + author + ", text=" + text + ", image=" + image + ", creationDate="
				+ creationDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, creationDate, id, image, text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPost other = (UserPost) obj;
		return author == other.author && Objects.equals(creationDate, other.creationDate) && id == other.id
				&& Objects.equals(image, other.image) && Objects.equals(text, other.text);
	}
}
