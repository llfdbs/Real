package com.yikang.real.fragment;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.activity.History;
import com.yikang.real.adapter.ForrentHouseAdapter;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;

public class ForrentHistory extends Fragment{

	History act;
	View view ;
	ListView listView;
	List data;
	BaseAdapter adapter;
	int postion;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		act =(History) activity;
		super.onAttach(activity);
	}

	public static ForrentHistory instantiation(int position) {
		ForrentHistory fragment = new ForrentHistory();
		Bundle args = new Bundle();
		args.putInt("position", position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =LayoutInflater.from(act).inflate(R.layout.refreshview, null);
		Bundle bundle =getArguments();
		postion =bundle.getInt("position");
		initData(postion);
		initView();
		return view;
	}

	private void initView(){
		listView =(ListView) view.findViewById(R.id.pull_refresh_list);
		listView.setAdapter(adapter);
	}
	
	private void initData(int postion){
		
		SharedPreferences share ;
		Gson gson =new Gson();
		String source;
		Type type ;
		share=act.getSharedPreferences(Container.SHARENAME, 0);
		switch (postion) {
		case 1:
			source=share.getString(Share.OLD.getType(), null);
			type =new TypeToken<List<SecondHouseValue>>(){}.getType();
			if(source!=null){
			data =gson.fromJson(source,type);
			adapter =new NewHouseAdapter(act, data);
			}
			break;

		case 0:
			source=share.getString(Share.FORRENT.getType(), null);
			type =new TypeToken<List<SecondHouseValue>>(){}.getType();
			if(source!=null){
			data =gson.fromJson(source,type);
			adapter =new ForrentHouseAdapter(act, data);
			}
			break;
		}
	}
}
