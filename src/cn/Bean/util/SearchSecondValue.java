package cn.Bean.util;

import java.io.Serializable;


/**
 * �������ַ�С������
 *
 */
public class SearchSecondValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xid;//С��id
	private String title;//С������
	private String lat; //ά��
	private String lng; //����
	
	
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
