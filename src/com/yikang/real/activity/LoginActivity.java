package com.yikang.real.activity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.Bean.util.Login;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.application.RealApplication;
import com.yikang.real.bean.User;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;
import com.yikang.real.until.Util;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

@SuppressLint("ResourceAsColor")
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
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		actionbar.setHomeButtonEnabled(true);
		yourTextView.setTextColor(R.color.black);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		actionbar.setIcon(R.drawable.back);
	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	Handler loginhandler = new Handler() {

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
					SharedPreferences share = getSharedPreferences("user", 0);
					Editor edit = share.edit();
					edit.putString("tel", login_username.getText().toString());
					edit.putString(
							"id",
							String.valueOf(responde.getRESPONSE_BODY()
									.get("list").get(0).getLid()));
					edit.commit();
					User user = new User();
					user.setUsername(login_username.getText().toString());
					user.setUid(String.valueOf(responde.getRESPONSE_BODY()
							.get("list").get(0).getLid()));
					Container.setUSER(user);
					finish();
				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			if (login_username.getText().length() == 0) {
				login_username.setError("账号不能为空");
				return;
			}
			if (login_pwd.getText().length() == 0) {
				login_pwd.setError("密码不能为空");
				return;
			}
			if (Util.isMobileNO(login_username.getText().toString())) {
				login_username.setError("账号必须是手机号");
				return;
			}
			final HttpConnect conn = new HttpConnect();
			final Request reques = new Request();
			reques.setCommandcode("111");
			HashMap map = new HashMap<String, String>();
			map.put("username", login_username.getText().toString());
			map.put("password", login_pwd.getText().toString());
			reques.setREQUEST_BODY(map);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Responds<Login> responds = (Responds<Login>) conn
							.httpUrlConnection(reques,
									new TypeToken<Responds<Login>>() {
									}.getType());
					if (responds != null) {
						loginhandler.obtainMessage(1, responds).sendToTarget();
					} else {
						loginhandler.obtainMessage(0).sendToTarget();
					}

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
		login_username.setText("");
		login_pwd.setText("");
	}

}
