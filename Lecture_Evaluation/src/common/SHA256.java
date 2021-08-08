package common;

import java.security.MessageDigest;

public class SHA256 {

	// 이메일 값에 해쉬 적용한값에 salt로 해킹 방어
	public static String getSHA256(String input) {
		StringBuffer result = new StringBuffer();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// sha256 적용하면 해킹 위험이 있어서 salt 값을 적용한다고 함
			// 자신이 원하는 salt를 넣어주는게 일반적
			byte[] salt = "Hello! This is Salt.".getBytes();
			digest.reset();
			digest.update(salt);
			
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for (int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if (hex.length() == 1) result.append("0");
				result.append(hex);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
}
