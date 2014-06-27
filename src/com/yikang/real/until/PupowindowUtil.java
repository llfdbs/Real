package com.yikang.real.until;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.yikang.real.R;
import com.yikang.real.activity.CheckedActivity;
import com.yikang.real.adapter.PopuTwoSelectAdapter;

public class PupowindowUtil {

	public Context context;
	LayoutInflater inflate = null;
	int h;

	public PupowindowUtil(Context context, Activity act) {
		this.context = context;
		inflate = LayoutInflater.from(context);
		DisplayMetrics metrie = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metrie);
		int height = metrie.heightPixels;
		h = (height / 5) * 3;
	}

	// 单list的popu
	public PopupWindow getListPopu(CheckedActivity act, ArrayList data, OnItemClickListener itemclick) {
		PopupWindow pop = new PopupWindow();
		View view = inflate.inflate(R.layout.listpopup, null);
		ListView lv = (ListView) view.findViewById(R.id.listpopup);
		SimpleAdapter adapter = new SimpleAdapter(act, data,
				android.R.layout.activity_list_item, new String[] { "item" },
				new int[] { android.R.id.text1 });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(itemclick);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, h);
		view.setLayoutParams(params);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(h);
//		PopupWindow pop = new PopupWindow(view,300,300,true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		 pop.update();
		return pop;
	}

	// 双list的popu
	public PopupWindow getListTwoPopu(ArrayList<String> data,
			ArrayList<HashMap<String, String>>[] datas,
			OnItemClickListener itemclick) {
		PopupWindow pop = new PopupWindow();
		ArrayList<HashMap<String, String>> list =new ArrayList<HashMap<String,String>>();
		list.addAll(datas[0]);
		View view = inflate.inflate(R.layout.popu2list, null);
		ListView lv = (ListView) view.findViewById(R.id.populist_menu);
		ListView lv2 = (ListView) view.findViewById(R.id.populist_menu2);
		final PopuTwoSelectAdapter adapter = new PopuTwoSelectAdapter(context,
				data);
		final SimpleAdapter adapter2 = new SimpleAdapter(null, list,
				android.R.layout.activity_list_item, new String[] { "item" },
				new int[] { android.R.id.text1 });

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> ap, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				String title = (String) ap.getItemAtPosition(position);
				adapter.setSelected(position);
				adapter.notifyDataSetChanged();
			}
		});

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(itemclick);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, h);
		view.setLayoutParams(params);
		pop.setContentView(view);
		return pop;
	}
}
