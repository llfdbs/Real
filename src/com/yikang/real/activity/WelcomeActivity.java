package com.yikang.real.activity;

import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import cn.Bean.util.Adurl;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

/**
 * 欢迎页
 * 
 * @author vv
 * 
 */
public class WelcomeActivity extends BaseActivity {
	private Button btn_jump = null;
	private ActionBar actionBar;// 导航栏
	private ImageView iv_adurl;
	
	Handler advert =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			Responds<Adurl> responde = (Responds<Adurl>) msg.obj;
			switch (result) {
			case 0:
				showToast("请求失败，请重试", 3000);
				break;

			default:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {
					String url=responde.getRESPONSE_BODY().get("list").get(0).getAdurl3();
					imageLoader.displayImage(url, iv_adurl, Container.adUrl_options);
				
				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}
		}
		
	};
	
	private void Request() {
		Thread citylist = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 获取网络城市列表
				final HttpConnect conn = new HttpConnect();
				final Request reques = new Request();
				reques.setCommandcode("130");
				HashMap map = new HashMap<String, String>();
				reques.setREQUEST_BODY(map);
				Responds<Adurl> responds = (Responds<Adurl>) conn
						.httpUrlConnection(reques,
								new TypeToken<Responds<Adurl>>() {
								}.getType());
				if (responds != null) {
					advert.obtainMessage(1,
							responds.getRESPONSE_BODY().get("list"))
							.sendToTarget();
				}
				advert.obtainMessage(0).sendToTarget();
			}
		});
		citylist.start();
	}

	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		final View view = View.inflate(this, R.layout.welcome, null);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(view);
		System.out.println(Build.VERSION.SDK_INT + "  ");
		ImageView bg =(ImageView) view.findViewById(R.id.welcome_bg);
		iv_adurl =(ImageView) view.findViewById(R.id.welcome_adurl);
		iv_adurl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				_jump();
			}
		});
		MyAnimation(bg);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		ActionBar action = getSupportActionBar();
		action.hide();
		return true;
	}

	/**
	 * 启动动画类
	 */
	private void MyAnimation(final View view) {
		// 渐变展示启动屏,从透明到不透明
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		// 持续时间3秒
		aa.setDuration(3000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				// 动画结束时跳转页面

				SharedPreferences share = getSharedPreferences("footprint", 0);
				Intent _Intent = null;
				if (share.getBoolean("frist", false)) {
					view.setVisibility(View.GONE);
					iv_adurl.setVisibility(View.VISIBLE);
				
				} else {
					_Intent = new Intent(WelcomeActivity.this,
							GuideActivity.class);
					redirectTo(_Intent);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});
	}

	private void _jump(){
		Intent _Intent = null;
		if (Container.getCity() == null) {
			_Intent = new Intent(WelcomeActivity.this,
					CityList.class);
		} else {
			_Intent = new Intent(WelcomeActivity.this,
					CheckedActivity.class);
		}
		redirectTo(_Intent);
	}
	
	/**
	 * 跳转到... 销毁当前欢迎页
	 */
	private void redirectTo(Intent _Intent) {

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
