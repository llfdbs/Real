package cn.Bean.util;

import java.io.Serializable;

/**
 * ���ַ�Դ����
 * @author admin
 *
 */

public class SecondHouseValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 100066L;
	private String temprownumber;
	private String nid;//��ԴID
	private String iconurl;//ͼƬ��ַ
	private String title;//����
	private String housetype;//����
	private String totalprice;//�ܼ�
	private String area;//���
	private String community;//����С������
	private String cid;//С��ID
	private String camera;//����ͷ
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
