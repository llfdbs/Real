package com.yikang.real.until;

import java.util.ArrayList;

import cn.Bean.util.City;

import com.yikang.real.bean.User;

public class Container {

	public static User USER;
	
	public static City city =null;
	
	final public static String RESULT="200";
	public static City getCity() {
		return city;
	}


	public static void setCity(City city) {
		Container.city = city;
	}


	public static enum PopStatus{
		Location,Picese,More;
	}
	
}
