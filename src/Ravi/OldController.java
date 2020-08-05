package Ravi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class OldController {
	Base64.Encoder encoder = Base64.getEncoder();

	public String encodedQrSvc(String qrsvc) {
		return Base64.getEncoder().encodeToString(qrsvc.getBytes());
	}

	public String Hash(String str) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] sha256 = md.digest(str.getBytes(StandardCharsets.UTF_8));
		String hash = DatatypeConverter.printHexBinary(sha256);

		return hash;
	}

	// use this if .pem file
	public PrivateKey loadPrivateKey(String s) throws Exception {

		// String privateKeyPEM = FileUtils.readFileToString(new
		// File("privatekey-pkcs8.pem"), StandardCharsets.UTF_8);

		// strip of header, footer, newlines, whitespaces
		s = s.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\r\\n",
				"");

		// decode to get the binary DER representation
		byte[] privateKeyDER = Base64.getDecoder().decode(s);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDER));
		return privateKey;
	}

	// use when key is in string
	public PrivateKey getPrivateKey(String base64PrivateKey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return privateKey;
	}

	public String encryptText(String msg, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		String s = Base64.getEncoder().encodeToString((cipher.doFinal(msg.getBytes())));
		System.out.println("inside en : " + s);
		return s;
	}

}
