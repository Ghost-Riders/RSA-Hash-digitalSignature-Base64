package Pem_Der_Key;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class Res {
public static String kk() {
	String PRIVATE_RSA_KEY_PKCS8 = 
		    "-----BEGIN PRIVATE KEY-----\r\n" + 
		    "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\r\n" + 
		    "dWgP1vOSU7Hkb/iGwZR0cEd2o6jd\r\n" + 
		    "-----END PRIVATE KEY-----";
		String key = PRIVATE_RSA_KEY_PKCS8
		    .replace("-----BEGIN PRIVATE KEY-----\n", "")
		    .replace("\n-----END PRIVATE KEY-----\n", "");
		PKCS8EncodedKeySpec keySpec =
		    new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(key));
		try {
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		    System.err.println("err");
		    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		    cipher.init(Cipher.DECRYPT_MODE, privateKey);
		    byte[] bytes = privateKey.getEncoded();
		    byte[] decryptedData = cipher.doFinal(bytes);
		    return new String(decryptedData);
		} catch (GeneralSecurityException e) {
		    return "";
		}
}
	static Base64.Encoder encoder = Base64.getEncoder();
	

	public static void main(String[] args) throws Exception {
		String filename = "D:\\14-05-2019\\BEST Meetiing\\Manmath\\eclipse_workspace_office\\QRwork\\sample keys\\private_key.pem";
		String key = "AAAAB3NzaC1yc2EAAAADAQABAAABAQDGWfyYJlPwD5vS2T3hc1GxPbO53LSZZ49zHXkYKOjlDDiAAvVsMahBKs2ri01R1yFsiUflvM3f6M+NRHjPlPu65kp59Twl+98G+766DCWo0CMCUfl0AC6znzvUR/tqfUcR0OIi/TdE/X6MBdknA4ALZFNmaG84axnyEF1qi0Rq+/jNnXTjQY2s/XwZGM9DuQ1dhAdoDLcxWnkWrYbllv17NTqOOVp5mvyoxAFcOCWo3bk8fF6mg4I9rFoMLtdIh6WFKWs50QjOZd13L7ecf4dk9P2Tx2q7rkyjxzgkrfPrPItsOMrasEVLeQ9Fw+J+V1ad8GA+XeA1n/I7KSGYaEZx";
	
		String ppkfilename = "D:\\14-05-2019\\BEST Meetiing\\Manmath\\eclipse_workspace_office\\QRwork\\sample keys\\public_key.der";
		d();
		String svctickblck = "abc";
		System.out.println("Main block: " + kk());
		String has = Hash(svctickblck);
		System.out.println("Hash: " + has);
		RSA rsa = new RSA();
		System.out.println(rsa.getPublicKey(ppkfilename).getAlgorithm());
		//String str = encryptText(has, loadPrivateKey(filename));
		//System.out.println(str);
	}
	public static void d() throws Exception {
		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		Signature signature = Signature.getInstance("SHA256WithDSA");

		signature.initSign(keyPair.getPrivate(), secureRandom);

		byte[] data = "abcdefghijklmnopqrstuvxyz".getBytes("UTF-8");
		signature.update(data);

		byte[] digitalSignature = signature.sign();


		Signature signature2 = Signature.getInstance("SHA256WithDSA");
		signature2.initVerify(keyPair.getPublic());

		signature2.update(data);

		boolean verified = signature2.verify(digitalSignature);

		System.out.println("verified = " + verified);
	}

	public static String encryptText(String msg, PrivateKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		String s = encoder.encodeToString((cipher.doFinal(msg.getBytes())));

		return s;
	}

	public static String Hash(String str) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] sha256 = md.digest(str.getBytes(StandardCharsets.UTF_8));
		String hash = DatatypeConverter.printHexBinary(sha256);

		return hash;
	}
	/*
	 * public PrivateKey getPemPrivateKey(String filename, String algorithm) throws
	 * Exception { File f = new File(filename); FileInputStream fis = new
	 * FileInputStream(f); DataInputStream dis = new DataInputStream(fis); byte[]
	 * keyBytes = new byte[(int) f.length()]; dis.readFully(keyBytes); dis.close();
	 * 
	 * String temp = new String(keyBytes); String privKeyPEM =
	 * temp.replace("-----BEGIN PRIVATE KEY-----\n", ""); privKeyPEM =
	 * privKeyPEM.replace("-----END PRIVATE KEY-----", "");
	 * //System.out.println("Private key\n"+privKeyPEM);
	 * 
	 * Base64 b64 = new Base64(); byte [] decoded = b64.decode(privKeyPEM);
	 * 
	 * PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded); KeyFactory kf =
	 * KeyFactory.getInstance(algorithm); return kf.generatePrivate(spec); }
	 */}
