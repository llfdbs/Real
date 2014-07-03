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

import com.yikang.real.R;

@SuppressLint("ResourceAsColor")
public class MoreAdapter extends BaseAdapter {

	Context context;
	List<More> data;
	List<Integer> check = new ArrayList<Integer>();
	Integer postion =0;
	
	public List<Integer> getCheck() {
		return check;
	}


	public Integer getPostion() {
		return postion;
	}


	public void setPostion(Integer postion) {
		this.postion = postion;
	}


	public MoreAdapter(Context context, List<More> data) {
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
					R.layout.popu2list_item, null);

		}
		TextView view = (TextView) convert
				.findViewById(R.id.popu2list_item_title);
		view.setText(data.get(postion).getMoreName());
		if (postion==this.postion) {
//		if (postion == check) {
			convert.setBackgroundColor(R.color.background_holo_light);
		} else {
			convert.setBackgroundColor(android.R.color.white);
		}
		return convert;
	}

}
