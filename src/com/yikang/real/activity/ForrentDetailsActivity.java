package com.yikang.real.activity;


import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import cn.Bean.util.Area;
import cn.Bean.util.ForrentHouse;
import cn.Bean.util.ForrentHouseDetailsBean;
import cn.Bean.util.ForrentHouseDetailsBean;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class ForrentDetailsActivity  extends BaseActivity implements OnClickListener{

	private TextView title;
	private Gallery mGallery;
	private TextView zujin;
	private TextView louxing;
	private String nid;

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
		findView();
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
				List<ForrentHouseDetailsBean>  data =  responde.getRESPONSE_BODY().get(Container.RESULT);
			System.out.println(data.get(0));
				break;
			}
		 
		}

	};

	
	/**
	 * 获取 数据
	 */
	public void getData(){
		
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
						.httpUrlConnection(request,
								new TypeToken<Responds<ForrentHouseDetailsBean>>() {
								}.getType());
				if (response != null) {
					mForrentDetailsActivityHandler.obtainMessage(1, response).sendToTarget();
				}
				mForrentDetailsActivityHandler.obtainMessage(0).sendToTarget();
			}
		}).start();
 
	}

	private void findView() {
	
//		ImageView back =	(ImageView) findViewById(R.id.back);
//		back.setOnClickListener(this);
////		 request.getParameter("bizorderid")==null?"":request.getParameter("bizorderid");
//		title = (TextView) findViewById(R.id.title);
//		mGallery = (Gallery) findViewById(R.id.mGallery);
//		title.setText(fh.getTitle()==null?"":fh.getTitle());
//		zujin = (TextView) findViewById(R.id.zujin);
//		zujin.setText(fh.getPrice()==null?"":fh.getPrice()+"/月");
//		louxing = (TextView) findViewById(R.id.louxing);
//		louxing.setText(fh.getPrice()==null?"":fh.getPrice()+"/月");
		
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

}
