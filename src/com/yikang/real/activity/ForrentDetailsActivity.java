package com.yikang.real.activity;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.Collect;
import cn.Bean.util.ForrentHouseDetailsBean;
import cn.Bean.util.SecondHouseValue;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.imp.PublicDb;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class ForrentDetailsActivity extends BaseActivity {

	private TextView title;
	private com.yikang.real.view.DetialGallery gallery;
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

		value = (SecondHouseValue) getIntent().getExtras().getSerializable(
				Share.FORRENT.getType());
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
	private TextView transaction_TV;
	private TextView zujin;
	private TextView area_TV;
	private TextView floor_TV;
	private TextView person_TV;

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
				if(Container.getUSER()!=null){
					body.put("tel", Container.getUSER().getUsername());
				}
				request.setREQUEST_BODY(body);
				Responds<ForrentHouseDetailsBean> response = (Responds<ForrentHouseDetailsBean>) new HttpConnect()
						.httpUrlConnection(
								request,
								new TypeToken<Responds<ForrentHouseDetailsBean>>() {
								}.getType());
				if (response != null) {
					mForrentDetailsActivityHandler.obtainMessage(1, response)
							.sendToTarget();
				} else
					mForrentDetailsActivityHandler.obtainMessage(0)
							.sendToTarget();
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
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postions,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(ForrentDetailsActivity.this,PicActivity.class);
				if(fhdb.getImage().size()>0){
					String s[] =new String[fhdb.getImage().size()];
					for(int i=0;i<fhdb.getImage().size();i++){
						s[i]=fhdb.getImage().get(i);
					}
					intent.putExtra("urls", s);
					intent.putExtra("count", postions);
					startActivity(intent);
				}
				
			}
		});
		title.setText(fhdb.getCom() == null ? "" : fhdb.getCom());
		// zujin = (TextView) findViewById(R.id.zujin);
		// zujin.setText(fh.getPrice()==null?"":fh.getPrice()+"/月");
		// louxing = (TextView) findViewById(R.id.louxing);
		// louxing.setText(fh.getPrice()==null?"":fh.getPrice()+"/月");

		fitment_TV = (TextView) findViewById(R.id.fitment_TV);
		// fhdb.getFitment()==null?"":fhdb.getFitment()
		fitment_TV.setText(fhdb.getFitment() == null ? "" : fhdb.getFitment());

		mob_TV = (TextView) findViewById(R.id.forrent_mob);
		mob_TV.setText(fhdb.getMob() == null ? "" : fhdb.getMob());

		mian_mob_TV = (TextView) findViewById(R.id.forrent_main_mob);
		mian_mob_TV.setText(fhdb.getMob() == null ? "" : fhdb.getMob());

		toward_TV = (TextView) findViewById(R.id.floor_TV);
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

		transaction_TV = (TextView) findViewById(R.id.transaction_TV);
		transaction_TV.setText(fhdb.getFacility() == null ? "" : fhdb
				.getFacility());

		facility_TV = (TextView) findViewById(R.id.facility_TV);
		facility_TV.setText(fhdb.getBusiness() == null ? "" : fhdb
				.getBusiness());

		name_TV = (TextView) findViewById(R.id.name_TV);
		name_TV.setText(fhdb.getName() == null ? "" : fhdb.getName());

		zujin = (TextView) findViewById(R.id.zujin);
		zujin.setText(value != null ? value.getPrice() : "");

		area_TV = (TextView) findViewById(R.id.area_TV);
		area_TV.setText(fhdb.getArea() == null ? "" : fhdb.getArea());

		floor_TV = (TextView) findViewById(R.id.floor_TV);
		floor_TV.setText(fhdb.getFloor() == null ? "" : fhdb.getFloor());

		person_TV = (TextView) findViewById(R.id.forrent_person);
		person_TV.setText(fhdb.getPerson() == null ? "" : fhdb.getPerson());
		
		ImageView head =(ImageView) findViewById(R.id.gerentouxiang);
		ImageLoader loader = ImageLoader.getInstance();
		
		if(fhdb.getIconurl()!=null){
			
			loader.displayImage(HttpConnect.picUrl+fhdb.getIconurl(), head,options);
		}
		
		ImageView iv_call =(ImageView) findViewById(R.id.forrent_call);
		iv_call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
	                String number = mob_TV.getText().toString();  
	                //用intent启动拨打电话  
	                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
	                startActivity(intent);  
			}
		});
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

	public Handler collect = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			new PublicDb().save(ForrentDetailsActivity.this, value,
					Share.FORRENT.getType());
			int result = msg.what;
			Responds<Collect> responde = (Responds<Collect>) msg.obj;
			switch (result) {
			case 0:
				break;
			default:
				List<Collect> data = responde.getRESPONSE_BODY().get(
						Container.RESULT);
				if (data.get(0).getState().equals("1")) {
					showToast("收藏成功", 2000);
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
