package com.yikang.real.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends BaseActivity implements OnClickListener {

	public Button login;

	public EditText login_username;

	public EditText login_pwd;

	ActionBar actionbar;

	User user;

	private TextView login_text_qa;

	private TextView login_text_regist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		findview();
		initActionBar();
		RealApplication.initImageLoader(getApplicationContext());
	}

	
	private void findview() {
		login = (Button) findViewById(R.id.login);
		login_username = (EditText) findViewById(R.id.login_username);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		login_text_regist = (TextView) findViewById(R.id.login_text_regist);
		login_text_qa = (TextView) findViewById(R.id.login_text_qa);
		login.setOnClickListener(this);
		login_text_regist.setOnClickListener(this);
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
		case R.id.login:
			final HttpConnect conn = new HttpConnect();
			final Request reques = new Request();
			reques.setCommandcode("103");
			HashMap map = new HashMap<String, String>();
			map.put("nid", "100001");
			reques.setREQUEST_BODY(map);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Responds<SecondHandHouseDetails> responds = (Responds<SecondHandHouseDetails>) conn
							.httpUrlConnection(
									reques,
									new TypeToken<Responds<SecondHandHouseDetails>>() {
									}.getType());
					openActivity(CheckedActivity.class);

				}
			}).start();
			break;
		case R.id.login_text_regist:
			openActivity(RegisterActivity.class);

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
		login_username.setText("test");
	}

}
