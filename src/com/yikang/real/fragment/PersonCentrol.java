package com.yikang.real.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yikang.real.R;
import com.yikang.real.activity.AboutUS;
import com.yikang.real.activity.History;
import com.yikang.real.activity.LoginActivity;
import com.yikang.real.activity.PersonCenter;
import com.yikang.real.activity.Result;
import com.yikang.real.adapter.PersonCentrolAdp;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.until.ToastTools;

public class PersonCentrol extends Fragment {
	BaseActivity act;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		if (act == null) {
			act = (BaseActivity) activity;
		}
		super.onAttach(activity);
	}

	public static PersonCentrol instantiation() {
		PersonCentrol fragment = new PersonCentrol();
		return fragment;
	}

	public ListView person_centrol;

	String[] data = new String[] { "登陆/注册", "我的收藏", "浏览记录", "关于我们" };
	PersonCentrolAdp adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(act).inflate(R.layout.person, null);
		person_centrol = (ListView) view.findViewById(R.id.person_centrol);

		initData();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (Container.USER == null) {
			data[0]="登录/注册";
		} else {
			data[0]="用户中心";
		}
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	public void initData() {
		adapter = new PersonCentrolAdp(getActivity(), data);
		person_centrol.setAdapter(adapter);
		person_centrol.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				// TODO Auto-generated method stub
				onItemClicke(postion);
			}
		});
	}

	public void onItemClicke(int postion) {

		Intent intent = new Intent();
		switch (postion) {
		case 0:
			if (Container.USER == null) {
				intent.setClass(act, LoginActivity.class);
			} else {
				intent.setClass(act, PersonCenter.class);
			}
			startActivity(intent);
			break;
		case 1:
			if (null == Container.USER || null == Container.USER.getUid()) {
				ToastTools.showToastResources(act, R.string.login_warn, 2000);
				return;
			}
			intent.setClass(act, History.class);
			intent.putExtra("fromwhere", "history");
			act.startActivity(intent);
			break;

		case 2:
			if (null == Container.USER || null == Container.USER.getUid()) {
				ToastTools.showToastResources(act, R.string.login_warn, 2000);
				return;
			}
			intent.setClass(act, History.class);
			intent.putExtra("fromwhere", "footmark");
			act.startActivity(intent);
			break;
		default:
			 intent.setClass(act, AboutUS.class);
			 act.startActivity(intent);
			break;
		}
	}

}
