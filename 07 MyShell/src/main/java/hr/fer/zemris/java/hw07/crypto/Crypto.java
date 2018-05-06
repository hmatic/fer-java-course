package hr.fer.zemris.java.hw07.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Class allows the user to encrypt/decrypt given file using the AES cryptoalgorithm
 * and the 128-bit encryption key or calculate and check the SHA-256 file digest. 
 * Program accepts 2 or 3 arguments. First argument is name of command: checksha, encrypt or decrypt.
 * For checksha, program takes one additional argument which is path to the file.
 * For encrypt/decrypt program takes two additional arguments, first is source file and second is destination file.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Crypto {
	/**
	 * Size of buffer for file reading.
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Program entry point.
	 * Arguments explained in javadoc of class.
	 * @param args string array of arguments
	 */
	public static void main(String[] args) {
		if(!(args.length == 2 || args.length == 3)) {
			System.out.println("Invalid number of arguments.");
			System.exit(1);
		}
		
		switch(args[0]) {
			case "checksha":
				if(args.length != 2) {
					System.out.println("Invalid number of arguments for this command.");
					System.exit(1);
				}
				String sha = getSha(args[1]);
				if(sha!=null) {
					checkSha(sha, args[1]);
				} else {
					System.out.println("SHA could not be calculated.");
				}
				break;
				
			case "encrypt":
				if(args.length != 3) {
					System.out.println("Invalid number of arguments for this command.");
					System.exit(1);
				}
				decryptOrEncrypt(args[1], args[2], true);
				break;
				
			case "decrypt":
				if(args.length != 3) {
					System.out.println("Invalid number of arguments for this command.");
					System.exit(1);
				}
				decryptOrEncrypt(args[1], args[2], false);
				break;
				
			default:
				System.out.println("Unsupported operation. Supported operations are checksha, encrypt and decrypt.");
				System.exit(1);
		}	
	}

	/**
	 * Method which encrypts or decrypts given source file to given destination. 
	 * Source file is given in first argument, destination is given in second argument.
	 * If third argument is true, file will be encrypted. If it is false, file will be decrypted.
	 * @param inputFile source file
	 * @param outputFile destination file
	 * @param encrypt encryption if true, decryption otherwise
	 */
	private static void decryptOrEncrypt(String inputFile, String outputFile, boolean encrypt) {
		String keyText;
		String ivText;
		
		try(Scanner sc = new Scanner(System.in)) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print("> ");
			keyText = sc.nextLine().trim();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print("> ");
			ivText = sc.nextLine().trim();
		}
		
		Cipher cipher = getCipher(keyText, ivText, encrypt);
		writeToFile(cipher, inputFile, outputFile);
		System.out.println("Decryption completed. Generated file " + inputFile + " based on file " + outputFile + ".");
	}
	
	
	/**
	 * Method that 
	 * @param cipher
	 * @param inputFile
	 * @param outputFile
	 */
	private static void writeToFile(Cipher cipher, String inputFile, String outputFile) {
		try (FileInputStream input = new FileInputStream(inputFile);
				FileOutputStream output = new FileOutputStream(outputFile)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int n = 0;
			while ((n = input.read(buffer)) != -1) {
				output.write(cipher.update(buffer, 0, n));
			}
			output.write(cipher.doFinal());
		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Factory method that constructs Cipher object.
	 * 
	 * @param keyText secret key
	 * @param ivText initialization vector
	 * @param encrypt encrypt mode if true, decrypt mode otherwise 
	 * @return cipher object
	 * @see javax.crypto.Cipher
	 */
	private static Cipher getCipher(String keyText, String ivText, boolean encrypt) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException |
				NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cipher;
	}
	
	/**
	 * Generates SHA key as String for given input file.
	 * 
	 * @param inputFile
	 * @return
	 */
	private static String getSha(String inputFile) {
		String sha = null;
		
		try (FileInputStream input = new FileInputStream(inputFile);) {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[BUFFER_SIZE];
			int n = 0;
			while ((n = input.read(buffer)) != -1) {
				digest.update(buffer, 0, n);
			}
			
			byte[] mdbytes = digest.digest();
			
			sha = Util.bytetohex(mdbytes);
		} catch (IOException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return sha;
	}
	
	/**
	 * Method takes expected SHA key from user and compares it to SHA given in argument.
	 * Method prints appropriate message as result.
	 * @param sha SHA generated from file
	 * @param file file name
	 */
	private static void checkSha(String sha, String file) {
		String expected;
		try(Scanner sc = new Scanner(System.in)) {
			System.out.println("Please provide expected sha-256 digest for " + file + ":");
			System.out.print("> ");
			expected = sc.nextLine().trim();
		}
		if(expected.equals(sha)) {
			System.out.println("Digesting completed. Digest of "+ file + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + file +
					" does not match the expected digest. "
					+ "Digest was: " + sha);
		}
	}
}
