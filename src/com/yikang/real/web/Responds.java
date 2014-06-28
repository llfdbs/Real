package com.yikang.real.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Responds<T> {

	private int RESPONSE_CODE;  //返回code
	private String RESPONSE_CODE_INFO;//返回信息
	private HashMap<String,List<T>> RESPONSE_BODY; //请求返回值
	
	

	public HashMap<String, List<T>> getRESPONSE_BODY() {
		return RESPONSE_BODY;
	}
	public void setRESPONSE_BODY(HashMap<String, List<T>> rESPONSE_BODY) {
		RESPONSE_BODY = rESPONSE_BODY;
	}
	public int getRESPONSE_CODE() {
		return RESPONSE_CODE;
	}
	public void setRESPONSE_CODE(int rESPONSE_CODE) {
		RESPONSE_CODE = rESPONSE_CODE;
	}
	public String getRESPONSE_CODE_INFO() {
		return RESPONSE_CODE_INFO;
	}
	public void setRESPONSE_CODE_INFO(String rESPONSE_CODE_INFO) {
		RESPONSE_CODE_INFO = rESPONSE_CODE_INFO;
	}
	
	
}
