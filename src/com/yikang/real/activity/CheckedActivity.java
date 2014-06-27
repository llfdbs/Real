package com.yikang.real.activity;

import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.fragment.ForrentFragments;
import com.yikang.real.fragment.NewHouseFragment;
import com.yikang.real.fragment.OldHouseFragment;
import com.yikang.real.fragment.PersonCentrol;

 
public class CheckedActivity extends BaseActivity implements OnCheckedChangeListener {

 
	public RadioButton mTab1;
 
	public RadioButton mTab2;
 
	public RadioButton mTab3;
 
	public RadioButton mTab4;
 
	public ViewPager pager;

	public CompoundButton currentButtonView;

	Fragment[] fragments ;
	MyAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		mAdapter = new MyAdapter(getSupportFragmentManager());
		pager.setAdapter(mAdapter);
		mTab1.performClick();
	}

	private void findView() {
		mTab1=	(RadioButton) findViewById(R.id.radio_button0);
		mTab2 = (RadioButton) findViewById(R.id.radio_button1);
		mTab3 =(RadioButton) findViewById(R.id.radio_button2);
		mTab4 =(RadioButton) findViewById(R.id.radio_button3);
		pager=	(ViewPager) findViewById(R.id.pager);
		mTab1.setOnCheckedChangeListener(this);
		mTab2.setOnCheckedChangeListener(this);
		mTab3.setOnCheckedChangeListener(this);
		mTab4.setOnCheckedChangeListener(this);
	}

 
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
		      switch (buttonView.getId()) {
	            case R.id.radio_button1:
	             	pager.setCurrentItem(1);
	            	break;
	            case R.id.radio_button2:
	             	pager.setCurrentItem(2);
	            	break;
	            case R.id.radio_button3:
	             	pager.setCurrentItem(3);
	            	break;
	            case R.id.radio_button0:
	            default:
	            	pager.setCurrentItem(0);
	            	break;
	            }
		
		}
	}

	 public static class MyAdapter extends FragmentPagerAdapter {
	        public MyAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public int getCount() {
	            return 4;
	        }
	        	
	        @Override
	        public Fragment getItem(int position) {
	            switch (position) {
//	          		 租房
	            case 0:
	            	return ForrentFragments.instantiation(2);
//	            	二手房
	            case 1:
	                return OldHouseFragment.instantiation(0);
//	            	新房
	            case 2:
	                return NewHouseFragment.instantiation(1);
//	              	  个人
	            case 3:
	            default:
	                return new PersonCentrol();
	            }
	        }

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
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
}
