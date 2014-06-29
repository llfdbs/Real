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
import cn.Bean.util.More;
import cn.Bean.util.MoreValue;

import com.yikang.real.R;
import com.yikang.real.activity.CheckedActivity;
import com.yikang.real.adapter.AreaAdapter;
import com.yikang.real.adapter.AreaAdapterTwo;
import com.yikang.real.adapter.MoreAdapter;
import com.yikang.real.adapter.MoreAdapterTwo;
import com.yikang.real.imp.PopWindowCallBack;

public class PupowindowUtil {

	public Context context;
	LayoutInflater inflate = null;
	int h;
	ArrayList<String> data_two = new ArrayList<String>();
	ArrayList<MoreValue> more_data = new ArrayList<MoreValue>();
	ListView lv2;
	AreaAdapter adapter;
	AreaAdapterTwo adapter2;
	MoreAdapter more;
	MoreAdapterTwo more_two;
	HashMap<Integer, Integer> more_check = new HashMap<Integer, Integer>();

	public PupowindowUtil(Context context, Activity act) {
		this.context = context;
		inflate = LayoutInflater.from(context);
		DisplayMetrics metrie = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metrie);
		int height = metrie.heightPixels;
		h = (height / 5) * 3;
	}

	// 单list的popu
	public PopupWindow getListPopu(CheckedActivity act, final ArrayList data,
			final PopWindowCallBack callBack) {
		PopupWindow pop = new PopupWindow();
		View view = inflate.inflate(R.layout.listpopup, null);
		ListView lv = (ListView) view.findViewById(R.id.listpopup);
		SimpleAdapter adapter = new SimpleAdapter(act, data,
				R.layout.popu2list_item, new String[] { "item" },
				new int[] { R.id.popu2list_item_title });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String price =((String) data.get(arg2)).replace("万", "");
				callBack.clickPrice(price);
			}
		});
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 100);
		view.setLayoutParams(params);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.MATCH_PARENT);
		pop.setFocusable(true);
		// PopupWindow pop = new PopupWindow(view,300,300,true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.update();
		return pop;
	}

	// 双list的popu area
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
				adapter2.setCheck(index);
				callBack.clickArea(data_two.get(index));
			}
		});
		lv2.setAdapter(adapter2);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 100);
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

	// 双list的popu area
	public PopupWindow getMorePop(final List<More> datas,
			final PopWindowCallBack callBack) {
		PopupWindow pop = new PopupWindow();

		more_data.addAll(createMoreMap(datas, 0));
		View view = inflate.inflate(R.layout.popu2list, null);
		ListView lv = (ListView) view.findViewById(R.id.populist_menu);
		lv2 = (ListView) view.findViewById(R.id.populist_menu2);
		lv2.setVisibility(View.VISIBLE);
		more = new MoreAdapter(context, datas);
		more_two = new MoreAdapterTwo(context, more_data);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> ap, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				more.getCheck().add(position);
				more.setPostion(position);
				more_two.setCheck(more_check.get(position)==null?0:more_check.get(position));
				more.notifyDataSetChanged();
				more_two.getData().clear();
				more_two.getData().addAll(createMoreMap(datas, position));
				more_two.notifyDataSetChanged();
			}
		});

		lv.setAdapter(more);
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				// TODO Auto-generated method stub
				more_two.setCheck(index);
				more_check.put(more.getPostion(), index);
				more.getCheck().remove(more.getPostion());
				String name = datas.get(more.getPostion()).getDetail()
						.get(index).getName();
				String value = datas.get(more.getPostion()).getDetail()
						.get(index).getValue();
				String key = datas.get(more.getPostion()).getKey();
				callBack.clickMore(name, key, value);
			}
		});
		lv2.setAdapter(more_two);

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

	// 把数据换转换成可用内容
	private List<MoreValue> createMoreMap(List<More> datas, int index) {
		return datas.get(index).getDetail();
	}
}
