package cn.Bean.util;

/**
 * �ⷿ����
 *
 */
public class RentHouseValue {
	
	private int zid;//¥��ID
	private String icon;//����ͼ��ַ
	private String title;//��Դ����
	private String roomtype;//��������
	private String simpleadd;//��ַ��д
	private String price;//���
	private String camera;//����ͷ
	public int getZid() {
		return zid;
	}
	public void setZid(int zid) {
		this.zid = zid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}
	public String getSimpleadd() {
		return simpleadd;
	}
	public void setSimpleadd(String simpleadd) {
		this.simpleadd = simpleadd;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	@Override
	public String toString() {
		return "RentHouseValue [zid=" + zid + ", icon=" + icon + ", title="
				+ title + ", roomtype=" + roomtype + ", simpleadd=" + simpleadd
				+ ", price=" + price + ", camera=" + camera + "]";
	}

}
