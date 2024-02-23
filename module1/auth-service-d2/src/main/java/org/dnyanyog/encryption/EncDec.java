package org.dnyanyog.encryption;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncDec {

	public static SecretKey generateAESKey() throws NoSuchAlgorithmException { //// KEY Generate

		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); // Create instance of KeyGenerator using static
																		// method
		keyGenerator.init(256); // Use 256-bit key size // Brute Force Attack

		return keyGenerator.generateKey();
	}

	public static String encrypt(String plaintext, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key); // Initialization

		byte[] plainTextByte = plaintext.getBytes(StandardCharsets.UTF_8);
		byte[] encryptedBytes = cipher.doFinal(plainTextByte);

		String encryptedDataInString = Base64.getEncoder().encodeToString(encryptedBytes);
		return encryptedDataInString;
	}

	public static String decrypt(String encryptedData, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] encryptedByteArrayData = Base64.getDecoder().decode(encryptedData);

		byte[] decryptedBytes = cipher.doFinal(encryptedByteArrayData);

		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public static void main(String[] args) throws Exception {

		// Step 1 : Generate key and store in some secure place
		SecretKey key = generateAESKey();

		String plaintext = "Test@123";

		// Step 2: Encrypt daya using generated key
		String encryptedText = encrypt(plaintext, key);
		System.out.println("Encrypted: " + encryptedText);

		// Step 3: Decrypt daya using generated key
		String decryptedText = decrypt(encryptedText, key);
		System.out.println("Decrypted: " + decryptedText);

	}
}
