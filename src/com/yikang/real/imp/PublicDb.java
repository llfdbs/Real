package com.yikang.real.imp;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import cn.Bean.util.SecondHouseValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Share;

public class PublicDb {

	public  void save(Context context,SecondHouseValue value,String db_key){
		Gson g =new Gson();
		if(Container.USER==null){
			return ;
		}
		SharedPreferences share =context.getSharedPreferences(Container.USER.getUsername(), 0);
		String sha= share.getString(db_key, null);
		ArrayList<SecondHouseValue> list;
		if(sha==null){
			list=new ArrayList<SecondHouseValue>();
		}else {
			Type type =new TypeToken<ArrayList<SecondHouseValue>>(){}.getType();
			list= g.fromJson(sha, type);
		}
		list.add(value);
		sha =g.toJson(list);
		Editor editor =share.edit();
		editor.putString(db_key, sha);
		editor.commit();
	}
	
}
