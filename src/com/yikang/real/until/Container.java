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
	
	
	
	public static User getUSER() {
		return USER;
	}


	public static void setUSER(User uSER) {
		USER = uSER;
	}


	public static enum Page{
		OLD,NEW,FORREN,PERSON;
	}
	
	public static enum Share{
		OLD("old"),NEW("new"),FORRENT("forrent");
		
		private String value;
		private Share(String value){
			this.value= value;
		}
		
		public String getType(){
			return value;
		}
		
		public static Share getEnum(String tid){
		Share[] values = Share.values();
		for(int i=0;i<values.length;i++){
			if(values[i].getType() == tid){
				return values[i];
			}
		}
		return OLD;
		}
	}
	
	final public static String SHARENAME="share"; 
	
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
