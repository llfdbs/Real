package com.yikang.real.adapter;

import java.util.ArrayList;
import java.util.List;

import com.yikang.real.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonCentrolAdp extends BaseAdapter{

	Context context;
	String[] data;
	public PersonCentrolAdp(Context context,String[] data){
		this.context= context;
		this.data= data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data!=null?data.length:0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data!=null?data[arg0]:null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int postion, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(null==view){
			view =LayoutInflater.from(context).inflate(R.layout.person_item, null);
		}
		ImageView icon =(ImageView) view.findViewById(R.id.person_icon);
		TextView name= (TextView) view.findViewById(R.id.person_name);
		Log.v(getClass().getName(), data[postion]);
		name.setText(data[postion]);
		switch (postion) {
		case 0:
			icon.setImageResource(R.drawable.shoucang);
			break;
		case 1:
			icon.setImageResource(R.drawable.history);
			break;
		case 2:
			icon.setImageResource(R.drawable.jisuanqi);
			break;
		case 3:
			icon.setImageResource(R.drawable.about);
			break;
		
		}
		return view;
	}

}
