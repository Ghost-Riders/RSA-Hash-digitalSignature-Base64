package EncryptionStrategies;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAExample {
	private static final String PUBLIC_KEY_FILE = "rsaexamplepub.key";
	private static final String PRIVATE_KEY_FILE = "rsaexamplepri.key";

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
					System.out.println("---GENERATE PUBLIC and PRIVATE KEY-------");
					KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
					keyPairGenerator.initialize(1024);
					KeyPair keyPair = keyPairGenerator.generateKeyPair();
					PublicKey publicKey = keyPair.getPublic();
					PrivateKey privateKey = keyPair.getPrivate();
					System.out.println("-----PULLING OUT PARAMETERS WHICH MAKES KEYPAIR-----");
					KeyFactory keyFactory = KeyFactory.getInstance("RSA");
					RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
					RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
		
					System.out.println("-----SAVING PUBLIC KEY AND PRIVATE KEY TO FILES-----");
		
					RSAExample RSAObj = new RSAExample();
		
					RSAObj.saveKeys(PUBLIC_KEY_FILE, rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
					RSAObj.saveKeys(PRIVATE_KEY_FILE, rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());
		
					// encrypt data using public key
					byte[] encryptedData = RSAObj.encryptData("heello slkdflasjfkl");
		
					// decrypt data using private key
					RSAObj.decryptData(encryptedData);
	}

	private void saveKeys(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			System.out.println("Genrating " + fileName + "...");
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(new BufferedOutputStream(fos));
			oos.writeObject(mod);
			oos.writeObject(exp);
			System.out.println(fileName + " genrated successfully");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				oos.close();
				if (fos != null) {
					fos.close();
				}
			}
		}
	}

	private byte[] encryptData(String data) throws IOException {
		System.out.println("\n----- ENCRYPTION STARTED----");
		System.out.println("Data Before Encryption : " + data);
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = null;
		try {
			PublicKey publicKey = readPublicKeyFromFile(this.PUBLIC_KEY_FILE);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedData = cipher.doFinal(dataToEncrypt);
			System.out.println("Encrypted Data: " + new String(encryptedData));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException e) {

			e.printStackTrace();
		}
		System.out.println("-------------------ENCRYPTION COMPLETED ------------------");

		return encryptedData;
	}

	private void decryptData(byte[] data) {
		System.out.println("\n----------------------DECRYPTION STARTED-------------------");
		byte[] descryptedData = null;
		try {
			PrivateKey privateKey = readPrivateKeyFromFile(this.PRIVATE_KEY_FILE);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			descryptedData = cipher.doFinal(data);
			System.out.println("Decrypted Data: " + new String(descryptedData));
		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------DECRYPTION COMPLETED---------------");
	}

	public PublicKey readPublicKeyFromFile(String fileName) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		PublicKey publicKey = null;
		try {
			fis = new FileInputStream(new File(fileName));
			ois = new ObjectInputStream(fis);
			BigInteger modulus = (BigInteger) ois.readObject();
			BigInteger exponent = (BigInteger) ois.readObject();
			// Get Public Key
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			publicKey = fact.generatePublic(rsaPublicKeySpec);

		} catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return publicKey;
	}

	public PrivateKey readPrivateKeyFromFile(String fileName) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		PrivateKey privateKey = null;
		try {
			fis = new FileInputStream(new File(fileName));
			ois = new ObjectInputStream(fis);
			BigInteger modulus = (BigInteger) ois.readObject();
			BigInteger exponent = (BigInteger) ois.readObject();
			// Get Private Key
			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			privateKey = fact.generatePrivate(rsaPrivateKeySpec);

		} catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return privateKey;
	}
}
