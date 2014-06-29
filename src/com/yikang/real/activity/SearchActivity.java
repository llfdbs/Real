package com.yikang.real.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

/**
 * 搜索界面
 * 
 * @author vv
 * 
 */
@SuppressLint("ResourceAsColor")
public class SearchActivity extends BaseActivity implements
		OnItemClickListener, OnEditorActionListener {

	ActionBar actionbar;
	ListView lv_result;
	SimpleAdapter adpter;
	List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	ProgressBar bar;
	EditText et_sreach;
	TextView tx_reset;

	Handler result = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}

	};

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

		actionbar = getSupportActionBar();
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
	}

	protected void initView() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		initView();
		initActionBar();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	private void getData(final String key) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				request.setCommandcode("112");
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("keyword", key);
				map.put("city", Container.getCity().getCity());
				request.setREQUEST_BODY(map);
				Responds<SecondHouseValue> responds = (Responds<SecondHouseValue>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<SecondHouseValue>>() {
								}.getType());
				
				if(responds!=null){
					result.obtainMessage(1, responds).sendToTarget();
				}
				result.obtainMessage(0).sendToTarget();
			}
		});
		thread.start();
	}

}
