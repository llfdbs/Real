package com.yikang.real.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.Bean.util.Area;
import cn.Bean.util.More;
import cn.Bean.util.MoreValue;

import com.yikang.real.R;

@SuppressLint("ResourceAsColor")
public class MoreAdapterTwo extends BaseAdapter {

	Context context;
	List<MoreValue> data;
	Integer check = 0;

	
	public List<MoreValue> getData() {
		return data;
	}

	public void setData(List<MoreValue> data) {
		this.data = data;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check){
		this.check=check;
	}

	public MoreAdapterTwo(Context context, List<MoreValue> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int postion, View convert, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convert == null) {
			convert = LayoutInflater.from(context).inflate(
					R.layout.popu2list_item2, null);

		}
		TextView view = (TextView) convert
				.findViewById(R.id.popu2list_item_title2);
		view.setText(data.get(postion).getName());
		if (postion==check) {
			view.setTextColor(R.color.holo_blue_bright);
		} else {
			view.setTextColor(R.color.black);
		}
		return convert;
	}

}
