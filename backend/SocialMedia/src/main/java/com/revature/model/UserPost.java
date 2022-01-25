package com.revature.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPost implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int author;
	String text;
	String image;
	Date creationDate;
}
