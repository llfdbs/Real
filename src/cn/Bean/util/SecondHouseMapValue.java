package cn.Bean.util;


/**
 * 二手房地图属性
 *
 */
public class SecondHouseMapValue {

	private int mid;//小区id
	private float lat;//经度
	private float lng;//纬度
	private String title;//标题
	private int count;//数量
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
