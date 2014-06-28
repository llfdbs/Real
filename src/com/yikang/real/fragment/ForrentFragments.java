package com.yikang.real.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import cn.Bean.util.ErFang;
import cn.Bean.util.RentHouse;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.yikang.real.R;
import com.yikang.real.activity.CheckedActivity;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.application.RealApplication;
import com.yikang.real.bean.House;
import com.yikang.real.until.Container.PopStatus;
import com.yikang.real.until.PupowindowUtil;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;
/**
 * 租房
 * @author Administrator
 *
 */
public class ForrentFragments extends MainFragment implements
		OnCheckedChangeListener, OnClickListener {

	public int Model = 0;
	@ViewById
	public LinearLayout house_topbar;
	public cn.trinea.android.common.view.DropDownListView listview;
	public CheckBox top_bar1;
	public CheckBox top_bar2;
	public CheckBox top_bar3;
	public CheckBox[] check;

	public ArrayList<House> data_newHouse;
	public NewHouseAdapter adapter;
	private String[] top_str = { "区域", "价格", "更多" };
	public int pos;
	public CheckedActivity act;
	private PopupWindow popLocation;
	private PopupWindow popPicese;
	private PopupWindow popMore;
	private LinearLayout zhu;

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
	     
 
		listview = (DropDownListView) view
				.findViewById(R.id.list_view);
		house_topbar = (LinearLayout) view.findViewById(R.id.house_topbar1);
		top_bar1 = (CheckBox) view.findViewById(R.id.top_bar1);
		top_bar2 = (CheckBox) view.findViewById(R.id.top_bar2);
		top_bar3 = (CheckBox) view.findViewById(R.id.top_bar3);
		zhu = (LinearLayout) view.findViewById(R.id.zhu);
		
		top_bar1.setOnCheckedChangeListener(this);
		top_bar2.setOnCheckedChangeListener(this);
		top_bar3.setOnCheckedChangeListener(this);
		
		
		
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

	public static ForrentFragments instantiation(int position) {
		ForrentFragments fragment = new ForrentFragments();
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
	/**
	 * 通过setOnDropDownListener设置下拉的事件，不过需要在事件结束时手动调用onDropDownComplete恢复状态 
	 * 	通过setOnBottomListener设置滚动到底部的事件，不过需要在事件结束时手动调用onBottomComplete恢复状态，示例代码如下： 
	 * 
	 */
	void AfterView() {
		// TODO Auto-generated method stub
		check = new CheckBox[] { top_bar1, top_bar2, top_bar3 };
		for (int i = 0; i < top_str.length; i++) {
			check[i].setText(top_str[i]);
		}
		initData();
		listview.setAdapter(adapter);
		// set drop down listener  
		listview.setOnDropDownListener(new OnDropDownListener() {  
			   
	            @Override  
	            public void onDropDown() {  
//	                new GetDataTask(true).execute();
//	            	listview.onDropDownComplete();
	            	listview.onDropDownComplete("完成");
	            }  
	        });
		
		
		 // set on bottom listener  
		listview.setOnBottomListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
//                new GetDataTask(false).execute();  
            	System.out.println("-------------");
            	listview.onBottomComplete();
            }  
        });  
//		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				getData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				getMore();
//			}
//
//		});
	}

	//
	private PopupWindow createPop(PopStatus status) {
		PopupWindow pop = null;
		PupowindowUtil util = new PupowindowUtil(act, act);
		switch (status) {
		case Location:
			pop = util.getListPopu(act,((RealApplication) act.getApplication())
					.getPicese(R.array.new_picese), loction);
			
			break;
		case Picese:

			break;
		case More:
			pop = util.getListPopu(act,((RealApplication) act.getApplication())
					.getPicese(R.array.new_picese), loction);
			break;
		}
		return pop;
	}

	private void validatePop() {

	}

//	// 刷新数据
//	public void getData() {
//		GetNewHouseControl control = new GetNewHouseControl();
//		refreshComplete();
//	}
//
//	 
//	public void getMore() {
//		refreshComplete();
//	}

 
//	public void refreshComplete() {
//		listview.onRefreshComplete();
//		 
//		 
//	}

	private void initData() {
		data_newHouse = new ArrayList<House>();
		
//		ttp://210.75.3.26:8855/houseapp/apprq?HEAD_INFO=
//			{"commandcode":108,"REQUEST_BODY":{"city":"昆明","desc":"0" 
//		,"p":1,"lat":24.973079315636,"lng":102.69840055824}}
		
		final HttpConnect conn = new HttpConnect();
		final Request reques = new Request();
		// REQUEST_BODY":{"username":"12" , "password":"xxx"}}
		reques.setCommandcode("108");
		HashMap map = new HashMap<String, Object>();
		map.put("city","昆明");
		map.put("desc","0");
		map.put("p",1);
		map.put("lat",24.973079315636);
		map.put("lng",102.69840055824);
		reques.setREQUEST_BODY(map);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Responds<ErFang> responds = (Responds<ErFang>) conn
						.httpUrlConnection(reques,
								new Responds<ErFang>());
			
			 System.out.println("------------------------------");
			 
			 
			 HashMap<String, List<ErFang>> response_BODY = responds.getRESPONSE_BODY();
			 List<ErFang>  rents= response_BODY.get("list");
//			 for(ErFang r:rents){
//				 
//				 System.out.println(r);
//			 }
		 
		 
//			 for(  HashMap<String, List<RentHouse>>  s:   response_BODY ){
//				 
//				 
//			 }
			}
		}).start();
		
		
		
		
		for (int i = 0; i < 6; i++) {
			House house = new House();
			house.setId(String.valueOf(i));
			house.setAddress("郑州市金水去黄河路" + i);
			house.setMuch(String.valueOf(10 + i) + "万");
			house.setName("现房好卖" + i);
			house.setSize(String.valueOf(30 + i) + "平米");
			data_newHouse.add(house);
		}
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
			if (isChecked) {
				break;
			}
			popLocation = createPop(PopStatus.Location);
			popLocation.showAtLocation(zhu, Gravity.CENTER, 0, 0);
			break;

		case R.id.top_bar2:
			popLocation = createPop(PopStatus.Location);
			if (isChecked) {
				break;
			}
			break;

		case R.id.top_bar3:
			if (isChecked) {
				break;
			}
			popLocation = createPop(PopStatus.More);
			popLocation.showAtLocation(zhu, Gravity.CENTER, 0, 0);
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

}
