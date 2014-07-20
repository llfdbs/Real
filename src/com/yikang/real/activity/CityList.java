package com.yikang.real.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.Bean.util.City;
import cn.Bean.util.SecondHandHouseDetails;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CityList extends BaseActivity {
	ListView listView;
	private List<Integer> positionList = new ArrayList<Integer>();
	private List<String> indexList = new ArrayList<String>();
	MyAdapter mAd;
	public List<String> listTag = new ArrayList<String>();
	ArrayList<String> list_data = new ArrayList<String>();
	List<City> city_list = null;
	Handler cityHanlder = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			if (result == 1) {
				city_list = (List<City>) msg.obj;
				list_data
						.addAll((ArrayList<String>) getListViewData((List<City>) msg.obj));
				mAd.notifyDataSetChanged();
			} else {
				showToast("请求出错", Toast.LENGTH_LONG);
			}

		}

	};
	private ActionBar actionbar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView) findViewById(R.id.listView);
		TextView local = (TextView) findViewById(R.id.main_local);
		local.setText(Container.getCity() != null ? Container.getCity()
				.getCity() : "昆明");

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("List " + arg2);

				Intent intent = getIntent();
				intent.setClass(CityList.this, CheckedActivity.class);
				SharedPreferences share = getSharedPreferences("city",
						Context.MODE_PRIVATE);
				Editor editor = share.edit();
				editor.putString("name", city_list.get(arg2).getCity());
				editor.putFloat("lat", city_list.get(arg2).getLat());
				editor.putFloat("lng", city_list.get(arg2).getLng());
				editor.commit();
				if (null == Container.getCity()) {
					Container.setCity(city_list.get(arg2));
					intent.putExtra("city", list_data.get(arg2));
					openActivity(CheckedActivity.class);
					finish();
				}
				System.out.println(Container.getCity().getCity());
				Container.setCity(city_list.get(arg2));
				intent.putExtra("city", list_data.get(arg2));
				setResult(200, intent);
				finish();
			}
		});
		initActionBar();
		init();
	}

	private void init() {
		mAd = new MyAdapter(this,
				android.R.layout.simple_expandable_list_item_1, list_data);
		listView.setAdapter(mAd);
		Request();
	}

	private void Request() {
		Thread citylist = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 获取网络城市列表
				final HttpConnect conn = new HttpConnect();
				final Request reques = new Request();
				reques.setCommandcode("100");
				HashMap map = new HashMap<String, String>();
				reques.setREQUEST_BODY(map);
				Responds<City> responds = (Responds<City>) conn
						.httpUrlConnection(reques,
								new TypeToken<Responds<City>>() {
								}.getType());
				if (responds != null) {
					cityHanlder.obtainMessage(1,
							responds.getRESPONSE_BODY().get("list"))
							.sendToTarget();
				}
				cityHanlder.obtainMessage(0).sendToTarget();
			}
		});
		citylist.start();
	}

	private List<String> getListViewData(List<City> data_src) {
		List<String> data = new ArrayList<String>();

		for (City city : data_src) {
			data.add(city.getCity());
		}
		return data;
	}

	class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);

		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public boolean isEnabled(int position) {
			return !listTag.contains(getItem(position));

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (listTag.contains(getItem(position))) {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.group_list_item_tag, null);
			} else {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.group_list_item, null);
			}

			TextView textView = (TextView) view
					.findViewById(R.id.group_list_item_text);
			textView.setText(getItem(position));

			return view;
		}

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

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
		actionbar.setTitle("城市列表");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:

			if (Container.getCity() == null) {
				City city = new City();
				city.setCity("昆明");
				city.setLat((float) 24.872058314636);
				city.setLng((float) 102.59540044824);
				Container.setCity(city);
				openActivity(CheckedActivity.class);
				finish();
			} else {
				finish();
				
			}
			break;
		}
		return true;
	}

}