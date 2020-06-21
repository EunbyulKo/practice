package com.keb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import com.keb.service.PracticeService;

public class HttpConnectionUtil {
	public static final String KAKAO_OAUTH = "KAKAO_OAUTH";
	public static final String KAKAO_USER = "KAKAO_USER";
	
	public static String httpConnection(String type, String input) throws PracticeException {
        URL url = null;
        HttpURLConnection conn = null;
        String line = "";
        BufferedReader br = null;
        StringBuffer sb = null;
     
        try {
        	switch(type) {
        		case KAKAO_OAUTH :
                    url = new URL("https://kauth.kakao.com/oauth/token");
                    
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
                    conn.setRequestProperty( "charset", "utf-8");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(getPostData(input));
                    break;
        		case KAKAO_USER :
					url = new URL("https://kapi.kakao.com/v2/user/me");
					  
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
					conn.setRequestProperty( "charset", "utf-8");
					conn.setRequestProperty( "Authorization", "Bearer " + input);
					conn.setRequestMethod("POST");
					conn.connect();
        	    default:
        	         break;
        	}

            if(Optional.ofNullable(conn.getHeaderField("Content-Encoding")).orElse("").contains("gzip")) {
            	br = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
            } else {
            	br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }
            
            sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
     
        } catch (IOException e) {
        	throw new PracticeException(PracticeException.HTTP_CONNECTION_FAILED_CODE, PracticeException.HTTP_CONNECTION_FAILED_DESC);
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
            	throw new PracticeException(PracticeException.HTTP_CONNECTION_FAILED_CODE, PracticeException.HTTP_CONNECTION_FAILED_DESC);
            }
        }
        
        return sb.toString();
    }
	
	private static byte[] getPostData(String code) throws UnsupportedEncodingException {
		Map<String,Object> params = new LinkedHashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", PracticeService.getKeys().get("kakao_client_id"));
        params.put("redirect_uri", "https://localhost:8443/sns/kt_login/oauth");
        params.put("code", code);
 
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString().getBytes("UTF-8");
	}

}
