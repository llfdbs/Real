package com.yikang.real.activity;

import java.lang.reflect.Type;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.Bean.util.Login;
import cn.Bean.util.Register;
import cn.Bean.util.SecondHandHouseDetails;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.application.RealApplication;
import com.yikang.real.bean.User;
import com.yikang.real.until.Util;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	ActionBar actionbar;

	User user;

	private Button regist;

	private EditText regist_username;

	private EditText regist_pwd;

	Handler regist_handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			Responds<Login> responde = (Responds<Login>) msg.obj;
			switch (result) {
			case 0:
				showToast("请求失败，请重试", 3000);
				break;

			default:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {
					finish();
				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}
		}
		
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		findview();
		initActionBar();
		RealApplication.initImageLoader(getApplicationContext());
	}

	private void findview() {
		regist = (Button) findViewById(R.id.register);
		regist_username = (EditText) findViewById(R.id.login_username);
		regist_pwd = (EditText) findViewById(R.id.login_pwd);
		regist.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub


		actionbar = getSupportActionBar();
		int titleId = Resources.getSystem().getIdentifier(  
                "action_bar_title", "id", "android"); 
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		actionbar.setIcon(R.drawable.back);
		actionbar.setTitle("注册");
	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:

			if(regist_username.getText().length()==0){
				regist_username.setError("账号不能为空");
				return ;
			}
			if(regist_pwd.getText().length()==0){
				regist_pwd.setError("密码不能为空");
				return ;
			}
			if(Util.isMobileNO(regist_username.getText().toString())){
				regist_username.setError("账号必须是手机号");
				return ;
			}
			
			final HttpConnect conn = new HttpConnect();
			final Request reques = new Request();
			// REQUEST_BODY":{"username":"12" , "password":"xxx"}}
			reques.setCommandcode("110");
			HashMap map = new HashMap<String, String>();
			map.put("username",regist_username.getText().toString().trim());
			map.put("password",regist_pwd.getText().toString().trim());
			reques.setREQUEST_BODY(map);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Responds<Register> responds = (Responds<Register>) conn
							.httpUrlConnection(reques,
									new TypeToken<Responds<Register>>(){}.getType());
					
					if(responds!=null){
						regist_handler.obtainMessage(1, responds).sendToTarget();
					}else
						regist_handler.obtainMessage(0).sendToTarget();

				}
			}).start();

			break;

		default:
			background();
			break;
		}
	}

	public void background() {
		chance();
	}

	public void chance() {
	 
	}

}
