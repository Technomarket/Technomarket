package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

	private static final String salt = "pepper";
	
	private Encrypter(){
		
	}
	
	public static String encrypt(String password){
		String saltedPassword = password + salt; 
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Sometung went wrong - blank error message");
		}
		if(digest == null){
			return password;
		}
		byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
		return new String(hash);
	}
}
