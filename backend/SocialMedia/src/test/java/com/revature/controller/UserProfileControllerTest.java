package com.revature.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revature.model.UserProfile;
import com.revature.service.UserProfileService;

class UserProfileControllerTest {
	
	UserProfileController controller = new UserProfileController();
	static UserProfileService mockService;
	static HttpServletRequest mockReq;
	static HttpSession mockSession;
	Map<String, Object> mockJson;
	static UserProfile mockUser;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mockService = Mockito.mock(UserProfileService.class);
		mockReq = Mockito.mock(HttpServletRequest.class);
		mockUser = Mockito.mock(UserProfile.class);
		mockSession = Mockito.mock(HttpSession.class);
	}

	@BeforeEach
	void setUp() throws Exception {
		mockJson = new HashMap<>();
		mockJson.put("username", "user");
		mockJson.put("password", "password");
		controller.setServ(mockService);
	}

	@Test
	void testLoginSuccess() {
		Mockito.when(mockService.authenticate((String)mockJson.get("email"), (String)mockJson.get("password"))).thenReturn(mockUser);
		Mockito.when(mockReq.getSession()).thenReturn(mockSession);
		
		ResponseEntity<UserProfile> resp = controller.login(mockReq, mockJson);
		
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
		assertEquals(resp.getBody(), mockUser);
	}
	
	@Test
	void testLoginFail() {
		Mockito.when(mockService.authenticate((String)mockJson.get("email"), (String)mockJson.get("password"))).thenReturn(null);
		
		ResponseEntity<UserProfile> resp = controller.login(mockReq, mockJson);
		
		assertEquals(resp.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	void testRegister() {
		Mockito.when(mockService.save(mockUser)).thenReturn(mockUser);
		
		ResponseEntity<UserProfile> resp = controller.register(mockUser);
		
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}

}
