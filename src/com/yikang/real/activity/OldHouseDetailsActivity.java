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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.imp.PublicDb;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;
import com.yikang.real.view.DetialGallery;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class OldHouseDetailsActivity extends BaseActivity {
	private OldHouseDetailsBean fhdb;
	private String nid;
	String intro;

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

			new PublicDb().save(OldHouseDetailsActivity.this, value,
					Share.OLD.getType());
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
				findView();
				break;
			}
		}

	};

	private DetialGallery gallery;
	private DisplayImageOptions options;
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
			if (Container.getUSER() == null) {
				showToast("请先登录", 2000);
				return true;
			}
			item.setIcon(R.drawable.collected);
			getCollect();
			break;
		case R.id.action_fenxiang:
			ShareSDK.initSDK(this);
			OnekeyShare oks = new OnekeyShare();
			// 关闭sso授权
			oks.disableSSOWhenAuthorize();

			// 分享时Notification的图标和文字
			oks.setNotification(R.drawable.ic_launcher,
					getString(R.string.app_name));
			// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
			oks.setTitle(getString(R.string.share));
			// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
			oks.setTitleUrl("http://sharesdk.cn");
			// text是分享文本，所有平台都需要这个字段
			oks.setText("我是分享文本");
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			oks.setImagePath("/sdcard/test.jpg");
			// url仅在微信（包括好友和朋友圈）中使用
			oks.setUrl("http://sharesdk.cn");
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用
			oks.setComment("我是测试评论文本");
			// site是分享此内容的网站名称，仅在QQ空间使用
			oks.setSite(getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			oks.setSiteUrl("http://sharesdk.cn");

			// 启动分享GUI
			oks.show(this);
			break;

		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_house_details);
		value = (SecondHouseValue) getIntent().getExtras().getSerializable(
				Share.OLD.getType());
		nid = value.getNid();
		getData();
		initActionBar();

	}

	private void findView() {

		options = Container.options;

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

		TextView floor_TV = (TextView) findViewById(R.id.floor_FV);
		floor_TV.setText(fhdb.getFloor() == null ? "" : fhdb.getFloor());
		// 装修
		TextView fitment_TV = (TextView) findViewById(R.id.fitment_FV);
		fitment_TV.setText(fhdb.getFitment() == null ? "" : fhdb.getFitment());

		TextView main_mob = (TextView) findViewById(R.id.old_detail_mob);
		main_mob.setText(fhdb.getMob() == null ? "" : fhdb.getMob());

		TextView years_TV = (TextView) findViewById(R.id.years_FV);
		years_TV.setText(fhdb.getYears() == null ? "" : fhdb.getYears());

		TextView name_TV = (TextView) findViewById(R.id.name_FV);
		name_TV.setText(fhdb.getName() == null ? "" : fhdb.getName());

		TextView add_TV = (TextView) findViewById(R.id.add_FV);
		add_TV.setText(fhdb.getAdd() == null ? "" : fhdb.getAdd());
		
		TextView price_FV= (TextView) findViewById(R.id.price_FV);
		price_FV.setText(value.getPrice()==null?"":value.getPrice());
		
		TextView type_FV= (TextView) findViewById(R.id.type_FV);
		type_FV.setText(fhdb.getType()==null?"":fhdb.getType());

		TextView Area_FV= (TextView) findViewById(R.id.area_FV);
		Area_FV.setText(value.getArea()==null?"":value.getArea());
		
		TextView com_FV =(TextView) findViewById(R.id.com_FV);
		com_FV .setText(fhdb.getName()==null?"":fhdb.getName());
		
		TextView desc_FV =(TextView) findViewById(R.id.desc_FV);
		desc_FV .setText(fhdb.getDesc()==null?"":fhdb.getDesc());
		
		ImageView head =(ImageView) findViewById(R.id.gerentouxiang);
		ImageLoader loader = ImageLoader.getInstance();
		
		if(fhdb.getNamepath()!=null){
			
			loader.displayImage(HttpConnect.picUrl+fhdb.getNamepath(), head,options);
		}
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
				if(Container.getUSER()!=null){
					body.put("tel", Container.getUSER().getUsername());
				}
				request.setREQUEST_BODY(body);
				Responds<OldHouseDetailsBean> response = (Responds<OldHouseDetailsBean>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<OldHouseDetailsBean>>() {
								}.getType());
				if (response != null) {
					mOldHouseDetailsActivityHandler.obtainMessage(1, response)
							.sendToTarget();
				} else
					mOldHouseDetailsActivityHandler.obtainMessage(0)
							.sendToTarget();
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
				if(Container.getUSER()!=null){
					body.put("tel", Container.getUSER().getUsername());
				}
				request.setREQUEST_BODY(body);
				Responds<Collect> response = (Responds<Collect>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<Collect>>() {
								}.getType());
				if (response != null) {
					collect.obtainMessage(1, response).sendToTarget();
				} else
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
