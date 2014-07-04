package cn.Bean.util;

import java.util.ArrayList;

public class OldHouseDetailsBean {

	// {"floor":1
	// ,"fitment":"精装",
	// "type":"",
	// "years":"2006",
	// "name":null,
	// "mob":null,
	// "add":"滇池卫城紫郢",
	// "namepath":"URL",
	// "lat":"24.980297936455 ",
	// "lng":"102.68820497036 ",
	// "desc":"1110",
	// "environmental":"杨家地农贸市场 正和农贸市场.太家河农贸市场 滇池卫城社区医院、儿童医院、圣约翰医院，同仁医院 ",
	// "education":"云南大学滇池学院 滇池旅游度假区实验中学 滇池旅游度假区实验小学 扬帆贝贝 爱英森电脑培训学",
	// "entertainment":"云南民族村、 海埂公园 大名星KTV 青少年活动中心 南亚风情园 西贡码头 千禧KTV 西",
	// "facility":"滇池学院，滇池卫城，金家村同仁医院\r\n172,A9,A4,106直接到滇池卫城，24，44,73路到",
	// "business":"康乐茶叶市场 、民升超市 、大商汇、 国美电器、 沃尔玛、南亚风情园 广福厨具市场、十一家具",
	// "image":["Photos/201404/CS1404000003/d07231dc03b147b498986176282cd88e.jpg"]}]}}

	String floor,//楼层
	fitment, //装修
	type,	//物业用途
	years,	//年代
	name,	//经纪人
	mob,	//手机
	add,	//地址
	namepath,	//经纪人图像
	lat, //精度
	lng,//维度
	desc,//详细描述
	environmental,// 周边环境
	education,//学校教育
	entertainment,//休闲娱乐
	facility,//交通状况
	business;//商业百汇

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
