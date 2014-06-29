package com.yikang.real.map;

import android.graphics.drawable.Drawable;
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
	

	public MyOverlay(Drawable defaultMarker, MapView mapView) {
		this(defaultMarker, mapView,null,null);
	}
	
	public MyOverlay(Drawable defaultMarker, MapView mapView,PopupOverlay pp,Button button){
		super(defaultMarker, mapView);
		this.button=button;
	}
}
