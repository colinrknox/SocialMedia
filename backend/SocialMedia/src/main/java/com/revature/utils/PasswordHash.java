package com.revature.utils;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

/***
 * Password Hash object with the ability to generate a String for storage in a database as well
 * as validating passwords.  Password validation is done by creating a new object with a stored
 * salt and iterations 
 * @author Colin Knox
 *
 */
public class PasswordHash {
	
	int iterations;
	byte[] salt;
	byte[] passBytes;
	String password;
	String dbPassword;
	
	private PasswordHash(int iterations, byte[] salt, String password) {
		this.iterations = iterations;
		this.salt = salt;
		this.password = password;
		passBytes = getHashedPasswordBytes();
		dbPassword = getPersistentPassword();
	}
	
	public String getDbPassword() {
		return dbPassword;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public boolean validate(String storedHash) {
		return validate(DatatypeConverter.parseHexBinary(storedHash));
	}
	
	public boolean validate(byte[] storedHashBytes) {
		return Arrays.equals(storedHashBytes, passBytes);
	}
	
	public static class Builder {
		private int iterations = 65536;
		private byte[] salt = getSalt();
		private String password;
		
		public Builder setIterations(int iterations) {
			this.iterations = iterations;
			return this;
		}
		
		public Builder setIterations(String iterations) {
			this.iterations = Integer.parseInt(iterations);
			return this;
		}
		
		public Builder setSalt(byte[] salt) {
			this.salt = salt;
			return this;
		}
		
		public Builder setSalt(String salt) {
			this.salt = DatatypeConverter.parseHexBinary(salt);
			return this;
		}
		
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public PasswordHash build() {
			return new PasswordHash(iterations, salt, password);
		}
	}
	
	
	/***
	 * Returns a 16 byte salt for password hashing
	 * Every call is a unique result
	 * @return
	 */
	private static byte[] getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
	
	/***
	 * Takes an iteration, salt, and password and concatenates them
	 * @return the string to be persisted as the user's password e.g {iterations:salt:hashedPassword}
	 */
	private String getPersistentPassword() {
		return iterations + ":" + DatatypeConverter.printHexBinary(salt) + ":" + DatatypeConverter.printHexBinary(passBytes);
	}
	
	
	/***
	 * Generate password byte array hashes with class fields salt, iterations, and password
	 * @return byte array of the hashed password
	 */
	private byte[] getHashedPasswordBytes() {
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
