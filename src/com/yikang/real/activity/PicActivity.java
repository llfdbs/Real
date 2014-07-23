package com.yikang.real.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikang.real.R;
import com.yikang.real.activity.adapter.ViewPagerAdapter;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;

/**
 * class desc: 引导界面
 * @Version 1.0
 * @Author yaoyong
 * 
 * 
 */
public class PicActivity extends BaseActivity implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;
	private int currentPageScrollStatus;
	private String [] urls;

	private Boolean statue =false;
	private int postions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.guide);
		Intent intent =getIntent();
		urls=intent.getStringArrayExtra("urls");
		postions =intent.getIntExtra("postions", 0);
		// 初始化页面
		initViews();
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表
		addView(urls);
		// 初始化引导图片列表
		vpAdapter = new ViewPagerAdapter(views,this);
		
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
		vp.setCurrentItem(postions);
	}

	
    public void addView(String[] url){
    	for(int i=0;i<url.length;i++){
				views.add(getView(url[i]));
		}
    }
    
	public View getView(String id){
		View view = LayoutInflater.from(this).inflate(R.layout.pic_new_one, null);
		ImageView image = (ImageView)view.findViewById(R.id.image_res);
		imageLoader.displayImage(id, image, Container.adUrl_options);
		return view;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		currentPageScrollStatus = arg0;
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		TextView view =(TextView) findViewById(R.id.pic_count);
		view.setText(arg0+1+"/"+urls.length);
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
