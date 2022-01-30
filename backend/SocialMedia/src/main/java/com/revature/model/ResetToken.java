package com.revature.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reset_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetToken implements Serializable{
	@Id
	String uuid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profileId")
	UserProfile user;
}
