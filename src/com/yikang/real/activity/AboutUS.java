package com.yikang.real.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.About;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class AboutUS extends BaseActivity {

	private ActionBar actionbar;
	TextView tv_about ;
	ImageView iv_about;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initActionBar();
		initListeners();
		getData(); 

	}

	Handler result = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int result = msg.what;
			Responds<About> responde = (Responds<About>) msg.obj;
			switch (result) {
			case 0:
				showToast("请求失败，请重试", 3000);
				break;

			default:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {

					List<About> data_new = responde
							.getRESPONSE_BODY().get(Container.RESULT);
					tv_about.setText(data_new.get(0).getAboutus());
					ImageLoader loader = ImageLoader.getInstance();
					loader.displayImage(HttpConnect.picUrl+data_new.get(0).getAboutusurl(), iv_about,Container.options);
					
				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}

		}

	};
	
	
	private void getData() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				request.setCommandcode("129");
				Responds<About> responds = (Responds<About>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<About>>() {
								}.getType());

				if (responds != null) {
					result.obtainMessage(1, responds).sendToTarget();
				}else
					result.obtainMessage(0).sendToTarget();
			}
		});
		thread.start();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub
		tv_about =(TextView) findViewById(R.id.about_us);
		iv_about =(ImageView) findViewById(R.id.about_image);
	
	}

	@SuppressLint("ResourceAsColor")
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
		actionbar.setTitle("关于我们");
	}

}
