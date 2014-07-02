package cn.Bean.util;

import java.util.ArrayList;

public class ForrentHouseDetailsBean {

	// "floor": 2,
	// "fitment": "毛坯",
	// "type": "住宅",
	// "state": "整租",
	// "person": 1,
	// "area": 199.23,
	// "address": "红塔东路12号滇池卫城",
	// "toward": "南北",
	// "name": null,
	// "mob": null,
	// "iconurl": "URL",
	// "lat": "24.9731210785 ",
	// "lng": "102.69138721763 ",
	// "config": "无",
	// "desc": null,
	// "com": "悦湖郡",
	// "cid": "974 ",
	// "environmental": "七公里菜市场 同仁医院 城乡建设规划设计院 滇池卫城新天地商业中心",
	// "education": "云南大学滇池学院 滇池三中 德亨中学 福海中学 滇池卫城实验学校小学，中学，国际高中 扬帆贝贝 爱英森电脑培训学校",
	// "entertainment":
	// "海埂民族村 海埂公园 西山海埂大坝 大名星KTV、正龙KTV、青少年活动中心、西贡码头、南亚风情园 昌元酒楼、新龙门、小天鹅、快乐海鲜、八号码头 广福美食街",
	// "facility":
	// "同仁医院 滇池卫城 云大学滇池学院 河尾小村滇池卫城\r\n44 73 A9 A4 24 165 A9.C143 106 141 199",
	// "business": "卫城新天地 佳佳果蔬超市 佳佳果蔬超市 沃尔玛 百大家电 大商汇 十一家具 港都家具 新天地",
	// "image": [
	// "Photos/201310/1686/efe733b29e104ba6946a9796eabf7235.jpg",
	// "Photos/201310/1686/bf5ebe0abaf84316ad73fe874ef89cef.jpg",
	// "Photos/201310/1686/93326e6a31514456a1fd508a55dc8733.jpg"
	// ]

	 
	String floor, fitment, type, state, person, area, address, toward, name,
			mob, iconurl,   config, desc, com, cid, environmental,lat,lng,
			education, entertainment, facility, business,tel;
 
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	ArrayList<String>  image;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getToward() {
		return toward;
	}
	public void setToward(String toward) {
		this.toward = toward;
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
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
 
 
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
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
		return "ForrentHouseDetailsBean [floor=" + floor + ", fitment="
				+ fitment + ", type=" + type + ", state=" + state + ", person="
				+ person + ", area=" + area + ", address=" + address
				+ ", toward=" + toward + ", name=" + name + ", mob=" + mob
				+ ", iconurl=" + iconurl + ", config=" + config + ", desc="
				+ desc + ", com=" + com + ", cid=" + cid + ", environmental="
				+ environmental + ", lat=" + lat + ", lng=" + lng
				+ ", education=" + education + ", entertainment="
				+ entertainment + ", facility=" + facility + ", business="
				+ business + ", tel=" + tel + ", image=" + image + "]";
	}

	
	

}
