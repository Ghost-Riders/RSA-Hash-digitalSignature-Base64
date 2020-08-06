package EncryptionStrategies;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class RaviCode {
	private static final String PUBLIC_KEY_FILE = "D:\\14-05-2019\\BEST Meetiing\\Manmath\\eclipse_workspace_office\\QRwork\\pem\\public_key.txt";
	private static final String PRIVATE_KEY_FILE = "D:\\14-05-2019\\BEST Meetiing\\Manmath\\eclipse_workspace_office\\QRwork\\pem\\pkcs8.txt";

	public static void main(String[] args) throws Exception {
		String pkey = "-----BEGIN PRIVATE KEY-----\r\n" + 
				"MIICXAIBAAKBgQDIJwW10HOdJydatS1VZLsrDAg6AM5OcSiuYBckb4Ty4HUgDz0v\r\n" + 
				"w1EP5yKTc5btYKYqXUw12Lnz+0F7FctjXYOB5ADh79KiqmiYTbrecBzTP8Dg4QFn\r\n" + 
				"kcMI4bMKxqvgvXtO+VFb8+hafUn0aySUpt3fO3lYMirXufck6H7CTIFieQIDAQAB\r\n" + 
				"AoGAJ3xTSxAJjupHgESX4e3SYuanxNGd4am/Tc9VIXICt2uCj8WR25dbqZh+oLo/\r\n" + 
				"/yp+/YKUY1cTMEfUHjUpf7vWEnOOhMR9SFEM4TMqIJxgXLaYxtAWzn3pOlqdPBNB\r\n" + 
				"qmHoTeQuw1Hkzb1bovzO3CBd5ux6OcxxdWbDWWOuGmoZLAECQQDpd8aq9dVRHqMo\r\n" + 
				"EIb9BM4Z1amAJgz3u6Zk61qiwvgbfwP0ZhX99Az0CZrrZEc0dlqnqu8USVvu3DCr\r\n" + 
				"SnjPdTqBAkEA23ghkLyhWHkjGmR4YzbjIbsxLveeEUC9yli2cmdmsNyi2Cg1iH63\r\n" + 
				"G+LTQ1K/eSLN+FmaMFRokBC21lnYvZ/7+QJBALkPBB8HdeszCcj8MMPmRygbQJwf\r\n" + 
				"jS/hQmfEVrelK90/tPbI5K3EAXB/HJlxhy+CkSFOUOwXqhOP3dQo19I57gECQBzM\r\n" + 
				"rtrnZeTOQKT9+i5KzZNnlRSsublZ3Y3oBkkM+JTM3zGuVRgMSMsIAOEcwrOKtayZ\r\n" + 
				"VXpyGSaOiu+l6qM3k0ECQEDtRgr1wFzMgjFqIAkaYpe9k/zbbk9/dDnscUjbGHCN\r\n" + 
				"COODB/pFYqxfMB1Adp4mm16MAqsTu838Ra89bWtW0Uo=\r\n" + 
				"-----END PRIVATE KEY-----\r\n" + 
				"";
		String publickey ="-----BEGIN PUBLIC KEY-----\r\n" + 
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIJwW10HOdJydatS1VZLsrDAg6\r\n" + 
				"AM5OcSiuYBckb4Ty4HUgDz0vw1EP5yKTc5btYKYqXUw12Lnz+0F7FctjXYOB5ADh\r\n" + 
				"79KiqmiYTbrecBzTP8Dg4QFnkcMI4bMKxqvgvXtO+VFb8+hafUn0aySUpt3fO3lY\r\n" + 
				"MirXufck6H7CTIFieQIDAQAB\r\n" + 
				"-----END PUBLIC KEY-----\r\n" + 
				"";
		String svc = "{4}{4}{0|7|41|5f294eec400003a7|5f294eec|f|SBINRC202008043596588|9c4}";
		String tktblk = "{()(<2|1|10|[%1|8a2|885|5f23a883|1e0|9c4|1|0|b4|0253_0%]>)}";

		OldController oc = new OldController();

		String s = svc + tktblk;
		System.out.println("Concat str : " + s);
		s = getHash(s.getBytes(), "SHA-256");
		System.out.println("Hash Value : "+s);
		RaviCode rc = new RaviCode();
		
		byte[] edata = rc.encryptData(s, rc.loadPublicK(publickey));
		
		String encdata =new String(edata);
		System.out.println("Key encryption  :  "+encdata);
		rc.decryptData(encdata, rc.loadPrivateK(pkey));

//		String h = oc.Hash(s);
//		System.out.println("Has value : " + h);
//		PrivateKey key = oc.loadPrivateKey(pkey);
//		System.out.println(key.getAlgorithm() + "\n" + key.getFormat() + "\n" + key.getEncoded());
//		try {
//			System.out.println(oc.encryptText(h, key));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	public PrivateKey loadPrivateK(String fileName) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
		fileName = fileName.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
				.replaceAll("\\r\\n", "");
		byte[] pkder = Base64.getDecoder().decode(fileName.getBytes("utf-8"));
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = fact.generatePrivate(new PKCS8EncodedKeySpec(pkder));
		return privateKey;
	}

	public PublicKey loadPublicK(String fileName) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
		fileName = fileName.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
				.replaceAll("\\r\\n", "");
		byte[] pkder = Base64.getDecoder().decode(fileName.getBytes("utf-8"));
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PublicKey publicKey = fact.generatePublic(new X509EncodedKeySpec(pkder));
		return publicKey;
	}

	public byte[] encryptData(String data, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = null;
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		encryptedData = cipher.doFinal(dataToEncrypt);
	//	System.out.println("Encrypted Data: " + new String(encryptedData));
		return encryptedData;
	}

	private void decryptData(String data, PrivateKey privateKey) throws UnsupportedEncodingException {
		byte[]ss =Base64.getDecoder().decode(data.getBytes("utf-8"));
		byte[] descryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			descryptedData = cipher.doFinal(ss);
			System.out.println("Decrypted Data: " + new String(descryptedData));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
	}
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

}
