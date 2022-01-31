package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.model.PostLike;

/***
 * Basic dao functionality included by default from JpaRepository
 * You can include your own custom queries following Spring documentation
 * {@link <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories">here</a>}
 * @author Colin Knox
 *
 */
@Repository
public interface PostLikeDao extends JpaRepository<PostLike, Integer> {
	
	/***
	 * Get the amount of likes for a given postId
	 * @param postId the id of the post to count the likes
	 * @return number of likes for the post
	 */
	@Query("select count(p) from PostLike p where p.postId = ?1")
	int countLikes(int postId);
}
