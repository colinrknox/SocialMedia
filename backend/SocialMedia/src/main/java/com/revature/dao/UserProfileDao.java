package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.UserProfile;

/***
 * Basic dao functionality included by default from JpaRepository
 * You can include your own custom queries following Spring documentation
 * {@link <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories">here</a>}
 * @author Colin Knox
 *
 */
@Repository
public interface UserProfileDao extends JpaRepository<UserProfile, Long> {
	
	/***
	 * Custom query interpreted by Spring JPA to lookup a user by email (natural key) that
	 * should always result in 0 or 1 users
	 * @param email string of user's email to search for in database
	 * @return a User object with a matching email or null?
	 */
	public UserProfile findByEmail(String email);
	
	/*Added by Luis R
	 *method for finding userprofile by their id(primary key)
	 *param the user id
	 *returns a user object of that id
	*/
	public UserProfile findByid(int id);
}
