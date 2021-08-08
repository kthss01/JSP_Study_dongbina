package common;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator {

	private String password = null;
	
	public Gmail() {
		
		Properties prop = new Properties();
		try {
			File path = new File("D:/git/JSP_Study_dongbina/Lecture_Evaluation/secret.secret");
			
			prop.load(new FileReader(path.getAbsolutePath()));
			
			password = prop.getProperty("pwd");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// 관리자 자신의 아이디 비밀번호 넣어주면 됨
		return new PasswordAuthentication("kthtim0704roba", password);
	}
	
}
