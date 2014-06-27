package cn.Bean.util;

/**
 * 新房属性
 *
 */
public class NewHouseValue {

	private int fid;//楼盘ID
	private String icon;//缩略图地址
	private String title;//楼盘名称
	private String state;//在售状态
	private String simpleadd;//地址简写
	private String price;//均价
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
}
