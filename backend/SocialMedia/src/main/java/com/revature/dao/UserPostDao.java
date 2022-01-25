package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.UserPost;

@Repository
public interface UserPostDao extends JpaRepository<UserPost, Long> {

}
