package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.dao.UserProfileDao;
import com.revature.model.UserProfile;
import com.revature.utils.PasswordHash;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

	@Mock
	private UserProfileDao userDaoMock;
	@Mock
	private PasswordHash hashedPwd;
	
	UserProfileServiceImpl serv;
	
	UserProfile mockProfile;
	String email = "user@gmail.com";
	String password = "password";
	
	@BeforeEach
	void setUp() throws Exception {
		serv = new UserProfileServiceImpl();
		serv.setRepo(userDaoMock);
	}
	
	@Test
	void testFindById() {
		int id = 1;
		when(userDaoMock.findById(id)).thenReturn(Optional.empty());
		
		Optional<UserProfile> actual = serv.findById(id);
		
		assertTrue(actual.isEmpty());
	}

}
