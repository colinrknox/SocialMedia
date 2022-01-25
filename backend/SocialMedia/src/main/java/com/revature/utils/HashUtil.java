package com.revature.utils;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

/***
 * Hashing utility class with static methods to aid make password storage more secure
 * for users.  In addition, it offers the ability to validate plain text passwords
 * with the persistent hashed password strings stored in the database.
 * 
 * @author Colin Knox
 *
 */
public class HashUtil {
	
	/***
	 * Returns a 16 byte salt for password hashing
	 * Every call is a unique result
	 * @return
	 */
	public static byte[] getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
	
	/***
	 * Takes a salt and password and gets the hashed password in a hex string
	 * @param salt the salt to be added to the password hash
	 * @param password the user's password in plain text form
	 * @return the string to be persisted as the user's password e.g {iterations:salt:hashedPassword}
	 * 			NOTE: salt and hashedPassword are stored as hex strings of byte arrays
	 */
	public static String getHashedPassword(byte[] salt, String password) {
		int iterations = 65536;
		byte[] hash = getHashedPasswordBytes(salt, password, iterations);
		return iterations + ":" + DatatypeConverter.printHexBinary(salt) + ":" + DatatypeConverter.printHexBinary(hash);
	}
	
	/***
	 * Validates a plain text password by converting it to a hashed value and comparing with
	 * a persisted password string (see getHashedPassword for format)
	 * @param password plain text candidate password to be compared to saved one
	 * @param persistedPassword saved password entry from the user_profiles table in the database
	 * @return true if the password is a match otherwise false
	 */
	public static boolean validatePassword(String password, String persistedPassword) {
		String[] tokens = persistedPassword.split(":");
		int iterations = Integer.parseInt(tokens[0]);
		
		byte[] salt = DatatypeConverter.parseHexBinary(tokens[1]);
		byte[] hash = DatatypeConverter.parseHexBinary(tokens[2]);
		
		byte[] candidate = getHashedPasswordBytes(salt, password, iterations);

		return Arrays.equals(hash, candidate);
	}
	
	/***
	 * Modularized way to generate password hashes by letting the caller decide the number of algorithm
	 * iterations
	 * @param salt random array of bytes used as hashing salt
	 * @param password plain text password to be returned as a hash
	 * @param iterations number of algorithm iterations to be performed
	 * @return byte array of the hashed password
	 */
	public static byte[] getHashedPasswordBytes(byte[] salt, String password, int iterations) {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 128);
		SecretKeyFactory factory = null;
		byte[] hash = null;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}
}
