package Ravi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class HashFunctions {

	public static String getHash(byte[] inputBytes, String algorithm) {
		// Available algorithms : MD2 , MD5 , SHA-1 , SHA-224 , SHA-256 , SHA-384 ,
		// SHA-512
		String hashValue = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(inputBytes);
			byte[] digestedBytes = messageDigest.digest();
			hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
		} catch (Exception e) {
			System.out.println(e);
		}
		return hashValue;
	}

	public static void main(String[] args) {
		String someString = "this is some string";
		System.out.println(getHash(someString.getBytes(), "SHA-512"));

		// try {
			// Read bytes from image and return hash
		// File image = new File("C:\\Users\\cdac\\Pictures\\Screenshots\\Screenshot(3).png");
		// byte[] imageBytes = Files.readAllBytes(image.toPath());
		// System.out.println(getHash(imageBytes, "MD5"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

}
