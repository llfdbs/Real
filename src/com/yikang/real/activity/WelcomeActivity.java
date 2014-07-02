package com.yikang.real.activity;

import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;

/**
 * 欢迎页
 * 
 * @author vv
 * 
 */
public class WelcomeActivity extends BaseActivity{
	private Button btn_jump = null;
	private ActionBar actionBar;//导航栏
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		final View view = View.inflate(this, R.layout.welcome, null);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(view);
		System.out.println(Build.VERSION.SDK_INT+"  " );

		MyAnimation(view);


	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 ActionBar action =getSupportActionBar();
		 action.hide();
		return true;
	}



	/**
	 * 启动动画类
	 */
	private void MyAnimation(View view) {
		// 渐变展示启动屏,从透明到不透明
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		// 持续时间3秒
		aa.setDuration(3000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				// 动画结束时跳转页面
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});
	}

	/**
	 * 跳转到... 销毁当前欢迎页
	 */
	private void redirectTo() {
		Intent _Intent = new Intent(WelcomeActivity.this, CheckedActivity.class);
		startActivity(_Intent);
		WelcomeActivity.this.finish();
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
