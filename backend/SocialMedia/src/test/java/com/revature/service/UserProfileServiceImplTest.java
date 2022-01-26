package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.dao.UserProfileDao;
import com.revature.model.UserProfile;

class UserProfileServiceImplTest {

	@MockBean
	static UserProfileDao userDaoMock;
	UserProfileServiceImpl serv;
	
	UserProfile mockProfile;
	static String email;
	static String password;
	
	static String hashedPassword;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		email = "user@gmail.com";
		password = "password";
		userDaoMock = Mockito.mock(UserProfileDao.class);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		serv = new UserProfileServiceImpl();
		serv.setRepo(userDaoMock);
		mockProfile = new UserProfile();
		mockProfile.setEmail(email);
		mockProfile.setPassword(hashedPassword);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAuthenticateSuccess() {
		Mockito.when(userDaoMock.findByEmail(email)).thenReturn(mockProfile);
		
		UserProfile validate = serv.authenticate(email, password);
		
		assertEquals(validate, mockProfile);
	}
	
	@Test
	void testAuthenticateFailure() {
		Mockito.when(userDaoMock.findByEmail(email)).thenReturn(null);
		
		UserProfile validate = serv.authenticate(email, password);
		
		assertEquals(validate, null);
	}
	
	@Test
	void testSaveSuccess() {
		Mockito.when(userDaoMock.save(mockProfile)).thenReturn(mockProfile);
		mockProfile.setPassword(password);
		mockProfile.setEmail(email.toUpperCase());
		
		UserProfile validate = serv.save(mockProfile);
		
		assertEquals(email, validate.getEmail());
		assertEquals(hashedPassword, validate.getPassword());
	}
	
	@Test
	void testSaveFailure() {
		
	}

}
