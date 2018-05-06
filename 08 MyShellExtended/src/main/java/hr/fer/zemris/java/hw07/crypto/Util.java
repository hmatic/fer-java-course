package hr.fer.zemris.java.hw07.crypto;

/**
 * Utils class for Crypto which provides conversion from hex to byte and reverse.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Util {
	
	/**
	 * Static method that converts given hex to array of bytes.
	 * If given hex is odd-sized or contains illegal symbols, IllegalArgumentException will be thrown.
	 * 
	 * @param keyText hex
	 * @return result as array of bytes
	 * @throws IllegalArgumentException if hex is in invalid format
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length()%2 != 0) {
			throw new IllegalArgumentException();
		}
		
		byte[] byteArray = new byte[keyText.length()/2];
		
		for(int i=0; i<byteArray.length; i++) {
			byte first = singleHexToByte(keyText.charAt(i*2));
			byte second = singleHexToByte(keyText.charAt((i*2)+1));
			
			byte byteValue = (byte) ((first << 4) | second);
			
			byteArray[i] = byteValue;
		}
		
		return byteArray;
	}
	
	/**
	 * Converts single char from hex to byte.
	 * 
	 * @param hex char in hex
	 * @return converted value as byte
	 */
	public static byte singleHexToByte(char hex) {
		if (hex >= '0' && hex <= '9') {
			return (byte) (hex - '0');
		} else if (hex >= 'a' && hex <= 'f') {
			return (byte) (hex - 'a' + 10);
		} else if (hex >= 'A' && hex <= 'F') {
			return (byte) (hex - 'A' + 10);
		} else {
			throw new IllegalArgumentException();
		}
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
