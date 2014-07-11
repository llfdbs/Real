package cn.Bean.util;

import java.io.Serializable;


/**
 * 搜索二手房小区属性
 *
 */
public class SearchSecondValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xid;//小区id
	private String title;//小区名称
	private String lat; //维度
	private String lng; //精度
	
	
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
	public String getXid() {
		return xid;
	}
	public void setXid(String xid) {
		this.xid = xid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


}
