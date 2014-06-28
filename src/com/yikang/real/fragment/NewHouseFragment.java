package com.yikang.real.fragment;

import java.util.ArrayList;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.UiThread;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import cn.Bean.util.SecondHouseValue;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;


//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yikang.real.R;
import com.yikang.real.activity.CheckedActivity;
import com.yikang.real.adapter.NewHouseAdapter;
import com.yikang.real.application.RealApplication;
import com.yikang.real.bean.House;
import com.yikang.real.control.GetNewHouseControl;
import com.yikang.real.until.Container.PopStatus;
import com.yikang.real.until.PupowindowUtil;

public class NewHouseFragment extends MainFragment implements
		OnCheckedChangeListener, OnClickListener {

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

		listview =  (DropDownListView) view
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

	public static NewHouseFragment instantiation(int position) {
		NewHouseFragment fragment = new NewHouseFragment();
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
				listview.onDropDownComplete();
			}
		});
		
		listview.setOnBottomListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listview.onBottomComplete();
			}
		});
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

	// 刷新数据
	public void getData() {
		GetNewHouseControl control = new GetNewHouseControl();
		refreshComplete();
	}

	 
	public void getMore() {
		refreshComplete();
	}

 
	public void refreshComplete() {
		 
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
			getData();
			break;

		default:
			break;
		}

	}

}
