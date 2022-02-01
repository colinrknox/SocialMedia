package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.model.ResetToken;

public interface ResetTokenDao extends JpaRepository<ResetToken, String>{

}
