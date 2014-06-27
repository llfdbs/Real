package com.yikang.real.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import com.yikang.real.R;
import com.yikang.real.activity.Sa;
import com.yikang.real.adapter.PersonCentrolAdp;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.until.ToastTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

@EFragment(R.layout.person)
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
	
	@ViewById
	public ListView person_centrol;

	String[] data = new String[] { "我的收藏", "浏览记录", "房贷计算器", "关于我们" };
	PersonCentrolAdp adapter = null;

	@AfterViews
	public void initData() {
		adapter = new PersonCentrolAdp(getActivity(), data);
		person_centrol.setAdapter(adapter);
	}

	@ItemClick(R.id.person_centrol)
	public void onItemClicke(int postion) {

		if (null == Container.USER || null == Container.USER.getUid()) {
			ToastTools.showToastResources(act, R.string.login_warn, 2000);
			return;
		}

		Intent intent = new Intent();
		switch (postion) {
		case 0:
			intent.setClass(act, Sa.class);
			act.startActivity(intent);
			break;
		case 1:
			intent.setClass(act, Sa.class);
			act.startActivity(intent);
			break;
		case 2:
			intent.setClass(act, Sa.class);
			act.startActivity(intent);
			break;

		default:
			intent.setClass(act, Sa.class);
			act.startActivity(intent);
			break;
		}
	}

}
