package com.yikang.real.map;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 
 * @author 袁天祥  
 * 
 * @param 
 * **/
public class MyOverlay extends ItemizedOverlay {

	PopupOverlay pop=null;
	Button button=null;
	ClickBack callBack=null;

	public MyOverlay(Drawable defaultMarker, MapView mapView,ClickBack callBack) {
		this(defaultMarker, mapView,null,null,callBack);
		this.callBack=callBack;
	}
	
	public MyOverlay(Drawable defaultMarker, MapView mapView,PopupOverlay pop,Button button,ClickBack callBack){
		super(defaultMarker, mapView);
		this.button=button;
		this.pop=pop;
	}



	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		callBack.Onclick(index);
		
		return true;
	}
	
	
}
