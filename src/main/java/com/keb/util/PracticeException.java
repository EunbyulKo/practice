package com.keb.util;

public class PracticeException extends Exception {
	private static final long serialVersionUID = -628406703514168516L;
	
	public static final int HTTP_CONNECTION_FAILED_CODE = 8000;
	public static final String HTTP_CONNECTION_FAILED_DESC = "HTTP connection failed";

	public static final int KAKAO_USER_DENIED_ACCESS_CODE = 1000;
	public static final String KAKAO_USER_DENIED_ACCESS_DESC = "Kakao user denied access";

	public static final int KAKAO_USER_EMPTY_TOKEN_CODE = 1001;
	public static final String KAKAO_USER_EMPTY_TOKEN_DESC = "Kakao user empty token";
	
	public static final int EMAIL_FAILED_SEND_CODE = 5000;
	public static final String EMAIL_FAILED_SEND_DESC = "failure sending mail";
	
	public static final int EMAIL_NOT_FOUND_CODE = 5001;
	public static final String EMAIL_NOT_FOUND_DESC = "Not found code";

	private int code;

	public PracticeException(int code, String desc) {
		super(desc);

		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
