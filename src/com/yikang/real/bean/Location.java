package com.yikang.real.bean;

public class Location {

	private String mTitle;
	private String mContent;
	
	public Location(String pTitle, String pContent) {
		mTitle = pTitle;
		mContent = pContent;
	}
	
	public String getTitle() {
		return mTitle;
	}
	public String getContent() {
		return mContent;
	}
}
