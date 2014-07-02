package com.yikang.real.until;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.Bean.util.SecondHouseValue;

import com.baidu.mapapi.utils.h;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class MapHousePop extends PopupWindow {

	Activity context;
	String title;
	String xid;
	String commandcode;
	LayoutInflater inflate;
	ArrayList<SecondHouseValue> data_newHouse = new ArrayList<SecondHouseValue>();
	NewHouseAdapter adapter;
	View view;
	ListView list;
	ProgressBar bar;

	public MapHousePop(Activity context, String title, int xid,
			String commandcode) {
		super(context);
		inflate = LayoutInflater.from(context);
		this.context = context;
		this.title = title;
		this.xid = String.valueOf(xid);
		this.commandcode = commandcode;
		init();
	}

	Handler getHouse = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			Responds<SecondHouseValue> responde = (Responds<SecondHouseValue>) msg.obj;
			switch (result) {
			case 0:
				Toast.makeText(context, "请求失败，请重试", 3000).show();
				break;

			default:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {

					List<SecondHouseValue> data = responde.getRESPONSE_BODY()
							.get(Container.RESULT);

					data_newHouse.addAll(data);

				} else {
					Toast.makeText(context, "请求失败，请重试", 3000).show();
				}
				break;
			}
			list.setVisibility(View.VISIBLE);
			bar.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();
		}

	};

	public void init() {
		view = inflate.inflate(R.layout.maphouse, null);
		TextView text = (TextView) view.findViewById(R.id.house_title);
		text.setText(title);
		list = (ListView) view.findViewById(R.id.map_house);
		bar = (ProgressBar) view.findViewById(R.id.map_house_bar);
		data_newHouse.clear();
		SecondHouseValue hous=new SecondHouseValue();

		adapter = new NewHouseAdapter(context, data_newHouse);
		list.setAdapter(adapter);
		getData();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 100);
		view.setLayoutParams(params);
		setOutsideTouchable(true);
		setAnimationStyle(R.style.house_anim_style);
		setContentView(view);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(createHight());
		setFocusable(true);
		// PopupWindow pop = new PopupWindow(view,300,300,true);
		setBackgroundDrawable(new BitmapDrawable());
//		update();

	}
	
	private void getData(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				request.setCommandcode(commandcode);
				HashMap<String, String> body = new HashMap<String, String>();
				body.put("xid", xid);
				request.setREQUEST_BODY(body);
				Responds<SecondHouseValue> response = (Responds<SecondHouseValue>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<SecondHouseValue>>() {
								}.getType());
				if (response != null) {
					getHouse.obtainMessage(1, response).sendToTarget();
				} else
					getHouse.obtainMessage(0).sendToTarget();
			}
		}).start();
	}
	

	private int createHight() {
		DisplayMetrics metrie = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrie);
		int height = metrie.heightPixels;
		int h = (height / 5) * 3;

		return h;
	}
}
