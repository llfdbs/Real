package com.yikang.real.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
		AfterView();
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
	
	private void AfterView(){
		ForrentFragments forrent =new ForrentFragments();
		OldHouseFragment old= new OldHouseFragment();
		NewHouseFragment newH =new NewHouseFragment();
		PersonCentrol centrol =new PersonCentrol();
		fragments =new Fragment[]{forrent,old,newH,centrol};
		mAdapter = new MyAdapter(getSupportFragmentManager(),fragments);
		pager.setAdapter(mAdapter);
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
		 
		 Fragment[] frags;
	        public MyAdapter(FragmentManager fm,Fragment[] frags) {
	            super(fm);
	            this.frags=frags;
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
	            	return frags[0];
//	            	二手房
	            case 1:
	                return frags[1];
//	            	新房
	            case 2:
	                return frags[2];
//	              	  个人
	            case 3:
	            default:
	                return frags[3];
	            }
	        }

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
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
}
