package com.revature.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class PasswordHashTest {
	
	String testPass = "testPassword";
	String testPassTwo = "testpassword";
	
	PasswordHash testPassHash, testPassHashTwo;
	
	@Test
	public void testSamePassOnly() {
		testPassHash = PasswordHash.builder().setPassword(testPass).build();
		testPassHashTwo = PasswordHash.builder().setPassword(testPass).build();
		
		assertFalse(testPassHash.validate(testPassHashTwo.passBytes));
	}
	
	@Test
	public void testSamePassSameIter() {
		testPassHash = PasswordHash.builder().setIterations(1000).setPassword(testPass).build();
		testPassHashTwo = PasswordHash.builder().setIterations(1000).setPassword(testPass).build();
		
		assertFalse(testPassHash.validate(testPassHashTwo.passBytes));
	}
	
	@Test
	public void testAllSame() {
		testPassHash = PasswordHash.builder().setIterations(1000).setPassword(testPass).build();
		testPassHashTwo = PasswordHash.builder()
				.setIterations(1000)
				.setPassword(testPass)
				.setSalt(testPassHash.salt)
				.build();
		
		assertTrue(testPassHash.validate(testPassHashTwo.passBytes));
	}
	
	@Test
	public void testDiffPassOnly() {
		testPassHash = PasswordHash.builder().setIterations(1000).setPassword(testPass).build();
		testPassHashTwo = PasswordHash.builder()
				.setIterations(1000)
				.setPassword(testPassTwo)
				.setSalt(testPassHash.salt)
				.build();
		
		assertFalse(testPassHash.validate(testPassHashTwo.passBytes));
	}
	
	@Test
	public void testPasswordHashDefaults() {
		testPassHash = PasswordHash.builder()
				.setPassword(testPass)
				.build();
		testPassHashTwo = PasswordHash.builder()
				.setPassword(testPass)
				.build();
		
		assertFalse(Arrays.equals(testPassHash.passBytes, testPassHashTwo.passBytes));
		assertFalse(Arrays.equals(testPassHash.salt, testPassHashTwo.salt));
		assertNotEquals(testPassHash.dbPassword, testPassHashTwo.dbPassword);
	}
}
