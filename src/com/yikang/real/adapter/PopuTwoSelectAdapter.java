package com.yikang.real.adapter;

import java.util.ArrayList;
import com.yikang.real.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PopuTwoSelectAdapter extends BaseAdapter{

	Context context;
	ArrayList<String> data;
	int selected=0;
	
	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public PopuTwoSelectAdapter(Context context,ArrayList<String> data){
		this.context =context;
		this.data=data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data!=null?data.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data!=null?data.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int postions, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.popu2list_item, null);
		}
		TextView text =(TextView) convertView.findViewById(R.id.popu2list_item_title);
//		ImageView icon =(ImageView) convertView.findViewById(R.id.line);
		
		text.setText(data.get(postions));
		if(postions==selected){
			text.setTextColor(R.color.holo_blue_bright);
//			icon .setVisibility(View.VISIBLE);
		}else{
			text.setTextColor(R.color.black);
//			icon .setVisibility(View.GONE);
		}
		
		return convertView;
	}

	
}
