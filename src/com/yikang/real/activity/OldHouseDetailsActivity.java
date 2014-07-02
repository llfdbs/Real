package com.yikang.real.activity;


 
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.OldHouseDetailsBean;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.view.DetialGallery;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class OldHouseDetailsActivity  extends BaseActivity implements OnClickListener{
	private OldHouseDetailsBean fhdb;
	private ImageView back;
	private String nid;

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
				List<OldHouseDetailsBean> data = responde
						.getRESPONSE_BODY().get(Container.RESULT);
				fhdb = data.get(0);
				System.out.println(fhdb);
				findView();
				break;
			}

		}

	};
	private DetialGallery gallery;
	private DisplayImageOptions options;
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
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_house_details );
		nid = (String) getIntent().getStringExtra("nid");
		
		getData();
	 
		
	}

	private void findView() {
		
		
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showStubImage(R.drawable.ic_launcher).cacheInMemory()
		.cacheOnDisc().build();
		back = (ImageView) findViewById(R.id.back); 
		back.setOnClickListener(this);
		
		 
//		{"floor":1
//			,"fitment":"精装",
//			"type":"",
//			"years":"2006",
//			"name":null,
//			"mob":null,
//			"add":"滇池卫城紫郢",
//			"namepath":"URL",
//			"lat":"24.980297936455 ",
//			"lng":"102.68820497036 ",
//			"desc":"1110",
//			"environmental":"杨家地农贸市场 正和农贸市场.太家河农贸市场 滇池卫城社区医院、儿童医院、圣约翰医院，同仁医院 ",
//			"education":"云南大学滇池学院 滇池旅游度假区实验中学 滇池旅游度假区实验小学 扬帆贝贝 爱英森电脑培训学",
//			"entertainment":"云南民族村、 海埂公园 大名星KTV 青少年活动中心 南亚风情园 西贡码头 千禧KTV 西",
//			"facility":"滇池学院，滇池卫城，金家村同仁医院\r\n172,A9,A4,106直接到滇池卫城，24，44,73路到",
//			"business":"康乐茶叶市场 、民升超市 、大商汇、 国美电器、 沃尔玛、南亚风情园 广福厨具市场、十一家具",
//			"image":["Photos/201404/CS1404000003/d07231dc03b147b498986176282cd88e.jpg"]}]}} 
	
	TextView	floor_TV =	   (TextView) findViewById(R.id.floor_TV);
	floor_TV.setText(fhdb.getFloor() == null ? "" : fhdb.getFloor());
//  装修
	TextView	fitment_TV =	   (TextView) findViewById(R.id.fitment_TV);
	fitment_TV.setText(fhdb.getFitment() == null ? "" : fhdb.getFitment());
	
	TextView	years_TV =	   (TextView) findViewById(R.id.years_TV);
	years_TV.setText(fhdb.getYears() == null ? "" : fhdb.getYears());
	
	TextView	name_TV =	   (TextView) findViewById(R.id.name_TV);
	name_TV.setText(fhdb.getName() == null ? "" : fhdb.getName());
	
	
	TextView	add_TV =	   (TextView) findViewById(R.id.add_TV);
	add_TV.setText(fhdb.getAdd() == null ? "" : fhdb.getAdd());
	
//	"environmental":"杨家地农贸市场 正和农贸市场.太家河农贸市场 滇池卫城社区医院、儿童医院、圣约翰医院，同仁医院 ",
//	"education":"云南大学滇池学院 滇池旅游度假区实验中学 滇池旅游度假区实验小学 扬帆贝贝 爱英森电脑培训学",
//	"entertainment":"云南民族村、 海埂公园 大名星KTV 青少年活动中心 南亚风情园 西贡码头 千禧KTV 西",
//	"facility":"滇池学院，滇池卫城，金家村同仁医院\r\n172,A9,A4,106直接到滇池卫城，24，44,73路到",
//	"business":"康乐茶叶市场 、民升超市 、大商汇、 国美电器、 沃尔玛、南亚风情园 广福厨具市场、十一家具",

	
	TextView	zhoubianshebei =	   (TextView) findViewById(R.id.zhoubianshebei);
	zhoubianshebei.setText(fhdb.getEnvironmental() == null ? "" : fhdb.getFloor());
	
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
						.httpUrlConnection(
								request,
								new TypeToken<Responds<OldHouseDetailsBean>>() {
								}.getType());
				if (response != null) {
					mOldHouseDetailsActivityHandler.obtainMessage(1, response)
							.sendToTarget();
				}
				mOldHouseDetailsActivityHandler.obtainMessage(0).sendToTarget();
			}
		}).start();

	}
	@Override
	public void onClick(View v) {
	 
		switch (v.getId()) {
		case R.id.back:
			
			break;

		default:
			break;
		}
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
