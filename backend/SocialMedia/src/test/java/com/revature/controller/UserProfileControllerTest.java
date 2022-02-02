package com.revature.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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

import com.revature.model.UserProfile;
import com.revature.service.UserProfileService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserProfileControllerTest {
	
	UserProfileController controller;
	String email = "colin@gmail.com";
	String password = "password";
	String firstName = "Colin";
	String lastName = "Knox";
	
	@Mock
	private UserProfileService mockService;
	@Mock
	private HttpSession mockSession;
	@Mock
	private HttpServletRequest mockReq;


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
		json.put("email", email);
		json.put("password", password);
		Mockito.when(mockService.authenticate((String)json.get("email"), (String)json.get("password"))).thenReturn(null);
		
		ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
			controller.login(null, json);
		});
		
		String expectedReason = "Invalid login credentials";
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		String actualReason = e.getReason();
		HttpStatus actualStatus = e.getStatus();
		Integer actualCode = e.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}
	
	@Test
	void testLoginSuccess() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("email", "colin");
		json.put("password", "password");
		UserProfile initial = new UserProfile();
		initial.setEmail(email);
		initial.setPassword(password);
		UserProfile expected = new UserProfile();
		expected.setEmail(email);
		expected.setPassword(password);
		
		when(mockService.authenticate((String)json.get("email"), (String)json.get("password"))).thenReturn(initial);
		
		UserProfile actual = controller.login(mockSession, json);
		
		assertEquals(actual, expected);
		verify(mockSession).setAttribute("account", initial);
	}
	
	@Test
	void testLoginOnlyEmail() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("email", email);
		json.put("password", null);
		ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
			controller.login(null, json);
		});
		
		String expectedReason = "Missing credentials";
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		String actualReason = e.getReason();
		HttpStatus actualStatus = e.getStatus();
		Integer actualCode = e.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}
	
	@Test
	void testRegisterBadRequest() {
		UserProfile badReq = new UserProfile();
		badReq.setEmail(email);
		
		ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
			controller.register(badReq);
		});
		
		String expectedReason = "Missing registration info";
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		String actualReason = e.getReason();
		HttpStatus actualStatus = e.getStatus();
		Integer actualCode = e.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}
	
	@Test
	void testRegisterNoPassword() {
		UserProfile badReq = new UserProfile();
		badReq.setEmail(email);
		badReq.setFirstName(firstName);
		badReq.setLastName(lastName);
		
		ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
			controller.register(badReq);
		});
		
		String expectedReason = "Missing registration info";
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		String actualReason = e.getReason();
		HttpStatus actualStatus = e.getStatus();
		Integer actualCode = e.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}
	
	@Test
	void testRegisterSuccess() {
		UserProfile initial = new UserProfile();
		UserProfile expected = new UserProfile();
		initial.setEmail(email);
		expected.setEmail(email);
		initial.setFirstName(firstName);
		expected.setFirstName(firstName);
		initial.setLastName(lastName);
		expected.setLastName(lastName);
		initial.setPassword(password);
		expected.setPassword(password);
		when(mockService.save(initial)).thenReturn(initial);
		
		UserProfile actual = controller.register(initial);
		
		assertEquals(actual, expected);
	}
	
	@Test
	void testLogout() {
		when(mockReq.getSession(false)).thenReturn(null);
		
		controller.logout(mockReq);
		
		verify(mockReq, times(1)).getSession(false);
	}
	
	@Test
	void testNoSessionLogout() {
		when(mockReq.getSession(false)).thenReturn(mockSession);
		
		controller.logout(mockReq);
		
		verify(mockReq, times(2)).getSession(false);
		verify(mockSession, times(1)).invalidate();
	}
	
	@Test
	void testSaveAbout() {
		UserProfile initial = new UserProfile();
		when(mockSession.getAttribute("account")).thenReturn(initial);
		when(mockService.saveAbout(initial, "Random about text")).thenReturn(initial);
		
		controller.saveAbout(mockSession, "Random about text");
		
		verify(mockSession, times(1)).getAttribute("account");
		verify(mockSession, times(1)).setAttribute("account", initial);
		verify(mockService, times(1)).saveAbout(initial, "Random about text");
	}
	
	@Test
	void testGetUserProfileByIdNoneFound() {
		Optional<UserProfile> empty = Optional.empty();
		int userId = 1;
		when(mockService.findById(userId)).thenReturn(empty);
		
		ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
			controller.getUserProfileById(userId);
		});
		
		String expectedReason = "No account found with id: " + userId;
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		Integer expectedCode = 400;
		
		String actualReason = e.getReason();
		HttpStatus actualStatus = e.getStatus();
		Integer actualCode = e.getRawStatusCode();
		
		assertEquals(expectedReason, actualReason);
		assertEquals(expectedStatus, actualStatus);
		assertEquals(expectedCode, actualCode);
	}
	
	@Test
	void testGetUserProfileByIdSuccess() {
		UserProfile initial = new UserProfile();
		initial.setEmail(email);
		UserProfile expected = new UserProfile();
		expected.setEmail(email);
		Optional<UserProfile> exists = Optional.of(initial);
		int userId = 1;
		when(mockService.findById(userId)).thenReturn(exists);
		
		UserProfile actual = controller.getUserProfileById(userId);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetMyAccount() {
		UserProfile initial = new UserProfile();
		initial.setEmail(email);
		UserProfile expected = new UserProfile();
		expected.setEmail(email);
		when(mockSession.getAttribute("account")).thenReturn(initial);
		
		UserProfile actual = controller.getMyProfile(mockSession);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveProfileImage() {
		when(mockReq.getSession()).thenReturn(mockSession);
		when(mockReq.getContentType()).thenReturn("xd");
		
		controller.saveProfileImage(mockReq, null);
		
		verify(mockSession, times(1)).getAttribute("account");
		verify(mockSession, times(1)).setAttribute("account", null);
	}
	
	@Test
	void testPasswordRecovery() {
		controller.passwordRecovery(email);
		
		verify(mockService, times(1)).generateResetPassword(email);
	}
	
	@Test
	void testChangePassword() {
		controller.changePassword(email, password);
		
		verify(mockService, times(1)).changeUserPassword(email, password);
	}

}
