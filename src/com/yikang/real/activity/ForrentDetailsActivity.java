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
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import cn.Bean.util.ForrentHouseDetailsBean;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class ForrentDetailsActivity extends BaseActivity implements
		OnClickListener {

	private TextView title;
	private com.yikang.real.view.DetialGallery gallery;
	private TextView zujin;
	private TextView louxing;
	private String nid;
	private ForrentHouseDetailsBean fhdb;

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
		setContentView(R.layout.forrent_details);

		nid = (String) getIntent().getStringExtra("nid");

		getData();
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
	private DisplayImageOptions options;

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
				}
				mForrentDetailsActivityHandler.obtainMessage(0).sendToTarget();
			}
		}).start();

	}

	private void findView() {

		ImageView back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		// request.getParameter("bizorderid")==null?"":request.getParameter("bizorderid");
		title = (TextView) findViewById(R.id.title);

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showStubImage(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().build();
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
