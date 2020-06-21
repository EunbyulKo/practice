package com.keb.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.keb.util.HttpConnectionUtil;
import com.keb.util.PracticeException;

@Service
public class KakaoLoginService {
	public String getUserProfile(String code, String error) throws PracticeException {
		if(error != null) {
    		throw new PracticeException(PracticeException.KAKAO_USER_DENIED_ACCESS_CODE, PracticeException.KAKAO_USER_DENIED_ACCESS_DESC);
    	}
		
		JSONParser parser = new JSONParser();
    	Object obj = null;
		try {
			obj = parser.parse(HttpConnectionUtil.httpConnection(HttpConnectionUtil.KAKAO_OAUTH, code));
		} catch (ParseException e) {
			throw new PracticeException(PracticeException.HTTP_CONNECTION_FAILED_CODE, PracticeException.HTTP_CONNECTION_FAILED_DESC);
		}
    	JSONObject jsonObj = (JSONObject) obj;

    	String token = (String) jsonObj.get("access_token");
    	
    	if(token == null) {
    		throw new PracticeException(PracticeException.KAKAO_USER_EMPTY_TOKEN_CODE, PracticeException.KAKAO_USER_EMPTY_TOKEN_DESC);
    	}
    	
    	return HttpConnectionUtil.httpConnection(HttpConnectionUtil.KAKAO_USER, token);
	}
}
