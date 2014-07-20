package com.yikang.real.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.fragment.ForrentFragments;
import com.yikang.real.fragment.NewHouseFragment;
import com.yikang.real.fragment.OldHouseFragment;
import com.yikang.real.fragment.PersonCentrol;
import com.yikang.real.map.OverlayDemo;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Page;

public class CheckedActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener {

	public RadioButton mTab1;

	public RadioButton mTab2;

	public RadioButton mTab3;

	public RadioButton mTab4;

	public ViewPager pager;

	public CompoundButton currentButtonView;

	public TextView localcity, map;

	public EditText search;

	private static RadioGroup main_radio;
	Fragment[] fragments;
	MyAdapter mAdapter;
	
	private static LinearLayout topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		AfterView();
		mTab1.performClick();
	}

	public static int getButtomHight(){
		return main_radio.getHeight();
	}
	
	public static int getActionBarHeight(){
		return topbar.getHeight();
	} 
	
	private void findView() {
		mTab1 = (RadioButton) findViewById(R.id.radio_button0);
		mTab2 = (RadioButton) findViewById(R.id.radio_button1);
//		mTab3 = (RadioButton) findViewById(R.id.radio_button2);
		mTab4 = (RadioButton) findViewById(R.id.radio_button3);
		main_radio =(RadioGroup) findViewById(R.id.main_radio);
		
		
		pager = (ViewPager) findViewById(R.id.pager);
		mTab1.setOnCheckedChangeListener(this);
		mTab2.setOnCheckedChangeListener(this);
//		mTab3.setOnCheckedChangeListener(this);
		mTab4.setOnCheckedChangeListener(this);
		topbar= (LinearLayout) findViewById(R.id.topbar);
		localcity = (TextView) findViewById(R.id.topbar_local);
		map = (TextView) findViewById(R.id.topbar_map);
		search = (EditText) findViewById(R.id.topbar_search);
		localcity.setOnClickListener(this);
		map.setOnClickListener(this);
		search.setOnClickListener(this);
		
		
		ActionBar acb=getSupportActionBar();
		acb.hide();
		setlocation();
	}
	
	private void setlocation(){
		localcity.setText(Container.getCity().getCity());
	}
	


	private void AfterView() {
		ForrentFragments forrent = new ForrentFragments();
		OldHouseFragment old = new OldHouseFragment();
		NewHouseFragment newH = new NewHouseFragment();
		PersonCentrol centrol = new PersonCentrol();
		fragments = new Fragment[] { forrent, old,  centrol };
		mAdapter = new MyAdapter(getSupportFragmentManager(), fragments);
		pager.setAdapter(mAdapter);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_button1:
				Container.setCurrentPage(Page.OLD);
				pager.setCurrentItem(1);
				break;
//			case R.id.radio_button2:
//				Container.setCurrentPage(Page.NEW);
//				pager.setCurrentItem(2);
//				break;
			case R.id.radio_button3:
				Container.setCurrentPage(Page.PERSON);
				pager.setCurrentItem(2);
				break;
			case R.id.radio_button0:
			default:
				Container.setCurrentPage(Page.FORREN);
				pager.setCurrentItem(0);
				break;
			}

		}
	}

	public static class MyAdapter extends FragmentPagerAdapter {

		Fragment[] frags;

		public MyAdapter(FragmentManager fm, Fragment[] frags) {
			super(fm);
			this.frags = frags;
		}

		@Override
		public int getCount() {
//			return 4;
			return frags.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			// 租房
			case 0:
				return frags[position];
				// 二手房
//			case 1:
//				return frags[1];
//				// 新房
			case 1:
				return frags[position];
				// 个人
			case 2:
			default:
				return frags[position];
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			Log.v(getClass().getName(), "destroyItem");
		}

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.topbar_local:
			openActivityForResult(CityList.class, null, 501);
			break;
		case R.id.topbar_search:
			openActivity(SearchActivity.class);
			break;
		case R.id.topbar_map:
			openActivity(OverlayDemo.class);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int request, int responds, Intent intent) {
		// TODO Auto-generated method stub
		
		switch (request) {
		case 501:
			if(responds==200){
				setlocation();
			}
			break;

		default:
			break;
		}
		super.onActivityResult(request, responds, intent);
	}
	
	
}
