package cn.Bean.util;
/**
 * 街道列表属性
 */
public class City {
	private String city;//区域名称
//	private String listarea;//区域名称列表
	private float lat;
	private float lng;
	
	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
//	public void setArea(String area) {
//		this.area = area;
//	}
//	public String getArea() {
//		return area;
//	}
//	public void setListarea(String listarea) {
//		this.listarea = listarea;
//	}
//	public String getListarea() {
//		return listarea;
//	}

}
