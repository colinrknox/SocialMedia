package com.revature.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.revature.model.UserProfile;
import com.revature.service.UserProfileService;

@RestController
@CrossOrigin
public class UserProfileController {

	UserProfileService serv;

	/***
	 * Authenticates a user's credentials by comparing them with the stored
	 * credentials in the database.
	 * 
	 * @param req  the request object to get the session variable
	 * @param json the form values in a key: value pair object
	 * @return HttpStatus.OK with the UserProfile object in the body as JSON on
	 *         success otherwise UNAUTHORIZED
	 */
	@PostMapping(value = "/login")
	public UserProfile login(HttpSession session, @RequestBody Map<String, Object> json) {
		if (json.get("email") == null || json.get("password") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing credentials");
		}
		UserProfile user = serv.authenticate((String) json.get("email"), (String) json.get("password"));
		if (user != null) {
			session.setAttribute("account", user);
			return user;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid login credentials");
		}
	}

	/***
	 * Register a new user account
	 * 
	 * @param user the body of the request mapped to a UserProfile object
	 * @return status code of OK on success otherwise 500 for exceptions
	 */
	@PostMapping(value = "/register")
	public UserProfile register(@RequestBody UserProfile user) {
		if (user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null
				|| user.getPassword() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing registration info");
		}
		return serv.save(user);
	}

	/***
	 * Remove session when user logs out
	 * 
	 * @param session
	 */
	@GetMapping(value = "/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpServletRequest req) {
		if (req.getSession(false) != null) {
			req.getSession(false).invalidate();
		}
	}

	@PostMapping(value = "/about/save")
	public void saveAbout(HttpSession session, @RequestBody String about) {
		UserProfile user = (UserProfile) session.getAttribute("account");
		session.setAttribute("account", serv.saveAbout(user, about));
	}

	/*
	 * searches for the user in the database by their id(primary key)
	 * 
	 * @author Luis R
	 */
	@GetMapping(value = "/user/{userId}")
	public UserProfile getUserProfileById(@PathVariable int userId) {
		try {
			return serv.findById(userId).get();
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with id: " + userId);
		}
	}
	
	@PostMapping(value = "/user/email")
	public UserProfile getUserProfileByEmail(@RequestBody String email) {
		UserProfile user = serv.findByEmail(email);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email doesn't match any users");
		}
		return user;
	}

	@GetMapping(value = "/myaccount")
	public UserProfile getMyProfile(HttpSession session) {
		UserProfile user = (UserProfile) session.getAttribute("account");
		return user;
	}

	@PostMapping(value = "/photo/save")
	public UserProfile saveProfileImage(HttpServletRequest req, @RequestBody byte[] img) throws RuntimeException {
		UserProfile user = (UserProfile) req.getSession().getAttribute("account");
		user = serv.saveProfileImage(user, img, req.getContentType());
		req.getSession().setAttribute("account", user);
		return user;
	}

	@PostMapping(value = "/password/recovery")
	public void passwordRecovery(@RequestBody String email) {
		if (serv.generateResetPassword(email) == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not found");
		}
	}

	@PostMapping(value = "/password/reset/{uuid}")
	public UserProfile changePassword(@PathVariable String uuid, @RequestBody String newPassword) {
		return serv.changeUserPassword(uuid, newPassword);
	}

	@Autowired
	public void setServ(UserProfileService serv) {
		this.serv = serv;
	}
}