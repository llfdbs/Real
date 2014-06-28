package com.yikang.real.activity;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yikang.real.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CityList extends Activity {	
	ListView listView;	
	private List<Integer> positionList = new ArrayList<Integer>();
	private List<String> indexList = new ArrayList<String>();
	MyAdapter mAd;	
	public List<String> listTag = new ArrayList<String>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView) findViewById(R.id.listView);

		
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("List " + arg2 );
			}
		});

		init();
    }
    
    private void init(){
    	mAd = new MyAdapter(this,android.R.layout.simple_expandable_list_item_1,getListViewData());
    	listView.setAdapter(mAd);
    }
    
	private List<String> getListViewData() {
		List<String> data = new ArrayList<String>();
		int ii = 0;
		
		data.add("aatest" + (ii++));
		data.add("aatest" + (ii++));
		data.add("aatest" + (ii++));
		data.add("bbtest" + (ii++));
		data.add("bbtest" + (ii++));
		data.add("bbtest" + (ii++));		
		data.add("bbtest" + (ii++));

		data.add("ccccc" + (ii++));
		data.add("cccccc" + (ii++));

		data.add("dddddddd" + (ii++));
		data.add("dddddd" + (ii++));
		data.add("dddddddd" + (ii++));

		data.add("eeeeee" + (ii++));
		data.add("eeeee" + (ii++));
		data.add("eeeeeee" + (ii++));

		data.add("ffffffff" + (ii++));
		data.add("ffffffff" + (ii++));


		data.add("ggg" + (ii++));
		data.add("ggggggggg" + (ii++));		

		
		
		ArrayList<String> indexdata = new ArrayList<String>();
		String _firstchar = null ;
		int dataNum = data.size();
		for (int i = 0; i < dataNum; i++) {
			String startStr = String.valueOf(data.get(i).charAt(0));
			
			if (!startStr.equalsIgnoreCase(_firstchar))
			{
				_firstchar = startStr;
				data.add(_firstchar.toUpperCase());
			}
		}
		dataNum = data.size();
		for (int i = 0; i < dataNum; i++) {
			
			String str = data.get(i);
			indexdata.add(str);	
			data.set(i, data.get(i));
		}
		
		Collections.sort(data,String.CASE_INSENSITIVE_ORDER);
		Collections.sort(indexdata,String.CASE_INSENSITIVE_ORDER);
		int num = indexdata.size();
		boolean isAdd = false;

		char startChar = indexdata.get(0).charAt(0);
		_firstchar=null;
		for (int i = 0; i < num; i++) 
		{
			int x = (int) startChar;
//			Log.i(TAG, "x= " + x);
			if ((x > 64 && x < 91) || (x > 96 && x < 123)) 
			{
				String startStr = String.valueOf(indexdata.get(i).charAt(0));
				Log.i("str:=",startStr);
				if (!startStr.equalsIgnoreCase(_firstchar))
				{
					_firstchar = startStr;
					Log.i("indexList ADD",startStr);
					positionList.add(i);
					startChar = indexdata.get(i).charAt(0);
					indexList.add(_firstchar.toUpperCase());
					listTag.add(_firstchar.toUpperCase());
				}
			}else{
				if(!isAdd){
					indexList.add("#");
					positionList.add(i);
					isAdd = true;
				}
			}
		}
		return data;
	}

	
	class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context,  int textViewResourceId,
				List<String> objects) {
			super(context,  textViewResourceId, objects);
		
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false; 
		}

		@Override
		public boolean isEnabled(int position) {
			return !listTag.contains(getItem(position)); 

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View view = convertView;

		    if(listTag.contains(getItem(position))){
		        view = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item_tag, null);
		    }else{                 
		        view = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item, null);
		    }

		    TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);
		    textView.setText(getItem(position));

		    return view;
		}
		
	}
}