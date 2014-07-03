package com.yikang.real.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.Bean.util.Area;
import com.yikang.real.R;

@SuppressLint("ResourceAsColor")
public class AreaAdapter extends BaseAdapter {

	Context context;
	List<Area> data;
	int check = -1;

	
	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public AreaAdapter(Context context, List<Area> data) {
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
		view.setText(data.get(postion).getArea());
		if (postion == check&&postion!=0) {
			convert.setBackgroundColor(R.color.background_holo_light);
		} else {
			convert.setBackgroundColor(android.R.color.white);
		}
		return convert;
	}

}
