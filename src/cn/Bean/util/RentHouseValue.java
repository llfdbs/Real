package cn.Bean.util;

/**
 * 租房属性
 *
 */
public class RentHouseValue {
	
	private int zid;//楼盘ID
	private String icon;//缩略图地址
	private String title;//房源标题
	private String roomtype;//房间类型
	private String simpleadd;//地址简写
	private String price;//租金
	private String camera;//摄像头
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
