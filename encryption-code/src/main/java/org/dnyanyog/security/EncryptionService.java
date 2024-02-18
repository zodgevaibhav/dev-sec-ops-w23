package org.dnyanyog.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class EncryptionService {

	private static final String SECRET_KEY = "5F270B070EF2F0BAB8123A810368B0E4"; // Replace with a secure secret key
	private static final String ALGORITHM = "AES";

	public String encrypt(String data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedData = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedData);
	}

	public static String decrypt(String encryptedData) throws Exception {
		SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
		return new String(decryptedData, StandardCharsets.UTF_8);
	}
	
	public static SecretKey generateAesKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256); // Use 256-bit key size
			return keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating AES key", e);
		}
	}
}

