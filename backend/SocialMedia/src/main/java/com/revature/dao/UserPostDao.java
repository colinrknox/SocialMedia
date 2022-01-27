package com.revature.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.UserPost;

/***
 * Basic dao functionality included by default from JpaRepository
 * You can include your own custom queries following Spring documentation
 * {@link <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories">here</a>}
 * @author Colin Knox
 *
 */
@Repository
public interface UserPostDao extends JpaRepository<UserPost, Long> {

	public List<UserPost> findAllByOrderByCreationDateDesc();
}
