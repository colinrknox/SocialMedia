package com.revature.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Aspect
public class AuthenticationAdvice {

	HttpSession session;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMappingAdvice() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMappingAdvice() {
	}

	@Pointcut("!execution(* login(..))")
	public void notLoginAdvice() {
	}

	@Pointcut("!execution(* register(..))")
	public void notRegisterAdvice() {
	}
	
	@Pointcut("!execution(* generateResetPassword(..))")
	public void notGenReset() {
	}

	/**
	 * Aspect to send an UNAUTHORIZED response to the client if they aren't logged in for all controllers
	 * except those that don't require you to be logged in: login, register, password recovery
	 * @param jp the joinpoint for the aspect
	 * @return the method's normal return value if the user is logged in otherwise a new UNAUTHORIZED response
	 * @throws Throwable Different response codes based on different failures either here or in the controllers
	 */
	@Around("getMappingAdvice() || postMappingAdvice() && notRegisterAdvice() && notLoginAdvice() && notGenReset()")
	public Object ensureLoggedIn(ProceedingJoinPoint jp) throws Throwable {
		if (session.getAttribute("account") == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} else {
			return jp.proceed();
		}
	}

	@Autowired
	public void setSession(HttpSession session) {
		this.session = session;
	}
}
