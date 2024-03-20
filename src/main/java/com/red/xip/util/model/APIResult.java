package com.red.xip.util.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class APIResult {

	private int resultCode;
	private Object resultData;
	private String resultMsg;
	
	private APIResult(int resultCode, Object resultData, String resultMsg) {
		this.resultCode = resultCode;
		this.resultData = resultData;
		this.resultMsg = resultMsg;
	}
	
	// 성공 시 데이터 설정을 위한 정적 메서드
	public static APIResult success(Object resultData) {
		return new APIResult(1, resultData, "Success");
	}

    // 실패 시 코드와 메시지 설정을 위한 정적 메서드
	public static APIResult fail(String resultMsg) {
		return new APIResult(-1, null, resultMsg);
	}
	
	// 실패 시 코드와 메시지 설정을 위한 정적 메서드
	public static APIResult tokenFail() {
		return new APIResult(-2, null, "Invalid token");
	}
}
