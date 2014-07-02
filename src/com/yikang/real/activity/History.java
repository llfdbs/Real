package com.yikang.real.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.fragment.ForrentFragments;
import com.yikang.real.fragment.ForrentHistory;
import com.yikang.real.fragment.NewHouseFragment;
import com.yikang.real.fragment.OldHouseFragment;
import com.yikang.real.fragment.PersonCentrol;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Page;

@SuppressLint("ResourceAsColor")
public class History extends BaseActivity implements OnCheckedChangeListener {

	public RadioButton mTab1;

	public RadioButton mTab2;

	public ViewPager pager;

	Fragment[] fragments;
	MyAdapter mAdapter;

	private ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		findView();
		AfterView();
		mTab1.performClick();
	}

	private void findView() {
		mTab1 = (RadioButton) findViewById(R.id.radio_button0);
		mTab2 = (RadioButton) findViewById(R.id.radio_button1);
		pager = (ViewPager) findViewById(R.id.pager);
		mTab1.setOnCheckedChangeListener(this);
		mTab2.setOnCheckedChangeListener(this);

		ActionBar acb = getSupportActionBar();
		acb.hide();
	}

	private void AfterView() {
		ForrentHistory forrent = ForrentHistory.instantiation(0);
		ForrentHistory old = ForrentHistory.instantiation(1);
	
		fragments = new Fragment[] { forrent, old};
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
			return 4;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			// 租房
			case 0:
				return frags[0];
				// 二手房
			case 1:
				return frags[1];
				// 新房

			}
			return null;
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
				actionbar = getSupportActionBar();
				actionbar.setHomeButtonEnabled(true);
				actionbar.setIcon(R.drawable.back);
				actionbar.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.top));
				int titleId = Resources.getSystem().getIdentifier("action_bar_title",
						"id", "android");
				TextView yourTextView = (TextView) findViewById(titleId);
				yourTextView.setTextColor(R.color.black);
				actionbar.setTitle("我的收藏");
	}

}
