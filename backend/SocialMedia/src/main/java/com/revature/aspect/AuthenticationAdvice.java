package com.revature.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

	@Around("getMappingAdvice() || postMappingAdvice() && notRegisterAdvice() && notLoginAdvice()")
	public ResponseEntity<Object> ensureLoggedIn(ProceedingJoinPoint jp) throws Throwable {
		if (session.getAttribute("account") == null) {
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		} else {
			return (ResponseEntity<Object>) jp.proceed();
		}
	}

	@Autowired
	public void setSession(HttpSession session) {
		this.session = session;
	}
}
