package com.keb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.keb.util.PracticeException;

@Service
public class EmailLoginService {
	@Autowired
	public JavaMailSender javaMailSender;
	
	@Autowired
	public RedisRunner redisRunner;

	@Async
	public void sendMail(String email) {
		String code = new TempKey().getKey(10, false);
		
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("iks1614@naver.com");
		simpleMessage.setTo(email);
		simpleMessage.setSubject("이메일 인증");
		simpleMessage.setText("인증번호: " + code);
		
		redisRunner.setEmailTempKey(email, code);
		javaMailSender.send(simpleMessage);
	}
	
	public boolean authorization(String email, String code) throws Exception {
		String restoredCode = redisRunner.getEmailTempKey(email);
		
		if(ObjectUtils.isEmpty(restoredCode)) {
			throw new PracticeException(PracticeException.EMAIL_NOT_FOUND_CODE, PracticeException.EMAIL_NOT_FOUND_DESC);
		}
		
		return code.equals(restoredCode);
	}
}
