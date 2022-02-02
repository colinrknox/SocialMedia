package com.revature.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.revature.service.UserProfileService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserProfileControllerTest {
	
	UserProfileController controller;
	
	@Mock
	static UserProfileService mockService;
	@Mock
	HttpSession mockSession;


	@BeforeEach
	void setUp() throws Exception {
		controller = new UserProfileController();
		controller.setServ(mockService);
	}

	@Test
	public void testLoginBadRequest() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("email", null);
		json.put("password", null);
		Exception e = assertThrows(ResponseStatusException.class, () -> {
			controller.login(null, json);
		});
		
		String expectedReason = "Missing credentials";
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		ResponseStatusException rse = (ResponseStatusException) e;
		String actualReason = rse.getReason();
		HttpStatus actualStatus = rse.getStatus();
		Integer actualCode = rse.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}
	
	@Test
	public void testLoginBadCredentials() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("email", "colin");
		json.put("password", "password");
		Mockito.when(mockService.authenticate((String)json.get("email"), (String)json.get("password"))).thenReturn(null);
		
		Exception e = assertThrows(ResponseStatusException.class, () -> {
			controller.login(null, json);
		});
		
		String expectedReason = "Invalid login credentials";
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		ResponseStatusException rse = (ResponseStatusException) e;
		String actualReason = rse.getReason();
		HttpStatus actualStatus = rse.getStatus();
		Integer actualCode = rse.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}

}
