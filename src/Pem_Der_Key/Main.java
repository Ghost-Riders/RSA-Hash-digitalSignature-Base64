package Pem_Der_Key;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * This file is intended to be used on a IDE for testing purposes.
 * ClassLoader.getSystemResource won't work in a JAR
 */
public class Main {
	static String getBase64EncodedCipherText(String cipherText) {
		byte[] cText = cipherText.getBytes();
		// return an ISO-8859-1 encoded String
		return Base64.getEncoder().encodeToString(cText);
	}

	/*
	 * public X509Certificate convertToX509Certificate(String pem) { X509Certificate
	 * cert = null; StringReader reader = new StringReader(pem); PEMReader pr = new
	 * PEMReader(reader); cert = (X509Certificate) pr.readObject(); return cert; }
	 */

	public static void main(String[] args)
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, URISyntaxException {

		String privateKeyContent = new String(Files.readAllBytes(
				Paths.get("D:\\14-05-2019\\BEST Meetiing\\Manmath\\eclipse_workspace_office\\QRwork\\pem\\1024private.ppk")));
		String publicKeyContent = new String(Files.readAllBytes(
				Paths.get("D:\\14-05-2019\\BEST Meetiing\\Manmath\\eclipse_workspace_office\\QRwork\\pem\\1024public")));

		privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "");
		publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "");
		;

		System.out.println(privateKeyContent + "\n" + publicKeyContent);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		privateKeyContent = getBase64EncodedCipherText(privateKeyContent);
		publicKeyContent = getBase64EncodedCipherText(publicKeyContent);

		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
		RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);

		X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

		// System.out.println(privKey);
		System.out.println(pubKey.getFormat());
	}
}