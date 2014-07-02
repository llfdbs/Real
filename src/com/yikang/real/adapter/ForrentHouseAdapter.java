package com.yikang.real.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.SecondHouseValue;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yikang.real.R;

public class ForrentHouseAdapter extends BaseAdapter {

	Context context;
	ArrayList<SecondHouseValue> data;

	public ForrentHouseAdapter(Context context, List<SecondHouseValue> data) {
		this.context = context;
		this.data = (ArrayList<SecondHouseValue>) data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data != null ? data.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data != null ? data.get(arg0) : null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int postions, View converView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HouseViewHolder holder;
		if (null == converView) {
			converView = LayoutInflater.from(context).inflate(
					R.layout.house_item, null);
			holder = new HouseViewHolder();
			ImageView icon = (ImageView) converView
					.findViewById(R.id.house_icon);
			TextView name = (TextView) converView.findViewById(R.id.house_name);
			TextView address = (TextView) converView
					.findViewById(R.id.house_address);
			TextView size = (TextView) converView.findViewById(R.id.house_size);
			TextView pices = (TextView) converView
					.findViewById(R.id.house_pices);
			holder.setHouseName(name);
			holder.setIcon(icon);
			holder.setAddress(address);
			holder.setHousePices(pices);
			holder.setHouseSize(size);
			converView.setTag(holder);
		} else {
			holder = (HouseViewHolder) converView.getTag();
		}

		holder.getHouseName().setText(data.get(postions).getSimpleadd());
		holder.getAddress().setText(data.get(postions).getCommunity());
		holder.getHousePices().setText(data.get(postions).getHousetype());
		holder.getHouseSize().setText(data.get(postions).getPrice()+"元");

		ImageLoader loader = ImageLoader.getInstance();

		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.ic_launcher) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showStubImage(R.drawable.ic_launcher).cacheInMemory()// 设置下载的图片是否缓存在内存中
				.cacheOnDisc().build();// 设置下载的图片是否缓存在SD卡中

		if(data.get(postions).getIconurl()!=null){
			loader.displayImage(data.get(postions).getIconurl(), holder.getIcon(),options);
		}

		return converView;
	}

}

 
class ForrentHouseViewHolder {
	private ImageView icon;
	private TextView houseName;
	private TextView address;
	private TextView houseSize;
	private TextView housePices;

	public ImageView getIcon() {
		return icon;
	}

	public void setIcon(ImageView icon) {
		this.icon = icon;
	}

	public TextView getHouseName() {
		return houseName;
	}

	public void setHouseName(TextView houseName) {
		this.houseName = houseName;
	}

	public TextView getAddress() {
		return address;
	}

	public void setAddress(TextView address) {
		this.address = address;
	}

	public TextView getHouseSize() {
		return houseSize;
	}

	public void setHouseSize(TextView houseSize) {
		this.houseSize = houseSize;
	}

	public TextView getHousePices() {
		return housePices;
	}

	public void setHousePices(TextView housePices) {
		this.housePices = housePices;
	}

}