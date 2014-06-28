package com.yikang.real.activity;

import java.lang.reflect.Type;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.Bean.util.Register;
import cn.Bean.util.SecondHandHouseDetails;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.application.RealApplication;
import com.yikang.real.bean.User;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	ActionBar actionbar;

	User user;

	private Button regist;

	private EditText regist_username;

	private EditText regist_pwd;

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

	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub

		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));
	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			
			if(regist_username.getText().toString().trim().length()==0){
				showToastResources(R.string.phone_number_err, 0);
				return; 
			}
			if(regist_pwd.getText().toString().trim().length()==0){
				showToastResources(R.string.pwd_err, 0);
				return; 
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
					openActivity(CheckedActivity.class);
					

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
