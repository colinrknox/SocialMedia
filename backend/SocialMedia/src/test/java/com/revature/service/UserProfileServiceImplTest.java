package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.dao.UserProfileDao;
import com.revature.model.UserProfile;
import com.revature.utils.PasswordHash;

class UserProfileServiceImplTest {

	static UserProfileDao userDaoMock;
	UserProfileServiceImpl serv;
	
	UserProfile mockProfile;
	static String email;
	static String password;
	
	static PasswordHash hashedPassword;
		
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		email = "user@gmail.com";
		password = "password";
		userDaoMock = Mockito.mock(UserProfileDao.class);
		hashedPassword = new PasswordHash.Builder().setPassword(password).build();
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
		mockProfile.setPassword(hashedPassword.getDbPassword());
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
		mockProfile.setPassword(password);
		mockProfile.setEmail(email.toUpperCase());
		
		assertNull(mockProfile.getCreationDate());
		
		Mockito.when(userDaoMock.save(mockProfile)).thenReturn(mockProfile);
		
		UserProfile validate = serv.save(mockProfile);
		
		assertEquals(email, validate.getEmail());
		assertNotNull(mockProfile.getCreationDate());
		assertNotEquals(mockProfile.getPassword(), password);
	}
	
	@Test
	void testSaveFailure() {
		Mockito.when(userDaoMock.save(mockProfile)).thenReturn(null);
		
		UserProfile validate = serv.save(mockProfile);
		
		assertNull(validate);
	}

}
