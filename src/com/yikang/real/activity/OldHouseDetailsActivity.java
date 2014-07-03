package com.yikang.real.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.Collect;
import cn.Bean.util.OldHouseDetailsBean;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;
import com.yikang.real.view.DetialGallery;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class OldHouseDetailsActivity extends BaseActivity  {
	private OldHouseDetailsBean fhdb;
	private ImageView back;
	private String nid;
	String intro;
	MenuItem item;

	public Handler mOldHouseDetailsActivityHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			Responds<OldHouseDetailsBean> responde = (Responds<OldHouseDetailsBean>) msg.obj;
			switch (result) {
			case 0:
				break;
			default:
				List<OldHouseDetailsBean> data = responde.getRESPONSE_BODY()
						.get(Container.RESULT);
				fhdb = data.get(0);
				System.out.println(fhdb);
				findView();
				break;
			}

		}

	};

	public Handler collect = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
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
					
					Gson g =new Gson();
					SharedPreferences share =getSharedPreferences(Container.SHARENAME, 0);
					String sha= share.getString(Share.OLD.getType(), null);
					ArrayList<SecondHouseValue> list;
					if(sha==null){
						list=new ArrayList<SecondHouseValue>();
					}else {
						Type type =new TypeToken<ArrayList<SecondHouseValue>>(){}.getType();
						list= g.fromJson(sha, type);
					}
					list.add(value);
					sha =g.toJson(list);
					Editor editor =share.edit();
					editor.putString(Share.OLD.getType(), sha);
					editor.commit();
					showToast("收藏成功", 2000);
					item.setIcon(R.drawable.collected);
				} else {
					showToast("不要重复收藏", 2000);
				}
				findView();
				break;
			}
		}

	};

	private DetialGallery gallery;
	private DisplayImageOptions options;
	private Intent intent;
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
		actionbar.setTitle("二手房详情");

	}

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
			getCollect();
			this.item = item;
			break;
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_house_details);
		value= (SecondHouseValue) getIntent().getExtras().getSerializable(Share.OLD.getType());
		nid = value.getNid();
		getData();
		initActionBar();

		
	}

	private void findView() {

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showStubImage(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().build();

		// {"floor":1
		// ,"fitment":"精装",
		// "type":"",
		// "years":"2006",
		// "name":null,
		// "mob":null,
		// "add":"滇池卫城紫郢",
		// "namepath":"URL",
		// "lat":"24.980297936455 ",
		// "lng":"102.68820497036 ",
		// "desc":"1110",
		// "environmental":"杨家地农贸市场 正和农贸市场.太家河农贸市场 滇池卫城社区医院、儿童医院、圣约翰医院，同仁医院 ",
		// "education":"云南大学滇池学院 滇池旅游度假区实验中学 滇池旅游度假区实验小学 扬帆贝贝 爱英森电脑培训学",
		// "entertainment":"云南民族村、 海埂公园 大名星KTV 青少年活动中心 南亚风情园 西贡码头 千禧KTV 西",
		// "facility":"滇池学院，滇池卫城，金家村同仁医院\r\n172,A9,A4,106直接到滇池卫城，24，44,73路到",
		// "business":"康乐茶叶市场 、民升超市 、大商汇、 国美电器、 沃尔玛、南亚风情园 广福厨具市场、十一家具",
		// "image":["Photos/201404/CS1404000003/d07231dc03b147b498986176282cd88e.jpg"]}]}}

		TextView floor_TV = (TextView) findViewById(R.id.floor_TV);
		floor_TV.setText(fhdb.getFloor() == null ? "" : fhdb.getFloor());
		// 装修
		TextView fitment_TV = (TextView) findViewById(R.id.fitment_TV);
		fitment_TV.setText(fhdb.getFitment() == null ? "" : fhdb.getFitment());

		TextView main_mob =(TextView) findViewById(R.id.old_detail_mob);
		main_mob.setText(fhdb.getMob() == null ? "" : fhdb.getMob());
		
		TextView years_TV = (TextView) findViewById(R.id.years_TV);
		years_TV.setText(fhdb.getYears() == null ? "" : fhdb.getYears());

		TextView name_TV = (TextView) findViewById(R.id.name_TV);
		name_TV.setText(fhdb.getName() == null ? "" : fhdb.getName());

		TextView add_TV = (TextView) findViewById(R.id.add_TV);
		add_TV.setText(fhdb.getAdd() == null ? "" : fhdb.getAdd());

		// "environmental":"杨家地农贸市场 正和农贸市场.太家河农贸市场 滇池卫城社区医院、儿童医院、圣约翰医院，同仁医院 ",
		// "education":"云南大学滇池学院 滇池旅游度假区实验中学 滇池旅游度假区实验小学 扬帆贝贝 爱英森电脑培训学",
		// "entertainment":"云南民族村、 海埂公园 大名星KTV 青少年活动中心 南亚风情园 西贡码头 千禧KTV 西",
		// "facility":"滇池学院，滇池卫城，金家村同仁医院\r\n172,A9,A4,106直接到滇池卫城，24，44,73路到",
		// "business":"康乐茶叶市场 、民升超市 、大商汇、 国美电器、 沃尔玛、南亚风情园 广福厨具市场、十一家具",

		TextView zhoubianshebei = (TextView) findViewById(R.id.zhoubianshebei);
		zhoubianshebei.setText(fhdb.getEnvironmental() == null ? "" : fhdb
				.getFloor());

		gallery = (DetialGallery) findViewById(R.id.gallery);
		gallery.setAdapter(new ImagePagerAdapter(fhdb.getImage()));
		gallery.setSelection(0);

	}

	/**
	 * 获取 数据
	 */
	public void getData() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				request.setCommandcode("103");
				HashMap<String, String> body = new HashMap<String, String>();
				body.put("nid", nid);
				request.setREQUEST_BODY(body);
				Responds<OldHouseDetailsBean> response = (Responds<OldHouseDetailsBean>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<OldHouseDetailsBean>>() {
								}.getType());
				if (response != null) {
					mOldHouseDetailsActivityHandler.obtainMessage(1, response)
							.sendToTarget();
				}else
				mOldHouseDetailsActivityHandler.obtainMessage(0).sendToTarget();
			}
		}).start();

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
				body.put("nid", nid);
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
