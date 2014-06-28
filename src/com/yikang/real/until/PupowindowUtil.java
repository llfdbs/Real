package com.yikang.real.until;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import cn.Bean.util.Area;

import com.yikang.real.R;
import com.yikang.real.activity.CheckedActivity;
import com.yikang.real.adapter.AreaAdapter;
import com.yikang.real.adapter.AreaAdapterTwo;
import com.yikang.real.adapter.PopuTwoSelectAdapter;
import com.yikang.real.imp.PopWindowCallBack;

public class PupowindowUtil {

	public Context context;
	LayoutInflater inflate = null;
	int h;
	ArrayList<String> data_two = new ArrayList<String>();
	ListView lv2;
	AreaAdapter adapter;
	AreaAdapterTwo adapter2;

	public PupowindowUtil(Context context, Activity act) {
		this.context = context;
		inflate = LayoutInflater.from(context);
		DisplayMetrics metrie = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metrie);
		int height = metrie.heightPixels;
		h = (height / 5) * 3;
	}

	// 单list的popu
	public PopupWindow getListPopu(CheckedActivity act, ArrayList data,
			OnItemClickListener itemclick) {
		PopupWindow pop = new PopupWindow();
		View view = inflate.inflate(R.layout.listpopup, null);
		ListView lv = (ListView) view.findViewById(R.id.listpopup);
		SimpleAdapter adapter = new SimpleAdapter(act, data,
				R.layout.popu2list_item, new String[] { "item" },
				new int[] { R.id.popu2list_item_title });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(itemclick);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, h);
		view.setLayoutParams(params);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(h);
		pop.setFocusable(true);
		// PopupWindow pop = new PopupWindow(view,300,300,true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.update();
		return pop;
	}

	// 双list的popu
	public PopupWindow getAreaPop(final List<Area> datas,
			final PopWindowCallBack callBack) {
		PopupWindow pop = new PopupWindow();
		Area area = new Area();
		area.setArea("不限");
		datas.add(0, area);
		View view = inflate.inflate(R.layout.popu2list, null);
		ListView lv = (ListView) view.findViewById(R.id.populist_menu);
		lv2 = (ListView) view.findViewById(R.id.populist_menu2);
		adapter = new AreaAdapter(context, datas);
		adapter2 = new AreaAdapterTwo(context, data_two);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> ap, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				adapter.setCheck(position);
				if (position > 0) {
					lv2.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
					adapter2.getData().clear();
					adapter2.getData().addAll(createAreaMap(datas, position));
					adapter2.notifyDataSetChanged();
				} else {
					lv2.setVisibility(View.GONE);
					callBack.clickArea("不限");
				}
			}
		});

		lv.setAdapter(adapter);
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				// TODO Auto-generated method stub
				callBack.clickArea(data_two.get(index));
			}
		});
		lv2.setAdapter(adapter2);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, h);
		view.setLayoutParams(params);
		pop.setContentView(view);
		pop.setOutsideTouchable(true);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(h);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.update();
		return pop;
	}

	// 把数据换转换成可用内容
	private List<String> createAreaMap(List<Area> datas, int index) {
		return datas.get(index).getListarea();
	}
}
