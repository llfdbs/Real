package cn.Bean.util;

import java.util.ArrayList;

public class OldHouseDetailsBean {

 
//		{"floor":1
//			,"fitment":"��װ",
//			"type":"",
//			"years":"2006",
//			"name":null,
//			"mob":null,
//			"add":"���������۫",
//			"namepath":"URL",
//			"lat":"24.980297936455 ",
//			"lng":"102.68820497036 ",
//			"desc":"1110",
//			"environmental":"��ҵ�ũó�г� ����ũó�г�.̫�Һ�ũó�г� �����������ҽԺ����ͯҽԺ��ʥԼ��ҽԺ��ͬ��ҽԺ ",
//			"education":"���ϴ�ѧ���ѧԺ ������ζȼ���ʵ����ѧ ������ζȼ���ʵ��Сѧ �﷫���� ��Ӣɭ������ѵѧ",
//			"entertainment":"��������塢 ������԰ ������KTV ���������� ���Ƿ���԰ ������ͷ ǧ��KTV ��",
//			"facility":"���ѧԺ��������ǣ���Ҵ�ͬ��ҽԺ\r\n172,A9,A4,106ֱ�ӵ�������ǣ�24��44,73·��",
//			"business":"���ֲ�Ҷ�г� ���������� �����̻㡢 ���������� �ֶ��ꡢ���Ƿ���԰ �㸣�����г���ʮһ�Ҿ�",
//			"image":["Photos/201404/CS1404000003/d07231dc03b147b498986176282cd88e.jpg"]}]}} 
	
	
	String  floor ,fitment ,type ,years ,name, mob, add, namepath ,lat, lng ,desc, environmental, education ,entertainment, facility ,business;
	  
	
	ArrayList<String> image;


	public String getFloor() {
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}


	public String getFitment() {
		return fitment;
	}


	public void setFitment(String fitment) {
		this.fitment = fitment;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getYears() {
		return years;
	}


	public void setYears(String years) {
		this.years = years;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMob() {
		return mob;
	}


	public void setMob(String mob) {
		this.mob = mob;
	}


	public String getAdd() {
		return add;
	}


	public void setAdd(String add) {
		this.add = add;
	}


	public String getNamepath() {
		return namepath;
	}


	public void setNamepath(String namepath) {
		this.namepath = namepath;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getEnvironmental() {
		return environmental;
	}


	public void setEnvironmental(String environmental) {
		this.environmental = environmental;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getEntertainment() {
		return entertainment;
	}


	public void setEntertainment(String entertainment) {
		this.entertainment = entertainment;
	}


	public String getFacility() {
		return facility;
	}


	public void setFacility(String facility) {
		this.facility = facility;
	}


	public String getBusiness() {
		return business;
	}


	public void setBusiness(String business) {
		this.business = business;
	}


	public ArrayList<String> getImage() {
		return image;
	}


	public void setImage(ArrayList<String> image) {
		this.image = image;
	}


	@Override
	public String toString() {
		return "OldHouseDetailsBean [floor=" + floor + ", fitment=" + fitment
				+ ", type=" + type + ", years=" + years + ", name=" + name
				+ ", mob=" + mob + ", add=" + add + ", namepath=" + namepath
				+ ", lat=" + lat + ", lng=" + lng + ", desc=" + desc
				+ ", environmental=" + environmental + ", education="
				+ education + ", entertainment=" + entertainment
				+ ", facility=" + facility + ", business=" + business
				+ ", image=" + image + "]";
	}
	
	
}
