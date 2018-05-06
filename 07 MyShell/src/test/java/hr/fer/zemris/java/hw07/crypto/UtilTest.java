package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {
	@Test
	public void hextobyte() {
		assertArrayEquals(new byte[] { 0x00, 0x01, 0x02 }, Util.hextobyte("000102"));
		assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xFD }, Util.hextobyte("FFFEFD"));
		assertArrayEquals(new byte[] { (byte) 0xFF }, Util.hextobyte("FF"));
		assertArrayEquals(new byte[] { (byte) 0x00 }, Util.hextobyte("00"));
		assertArrayEquals(new byte[] { (byte) 0x01 }, Util.hextobyte("01"));
		assertArrayEquals(new byte[] { (byte) 0x7F }, Util.hextobyte("7F"));
		assertArrayEquals(new byte[] { (byte) 0x80 }, Util.hextobyte("80"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void hextobyteThrowsIfOddNumberOfCharacters() {
		Util.hextobyte("12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void hextobyteThrowsIfInvalidCharacters() {
		Util.hextobyte("ABCDEFGH");
	}

	@Test
	public void singleHexToByte() {
		assertEquals(0x0, Util.singleHexToByte('0'));
		assertEquals(0x1, Util.singleHexToByte('1'));
		assertEquals(0x9, Util.singleHexToByte('9'));
		assertEquals(0xa, Util.singleHexToByte('A'));
		assertEquals(0xf, Util.singleHexToByte('F'));
		assertEquals(0xa, Util.singleHexToByte('a'));
		assertEquals(0xf, Util.singleHexToByte('f'));
	}

	@Test(expected = IllegalArgumentException.class)
	public void singleHexToByteThrowsIfInvalid1() {
		Util.singleHexToByte('z');
	}
	
	@Test
	public void bytetohex() {
		assertEquals("000102", Util.bytetohex(new byte[] { 0x00, 0x01, 0x02 }));
		assertEquals("fffefd", Util.bytetohex(new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0xFD }));
		assertEquals("ff", Util.bytetohex(new byte[] { (byte) 0xFF }));
		assertEquals("00", Util.bytetohex(new byte[] { (byte) 0x00 }));
		assertEquals("01", Util.bytetohex(new byte[] { (byte) 0x01 }));
		assertEquals("7f", Util.bytetohex(new byte[] { (byte) 0x7F }));
		assertEquals("80", Util.bytetohex(new byte[] { (byte) 0x80 }));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void singleByteToHexWhenInvalid() {
		Util.singleByteToHex(45,16);
	}
}
