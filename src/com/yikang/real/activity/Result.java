package com.yikang.real.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.Bean.util.SearchSecondValue;
import cn.Bean.util.SecondHouseValue;
import cn.trinea.android.common.view.DropDownListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Page;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

@SuppressLint("ResourceAsColor")
public class Result extends BaseActivity implements OnItemClickListener {
	private Button button;
	private ActionBar actionbar;
	Intent intent;
	public ArrayList<SecondHouseValue> data_newHouse;
	public NewHouseAdapter adapter;
	DropDownListView lv_result;
	List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	ProgressBar bar;
	TextView tx_reset;
	int page = 0;

	Handler result = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			lv_result.setVisibility(View.VISIBLE);
			bar.setVisibility(View.GONE);
			int result = msg.what;
			Responds<SecondHouseValue> responde = (Responds<SecondHouseValue>) msg.obj;
			switch (result) {
			case 0:
				showToast("请求失败，请重试", 3000);
				break;

			default:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {

					List<SecondHouseValue> data = responde.getRESPONSE_BODY()
							.get(Container.RESULT);
					data_newHouse.clear();
					data_newHouse.addAll(data);
					 if (!responde.isRESPONSE_NEXTPAGE()) {
						 lv_result.setOnBottomStyle(false);
						}

				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}
			adapter.notifyDataSetChanged();

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		initView();
		init();
		initActionBar();
		getData();

	}

	public void init() {

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		data_newHouse = new ArrayList<SecondHouseValue>();
		adapter = new NewHouseAdapter(this, data_newHouse);
	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	protected void initView() {
		initData();
		bar = (ProgressBar) findViewById(R.id.result_progress);
		lv_result = (DropDownListView) findViewById(R.id.resutl_list);
		lv_result.setOnItemClickListener(this);

		lv_result.setAutoLoadOnBottom(true);
		lv_result.setDropDownStyle(false);
		lv_result.setOnBottomStyle(true);
		lv_result.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getData();
				page++;
			}
		});

		lv_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				openActivity(OldHouseDetailsActivity.class);
			}
		});
		
		lv_result.setAdapter(adapter);
		bar.setVisibility(View.GONE);

	}

	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub
		intent = getIntent();
		actionbar = getSupportActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		if (null != intent.getStringExtra("xid")) {
			actionbar.setTitle(intent.getStringExtra("title"));
		} else {
			actionbar.setTitle("搜索结果");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	private void getData() {
		lv_result.setVisibility(View.GONE);
		bar.setVisibility(View.VISIBLE);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				if(Container.getCurrentPage()==Page.FORREN){
					request.setCommandcode("115");
				}else if(Container.getCurrentPage()==Page.OLD){
					request.setCommandcode("113");
				}else {
					request.setCommandcode("113");
				}
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("xid", intent.getStringExtra("xid"));
				map.put("p", String.valueOf(page));
				request.setREQUEST_BODY(map);
				Responds<SecondHouseValue> responds = (Responds<SecondHouseValue>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<SecondHouseValue>>() {
								}.getType());

				if (responds != null) {
					result.obtainMessage(1, responds).sendToTarget();
				}else
					result.obtainMessage(0).sendToTarget();
			}
		});
		thread.start();
	}
}
