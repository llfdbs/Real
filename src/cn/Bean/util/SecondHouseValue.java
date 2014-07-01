package cn.Bean.util;

import java.io.Serializable;

/**
 * 二手房源属性
 * @author admin
 *
 */

public class SecondHouseValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 100066L;
	private String temprownumber;
	private String nid;//房源ID
	private String iconurl;//图片地址
	private String title;//标题
	private String housetype;//户型
	private String totalprice;//总价
	private String area;//面积
	private String community;//所属小区名称
	private String cid;//小区ID
	private String camera;//摄像头
	private String simpleadd;

	
	public String getTemprownumber() {
		return temprownumber;
	}
	public void setTemprownumber(String temprownumber) {
		this.temprownumber = temprownumber;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getSimpleadd() {
		return simpleadd;
	}
	public void setSimpleadd(String simpleadd) {
		this.simpleadd = simpleadd;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHousetype() {
		return housetype;
	}
	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}

}
