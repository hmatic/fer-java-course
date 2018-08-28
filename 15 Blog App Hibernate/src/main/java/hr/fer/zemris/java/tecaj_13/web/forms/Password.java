package hr.fer.zemris.java.tecaj_13.web.forms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class with static methods used for password 
 * validation and password hash generation.
 * Cryptography used: SHA-1
 * Password encoding: UTF-8
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Password {

	/**
	 * Check if hash of password given in first argument 
	 * matches password hash given in second argument.
	 * @param password password to be checked
	 * @param passwordHash password hash
	 * @return true if password matches, false otherwise
	 */
	public static boolean checkPassword(String password, String passwordHash) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] mdbytes = md.digest();
			String passwordHashed = bytetohex(mdbytes);
			
			return passwordHashed!=null ? passwordHashed.equals(passwordHash) : false;
		} catch (NoSuchAlgorithmException ignorable) {}
		
		return false;
	}
	
	/**
	 * Generates password hash using SHA-1 from password given in argument.
	 * @param password password
	 * @return password hash
	 */
	public static String generatePasswordHash(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes(StandardCharsets.UTF_8));
			byte[] mdbytes = md.digest();
			
			return bytetohex(mdbytes);
		} catch (NoSuchAlgorithmException ignorable) {}
		
		return null;
	}
	
	
	
	/**
	 * Converts array of bytes to hex as String.
	 * 
	 * @param byteArray array of bytes
	 * @return String representation of hex
	 */
	public static String bytetohex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < byteArray.length; i++){
		    sb.append(singleByteToHex((byteArray[i] >> 4) & 0xF, 16));
		    sb.append(singleByteToHex((byteArray[i] & 0xF), 16));
		}
		return sb.toString();
	}
	
	/**
	 * Converts single byte to hex.
	 * Throws appropriate exception if byte is out of hex range.
	 *  
	 * @param digit byte
	 * @param radix max value of hex
	 * @return converted value as hex char
	 * @throws IllegalArgumentException if digit is out of range of hex
	 */
	public static char singleByteToHex(int digit, int radix) {
        if ((digit > radix) || (digit < 0)) {
        	throw new IllegalArgumentException();
        }
        if (digit < 10) {
            return (char)('0' + digit);
        }
        return (char)('a' - 10 + digit);
    }
}
