package EncryptionStrategies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64String {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String sampleString = "welcome";
		System.out.println("Original String  = " + sampleString);
		String encodeString = encoder(sampleString);
		System.out.println("\nBase64String = " + encodeString);

		System.out.println("\nDecoded Base64 to String = " + decoder(encodeString));

	}

	public static String encoder(String sampleString) throws UnsupportedEncodingException {

		String base64String = Base64.getEncoder().encodeToString(sampleString.getBytes("utf-8"));

		return base64String;
	}

	public static String decoder(String base64String) throws UnsupportedEncodingException {
		// Converting a Base6 String into String byte array
		byte[] stringByteArray = Base64.getDecoder().decode(base64String);
		return new String(stringByteArray, "utf-8");

	}
}