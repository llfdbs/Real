package com.yikang.real.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import cn.Bean.util.SecondHandHouse;
import cn.Bean.util.SecondHandHouseDetails;
import cn.Bean.util.SecondHouseValue;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.activity.CheckedActivity;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.application.RealApplication;
import com.yikang.real.bean.House;
import com.yikang.real.control.GetNewHouseControl;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.PopStatus;
import com.yikang.real.until.PupowindowUtil;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

public class OldHouseFragment extends MainFragment implements
		OnCheckedChangeListener, OnClickListener, OnFocusChangeListener {

	public int Model = 0;
	@ViewById
	public LinearLayout house_topbar;
	public DropDownListView listview;
	public CheckBox top_bar1;
	public CheckBox top_bar2;
	public CheckBox top_bar3;
	public CheckBox[] check;

	public ArrayList<SecondHouseValue> data_newHouse;
	public NewHouseAdapter adapter;
	private String[] top_str = { "区域", "售价", "更多" };
	public int pos;
	public CheckedActivity act;
	private PopupWindow popLocation;
	private PopupWindow popPicese;
	private PopupWindow popMore;
	private LinearLayout zhu;

	
	/**参数*/
	private String area=""; //区域
	private String lng="";
	private String lat="";
	private String businesscCircle="";
	private String price="";
	private int rType=0;
	private String MJ="";
	private String age="";
	private int ztype =0;
	private int desc=0;
	
	private int requestMode=0;
	private int page =0;
	public Handler getDataResult = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int result = msg.what;
			Responds<SecondHouseValue> responde =(Responds<SecondHouseValue>) msg.obj;
			switch (result) {
			case 0:
				((BaseActivity)act).showToast("请求失败，请重试", 3000);
				break;

			default:
				if(responde.getRESPONSE_CODE_INFO().equals("成功")){
					
					List<SecondHouseValue> data= responde.getRESPONSE_BODY().get(Container.RESULT);
					if(requestMode==Container.REFRESH){
						data_newHouse.clear();
					}else if(!responde.isRESPONSE_NEXTPAGE()){
						listview.setOnBottomStyle(false);
					}
					data_newHouse.addAll(data);
					
				}else {
					((BaseActivity)act).showToast("请求失败，请重试", 3000);
				}
				break;
			}
			adapter.notifyDataSetChanged();
			listview.onDropDownComplete();
			listview.onBottomComplete();
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frorent_house, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		listview = (DropDownListView) view.findViewById(R.id.list_view);
		house_topbar = (LinearLayout) view.findViewById(R.id.house_topbar1);
		top_bar1 = (CheckBox) view.findViewById(R.id.top_bar1);
		top_bar2 = (CheckBox) view.findViewById(R.id.top_bar2);
		top_bar3 = (CheckBox) view.findViewById(R.id.top_bar3);
		zhu = (LinearLayout) view.findViewById(R.id.house_topbar);

		top_bar1.setOnCheckedChangeListener(this);
		top_bar2.setOnCheckedChangeListener(this);
		top_bar3.setOnCheckedChangeListener(this);
		top_bar1.setOnFocusChangeListener(this);
		top_bar2.setOnFocusChangeListener(this);
		top_bar3.setOnFocusChangeListener(this);

		AfterView();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		if (act == null) {
			act = (CheckedActivity) activity;
		}
		super.onAttach(activity);
	}

	public static OldHouseFragment instantiation(int position) {
		OldHouseFragment fragment = new OldHouseFragment();
		Bundle args = new Bundle();
		args.putInt("position", position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	void AfterView() {
		// TODO Auto-generated method stub
		check = new CheckBox[] { top_bar1, top_bar2, top_bar3 };
		for (int i = 0; i < top_str.length; i++) {
			check[i].setText(top_str[i]);
		}
		initData();
		listview.setAdapter(adapter);
		listview.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				// TODO Auto-generated method stub
				listview.setOnBottomStyle(true);
				requestMode =Container.REFRESH;
				getData();
			}
		});

		listview.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				requestMode =Container.GETMORE;
				page++;
				getData();
			}
		});
	}

	//
	private PopupWindow createPop(PopStatus status) {
		PopupWindow pop = null;
		PupowindowUtil util = new PupowindowUtil(act, act);
		switch (status) {
		case Location:
			pop = util.getListPopu(act,
					((RealApplication) act.getApplication())
							.getPicese(R.array.new_picese), loction);

			break;
		case Picese:

			break;
		case More:
			pop = util.getListPopu(act,
					((RealApplication) act.getApplication())
							.getPicese(R.array.new_picese), loction);
			break;
		}
		return pop;
	}

	private void validatePop() {

	}

	// 刷新数据
	private void getData() {
		Thread getdata = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub	
				Request request = new Request();
				request.setCommandcode("102");
				HashMap<String, String> body = new HashMap<String, String>();
				body.put("city", "昆明");
				body.put("desc", String.valueOf(desc));
				body.put("price", price);
				body.put("area", area);
				Log.v(Thread.currentThread().getName(), String.valueOf(page));
				body.put("p", String.valueOf(page));
				body.put("age", age);
				body.put("ztype", String.valueOf(ztype));
				body.put("rType", String.valueOf(rType));
				body.put("businesscCircle", businesscCircle);
				body.put("lat", lat);
				body.put("lng", lng);
				request.setREQUEST_BODY(body);
				Responds<SecondHouseValue> response = (Responds<SecondHouseValue>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<SecondHouseValue>>() {
								}.getType());
				if(response!=null){
					getDataResult.obtainMessage(1, response).sendToTarget();
				}
				getDataResult.obtainMessage(0).sendToTarget();
			}
		});
		getdata.start();
	}

	private void initData() {
		data_newHouse = new ArrayList<SecondHouseValue>();
//		for (int i = 0; i < 6; i++) {
//			House house = new House();
//			house.setId(String.valueOf(i));
//			house.setAddress("郑州市金水去黄河路" + i);
//			house.setMuch(String.valueOf(10 + i) + "万");
//			house.setName("现房好卖" + i);
//			house.setSize(String.valueOf(30 + i) + "平米");
//			data_newHouse.add(house);
//		}
		adapter = new NewHouseAdapter(act, data_newHouse);
	}

	OnItemClickListener loction = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
		}
	};

	OnItemClickListener picese = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {

		case R.id.top_bar1:
			if (!isChecked) {
				break;
			}
			popLocation = createPop(PopStatus.Location);
			popLocation.showAsDropDown(zhu);
			break;

		case R.id.top_bar2:
			popLocation = createPop(PopStatus.Location);
			if (!isChecked) {
				break;
			}
			break;

		case R.id.top_bar3:
			if (!isChecked) {
				break;
			}
			popLocation = createPop(PopStatus.More);
			popLocation.showAtLocation(zhu, Gravity.BOTTOM, 0, 0);
			break;
		}

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.top_bar1:
		
			break;

		default:
			break;
		}

	}

	@Override
	public void onFocusChange(View view, boolean arg1) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.top_bar1:
			CheckBox box1 = (CheckBox) view;
			box1.setChecked(false);
			break;
		case R.id.top_bar2:
			CheckBox box2 = (CheckBox) view;
			box2.setChecked(false);
			break;
		case R.id.top_bar3:
			CheckBox box3 = (CheckBox) view;
			box3.setChecked(false);
			break;
		}
	}

}
