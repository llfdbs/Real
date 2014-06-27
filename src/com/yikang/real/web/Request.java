package com.yikang.real.web;

import java.util.HashMap;

public class Request {

	private String commandcode;
	private Object REQUEST_BODY;
	
	public Request(){
		REQUEST_BODY =new HashMap<String, String>();
	}
	public String getCommandcode() {
		return commandcode;
	}
	public void setCommandcode(String commandcode) {
		this.commandcode = commandcode;
	}
	public Object getREQUEST_BODY() {
		return REQUEST_BODY;
	}
	public void setREQUEST_BODY(Object rEQUEST_BODY) {
		REQUEST_BODY = rEQUEST_BODY;
	}
	
}
