package com.yikang.real.activity;

import java.lang.reflect.Type;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.Bean.util.SearchSecondValue;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.map.OverlayDemo;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Page;
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
public class SearchActivity extends BaseActivity implements OnItemClickListener {

	ActionBar actionbar;
	ListView lv_result;
	SimpleAdapter adapter;
	List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	ProgressBar bar;
	EditText et_sreach;
	TextView tx_reset;

	Handler result = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			lv_result.setVisibility(View.VISIBLE);
			bar.setVisibility(View.GONE);
			int result = msg.what;
			Responds<SearchSecondValue> responde = (Responds<SearchSecondValue>) msg.obj;
			switch (result) {
			case 0:
				showToast("请求失败，请重试", 3000);
				break;

			default:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {

					List<SearchSecondValue> data_new = responde
							.getRESPONSE_BODY().get(Container.RESULT);
					data.clear();
					Gson g = new Gson();
					ArrayList<HashMap<String, String>> map = g.fromJson(
							g.toJson(data_new), ArrayList.class);
					data.addAll(map);

				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}
			adapter.notifyDataSetChanged();

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
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setTitle("搜索");

	}

	protected void initView() {
		et_sreach = (EditText) findViewById(R.id.searchediter);
		et_sreach.addTextChangedListener(textWatcher);
		bar = (ProgressBar) findViewById(R.id.search_progress);
		lv_result = (ListView) findViewById(R.id.search_list);
		lv_result.setOnItemClickListener(this);
		adapter = new SimpleAdapter(SearchActivity.this, data,
				R.layout.group_list_item, new String[] { "title" },
				new int[] { R.id.group_list_item_text });

		lv_result.setAdapter(adapter);
		bar.setVisibility(View.GONE);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		Type type =new TypeToken<ArrayList<HashMap<String,String>>>(){}.getType();
		Gson g = new Gson();
		ArrayList<HashMap<String, String>> temp = g.fromJson(
				g.toJson(data), type);
		bundle.putString("xid", temp.get(index).get("xid"));
		bundle.putString("title", temp.get(index).get("title"));
		if(null!=getIntent().getStringExtra("from")){
			Intent intent =getIntent();
			intent.setClass(SearchActivity.this, OverlayDemo.class);
			intent.putExtras(bundle);
			setResult(200,intent);
		}else {
			openActivity(Result.class, bundle);
		}
	}

	private void getData(final String key) {
		lv_result.setVisibility(View.GONE);
		bar.setVisibility(View.VISIBLE);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				if(Container.getCurrentPage()==Page.FORREN){
					request.setCommandcode("114");
				}else if(Container.getCurrentPage()==Page.OLD){
					request.setCommandcode("112");
				}else {
					request.setCommandcode("112");
				}
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("keyword", key);
				map.put("city", Container.getCity().getCity());
				request.setREQUEST_BODY(map);
				Responds<SearchSecondValue> responds = (Responds<SearchSecondValue>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<SearchSecondValue>>() {
								}.getType());

				if (responds != null) {
					result.obtainMessage(1, responds).sendToTarget();
				}else
					result.obtainMessage(0).sendToTarget();
			}
		});
		thread.start();
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			//

			getData(et_sreach.getText().toString());

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}
	};

}
