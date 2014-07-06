package com.yikang.real.fragment;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.activity.ForrentDetailsActivity;
import com.yikang.real.activity.History;
import com.yikang.real.adapter.ForrentHouseAdapter;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.imp.PublicDb;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;

public class ForrentHistory extends Fragment{

	History act;
	View view ;
	ListView listView;
	List<SecondHouseValue> data;
	BaseAdapter adapter;
	int postion;
	private String fromwhere;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		act =(History) activity;
		super.onAttach(activity);
	}

	public static ForrentHistory instantiation(int position,String fromwhere) {
		ForrentHistory fragment = new ForrentHistory();
		Bundle args = new Bundle();
		args.putInt("position", position);
		args.putString("fromwhere", fromwhere);
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
		fromwhere =bundle.getString("fromwhere");
		initData(fromwhere,postion);
		initView();
		return view;
	}

	private void initView(){
		listView =(ListView) view.findViewById(R.id.pull_refresh_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(act, ForrentDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(Share.FORRENT.getType(), data.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	private void initData(String fromwhere,int postion){
		
		SharedPreferences share ;
		Gson gson =new Gson();
		String source;
		Type type ;
		share=act.getSharedPreferences(Container.USER.getUsername(), 0);
		if(fromwhere.equals("history")){
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
		}else{
			switch (postion) {
			case 1:
				source=share.getString(Share.OLD_FOOTMARK.getType(), null);
				type =new TypeToken<List<SecondHouseValue>>(){}.getType();
				if(source!=null){
				data =gson.fromJson(source,type);
				adapter =new NewHouseAdapter(act, data);
				}
				break;

			case 0:
				source=share.getString(Share.FORRENT_FOOTMARK.getType(), null);
				type =new TypeToken<List<SecondHouseValue>>(){}.getType();
				if(source!=null){
				data =gson.fromJson(source,type);
				adapter =new ForrentHouseAdapter(act, data);
				}
				break;
			}
		}
		
	
	}
}
