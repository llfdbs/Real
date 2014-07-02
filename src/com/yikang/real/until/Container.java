package com.yikang.real.until;

import java.util.ArrayList;

import cn.Bean.util.City;

import com.yikang.real.bean.User;

public class Container {

	public static User USER;
	
	public static City city =null;
	
	final public static String RESULT="list";
	
	final public static int REFRESH=0;
	
	final public static int GETMORE=1;
	
	private  static Page currentPage;
	
	
	
	public static enum Page{
		OLD,NEW,FORREN,PERSON;
	}
	
	public static City getCity() {
		return city;
	}


	public static void setCity(City city) {
		Container.city = city;
	}


	public static enum PopStatus{
		Location,Picese,More;
	}

	public static Page getCurrentPage() {
		return currentPage;
	}


	public static void setCurrentPage(Page currentPage) {
		Container.currentPage = currentPage;
	}
	
	
}
