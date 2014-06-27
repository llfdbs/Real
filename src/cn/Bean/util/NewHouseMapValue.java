package cn.Bean.util;


/**
 * 新房地图属性
 *
 */
public class NewHouseMapValue {

	private int mid;//楼盘id
	private float lat;//经度
	private float lng;//纬度
	private String title;//标题
	private String price;//价格
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
