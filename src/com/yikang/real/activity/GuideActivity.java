package com.yikang.real.activity;

import java.util.ArrayList;
import java.util.List;

import com.yikang.real.R;
import com.yikang.real.activity.adapter.ViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * class desc: 引导界面
 * @Version 1.0
 * @Author yaoyong
 * 
 * 
 */
public class GuideActivity extends Activity implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;
	private int currentPageScrollStatus;
	private int drawable[] = {R.drawable.guide1800,R.drawable.guide2800,R.drawable.guide3800};

	private Boolean statue =false;

	private void setStatue(Boolean statue){
		this.statue= statue;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.guide);
		
		// 初始化页面
		initViews();

		// 初始化底部小点
		initDots();
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表
		addView(drawable);
		// 初始化引导图片列表
        
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views,this);
		
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
	}
	// 初始化引导图片列表
    public void addView(int drawable[]){
    	for(int i=0;i<drawable.length;i++){
			if(i==drawable.length-1){
				views.add(getView(drawable[drawable.length-1],statue));
			}else{
				views.add(getView(drawable[i],statue));
			}
		}
    }
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[views.size()];
		Log.v("guide views lenght ",String.valueOf(views.size()));
		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		currentPageScrollStatus = arg0;
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if(currentIndex == views.size()-1){
			if(arg2==0 && currentPageScrollStatus==1){
				currentPageScrollStatus = 0;
				goHome();
			}
		}
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurrentDot(arg0);
	}
	private void goHome() {
		SharedPreferences share = getSharedPreferences("footprint", 0);
		Editor edit =share.edit();
		edit.putBoolean("frist", true);
		edit.commit();
		// 跳转
		Intent intent = new Intent(GuideActivity.this, CheckedActivity.class);
		startActivity(intent);
		finish();
	}
	
	public View getView(int id,Boolean b){
		View view = LayoutInflater.from(GuideActivity.this).inflate(R.layout.what_new_one, null);
		ImageView image = (ImageView)view.findViewById(R.id.image);
		image.setBackgroundResource(id);
		return view;
	}
}
