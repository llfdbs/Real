package com.yikang.real.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.Collect;
import cn.Bean.util.ForrentHouseDetailsBean;
import cn.Bean.util.OldHouseDetailsBean;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.imp.PublicDb;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class ForrentDetailsActivity extends BaseActivity 
		 {

	private TextView title;
	private com.yikang.real.view.DetialGallery gallery;
	private TextView zujin;
	private TextView louxing;
	private String nid;
	private ForrentHouseDetailsBean fhdb;
	private ActionBar actionbar;
	private SecondHouseValue value;

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
		actionbar.setTitle("租房详情");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forrent_details);

		value= (SecondHouseValue) getIntent().getExtras().getSerializable(Share.FORRENT.getType());
		nid = value.getNid();
		getData();
		initActionBar();
	}

	public Handler mForrentDetailsActivityHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			Responds<ForrentHouseDetailsBean> responde = (Responds<ForrentHouseDetailsBean>) msg.obj;
			switch (result) {
			case 0:
				break;
			default:
				List<ForrentHouseDetailsBean> data = responde
						.getRESPONSE_BODY().get(Container.RESULT);
				fhdb = data.get(0);
				findView();
				break;
			}

		}

	};
	private TextView fitment_TV;
	private TextView toward_TV;
	private TextView type_TV;
	private TextView address_TV;
	private TextView state_TV;
	private TextView com_TV;
	private TextView config_TV;
	private TextView environmental_TV;
	private TextView facility_TV;
	private TextView name_TV;
	private TextView mob_TV;
	private DisplayImageOptions options;
	private TextView mian_mob_TV;

	/**
	 * 获取 数据
	 */
	public void getData() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				request.setCommandcode("109");
				HashMap<String, String> body = new HashMap<String, String>();
				body.put("nid", nid);
				request.setREQUEST_BODY(body);
				Responds<ForrentHouseDetailsBean> response = (Responds<ForrentHouseDetailsBean>) new HttpConnect()
						.httpUrlConnection(
								request,
								new TypeToken<Responds<ForrentHouseDetailsBean>>() {
								}.getType());
				if (response != null) {
					mForrentDetailsActivityHandler.obtainMessage(1, response)
							.sendToTarget();
				}else
				mForrentDetailsActivityHandler.obtainMessage(0).sendToTarget();
			}
		}).start();

	}

	private void findView() {

		// request.getParameter("bizorderid")==null?"":request.getParameter("bizorderid");
		title = (TextView) findViewById(R.id.title);

		options = Container.options;
		gallery = (com.yikang.real.view.DetialGallery) findViewById(R.id.gallery);

		gallery.setAdapter(new ImagePagerAdapter(fhdb.getImage()));
		gallery.setSelection(0);

		// title.setText(fh.getTitle()==null?"":fhdb.getTitle());
		// zujin = (TextView) findViewById(R.id.zujin);
		// zujin.setText(fh.getPrice()==null?"":fh.getPrice()+"/月");
		// louxing = (TextView) findViewById(R.id.louxing);
		// louxing.setText(fh.getPrice()==null?"":fh.getPrice()+"/月");

		fitment_TV = (TextView) findViewById(R.id.fitment_TV);
		// fhdb.getFitment()==null?"":fhdb.getFitment()
		fitment_TV.setText(fhdb.getFitment() == null ? "" : fhdb.getFitment());

		
		mob_TV= (TextView) findViewById(R.id.forrent_mob);
		mob_TV.setText(fhdb.getMob()==null?"":fhdb.getMob());
		
		mian_mob_TV= (TextView) findViewById(R.id.forrent_main_mob);
		mian_mob_TV.setText(fhdb.getMob()==null?"":fhdb.getMob());
		
		
		toward_TV = (TextView) findViewById(R.id.toward_TV);
		toward_TV.setText(fhdb.getToward() == null ? "" : fhdb.getToward());

		type_TV = (TextView) findViewById(R.id.type_TV);
		type_TV.setText(fhdb.getType() == null ? "" : fhdb.getType());

		address_TV = (TextView) findViewById(R.id.address_TV);
		address_TV.setText(fhdb.getAddress() == null ? "" : fhdb.getAddress());

		state_TV = (TextView) findViewById(R.id.state);
		state_TV.setText(fhdb.getState() == null ? "" : fhdb.getState());

		com_TV = (TextView) findViewById(R.id.com_TV);
		com_TV.setText(fhdb.getAddress() == null ? "" : fhdb.getCom());

		config_TV = (TextView) findViewById(R.id.config_TV);
		config_TV.setText(fhdb.getConfig() == null ? "" : fhdb.getConfig());

		environmental_TV = (TextView) findViewById(R.id.environmental_TV);
		environmental_TV.setText(fhdb.getEnvironmental() == null ? "" : fhdb
				.getEnvironmental());

		facility_TV = (TextView) findViewById(R.id.facility_TV);
		facility_TV.setText(fhdb.getFacility() == null ? "" : fhdb
				.getFacility());

		name_TV = (TextView) findViewById(R.id.name_TV);
		name_TV.setText(fhdb.getName() == null ? "" : fhdb.getName());

	}

	/**
	 * 获取 数据
	 */
	public void getCollect() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				request.setCommandcode("124");
				HashMap<String, String> body = new HashMap<String, String>();
				body.put("nid", nid.trim());
				body.put("username", Container.getUSER().getUsername());
				request.setREQUEST_BODY(body);
				Responds<Collect> response = (Responds<Collect>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<Collect>>() {
								}.getType());
				if (response != null) {
					collect.obtainMessage(1, response).sendToTarget();
				}else
				collect.obtainMessage(0).sendToTarget();
			}
		}).start();

	}

	
	public Handler collect = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			new PublicDb().save(ForrentDetailsActivity.this,value,Share.FORRENT.getType());
			int result = msg.what;
			Responds<Collect> responde = (Responds<Collect>) msg.obj;
			switch (result) {
			case 0:
				showToast("请求失败", 2000);
				break;
			default:
				List<Collect> data = responde.getRESPONSE_BODY().get(
						Container.RESULT);
				if (data.get(0).getState().equals("1")) {
					showToast("收藏成功", 2000);
				} else {
					showToast("不要重复收藏", 2000);
				}
				break;
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:

			finish();

			break;
		case R.id.action_settings:
			if(Container.getUSER()==null){
				showToast("请先登录", 2000);
				return true;
			}
			item.setIcon(R.drawable.collected);
			getCollect();
			break;
		}
		return true;
	}


	private class ImagePagerAdapter extends BaseAdapter {

		private List<String> images;
		private LayoutInflater inflater;

		ImagePagerAdapter(List<String> images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) inflater.inflate(
						R.layout.item_gallery_image, parent, false);
			}
			imageLoader.displayImage(HttpConnect.picUrl + images.get(position),
					imageView, options);
			return imageView;
		}
	}
}
