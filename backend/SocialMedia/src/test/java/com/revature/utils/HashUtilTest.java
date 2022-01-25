package com.revature.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class HashUtilTest {
	
	String testPass = "testPassword";
	String testPassTwo = "testpassword";
	int iterations = 1000;
	
	byte[] saltOne = HashUtil.getSalt(), saltTwo = HashUtil.getSalt();
	
	@Test
	void testGetSalt() {
		byte[] saltOneLocal = HashUtil.getSalt();
		byte[] saltTwoLocal = HashUtil.getSalt();
		
		assertNotEquals(saltOneLocal, saltTwoLocal);
		assertEquals(saltOneLocal.length, saltTwoLocal.length, 16);
	}
	
	@Test
	void testGetHashedPasswordSameSaltSamePwd() {
		String hashOne = HashUtil.getHashedPassword(saltOne, testPass);
		String hashTwo = HashUtil.getHashedPassword(saltOne, testPass);
		
		assertEquals(hashOne, hashTwo);
	}
	
	@Test
	void testGetHashedPasswordSameSaltDiffPwd() {
		String hashOne = HashUtil.getHashedPassword(saltOne, testPass);
		String hashTwo = HashUtil.getHashedPassword(saltOne, testPassTwo);
		
		assertNotEquals(hashOne, hashTwo);
	}
	
	@Test
	void testGetHashedPasswordDiffSaltSamePwd() {
		String hashOne = HashUtil.getHashedPassword(saltOne, testPass);
		String hashTwo = HashUtil.getHashedPassword(saltTwo, testPass);
		
		assertNotEquals(hashOne, hashTwo);
	}
	
	@Test
	void testValidatePasswordValid() {
		String hashOne = HashUtil.getHashedPassword(saltOne, testPass);
		boolean valid = HashUtil.validatePassword(testPass, hashOne);
		assertTrue(valid);
	}
	
	@Test
	void testValidPasswordNotValid() {
		String hashOne = HashUtil.getHashedPassword(saltOne, testPass);
		boolean valid = HashUtil.validatePassword(testPassTwo, hashOne);
		assertFalse(valid);
	}
	
	@Test
	void testGetHashedPasswordBytesSameSaltSamePwd() {
		byte[] hashOne = HashUtil.getHashedPasswordBytes(saltOne, testPass, iterations);
		byte[] hashTwo = HashUtil.getHashedPasswordBytes(saltOne, testPass, iterations);
		
		assertArrayEquals(hashOne, hashTwo);
	}
	
	@Test
	void testGetHashedPasswordBytesSameSaltDiffPwd() {
		byte[] hashOne = HashUtil.getHashedPasswordBytes(saltOne, testPass, iterations);
		byte[] hashTwo = HashUtil.getHashedPasswordBytes(saltOne, testPassTwo, iterations);
		
		assertFalse(Arrays.equals(hashOne, hashTwo));
	}
	
	@Test
	void testGetHashedPasswordBytesDiffSaltSamePwd() {
		byte[] hashOne = HashUtil.getHashedPasswordBytes(saltOne, testPass, iterations);
		byte[] hashTwo = HashUtil.getHashedPasswordBytes(saltTwo, testPass, iterations);
		
		assertFalse(Arrays.equals(hashOne, hashTwo));
	}

}
