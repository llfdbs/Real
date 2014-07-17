package com.yikang.real.map;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.Bean.util.SecondHouseMapValue;
import cn.Bean.util.SecondHouseValue;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.google.gson.reflect.TypeToken;
import com.yikang.real.R;
import com.yikang.real.activity.ForrentDetailsActivity;
import com.yikang.real.activity.OldHouseDetailsActivity;
import com.yikang.real.activity.SearchActivity;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.application.RealApplication;
import com.yikang.real.until.Container;
import com.yikang.real.until.Container.Page;
import com.yikang.real.until.Container.Share;
import com.yikang.real.until.MapHousePop;
import com.yikang.real.web.HttpConnect;
import com.yikang.real.web.Request;
import com.yikang.real.web.Responds;

/**
 * @author 袁天祥 功能1:地图移动缩放时显示边界坐标范围 功能2:地图缩放到一定级别的时候 地图上四个点变成一个点
 *         功能3:加载地图实现首次定位，通过按钮实现手动定位
 */
public class OverlayDemo extends BaseActivity implements ClickBack,OnItemClickListener {

	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView = null;
	/**
	 * 用MapController完成地图控制
	 */
	private MapController mMapController = null;
	private MyOverlay mOverlay = null;
	private PopupOverlay pop = null;
	// private View viewCache = null;
	private ImageView requestLocButton = null;
	ArrayList<OverlayItem> list = new ArrayList<OverlayItem>();
	float level = 0;

	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	// 定位图层
	locationOverlay myLocationOverlay = null;
	MKMapViewListener mMapListener = null;
	OnTouchListener listener = null;
	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位
	boolean isLocationClientStop = false;

	private List<Map<String, Float>> srcoue = new ArrayList<Map<String, Float>>();
	public List<SecondHouseMapValue> temp_data=null;
	
	Handler result = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Responds<SecondHouseMapValue> responde = (Responds<SecondHouseMapValue>) msg.obj;
			int result = msg.what;
			switch (result) {
			case 0:
				showToast("请求失败，请重试", 3000);
				break;

			case 1:
				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {

					temp_data = responde
							.getRESPONSE_BODY().get(Container.RESULT);
					System.out.println(temp_data);
					ListConversionPotions(temp_data);

				} else {
					showToast("请求失败，请重试", 3000);
				}
				break;
			}
		}

	};
	private ActionBar actionbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		RealApplication app = (RealApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(RealApplication.strKey,
					new RealApplication.MyGeneralListener());
		}
		/**
		 * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
		 */
		setContentView(R.layout.activity_overlay);
//		EditText edit =(EditText) findViewById(R.id.map_search);
//		edit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Bundle bundle =new Bundle();
//				bundle.putString("from", "Map");
//				openActivityForResult(SearchActivity.class,bundle,500);
//			}
//		});
		mMapView = (MapView) findViewById(R.id.bmapView);
		requestLocButton = (ImageView) findViewById(R.id.button1);
		OnClickListener btnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 手动定位请求
				requestLocClick();
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);
		/**
		 * 获取地图控制器
		 */
		mMapController = mMapView.getController();
		/**
		 * 设置地图是否响应点击事件 .
		 */
		mMapController.enableClick(true);
		/**
		 * 设置地图缩放级别
		 */
		mMapController.setZoom(9);
		/**
		 * 显示内置缩放控件
		 */
		mMapView.setBuiltInZoomControls(true);
		// init();
		initOverlay();
		move();
		// mMapController.animateTo(new GeoPoint(
		// (int) (24.872058314636 * 1e6),
		// (int) (102.59540044824 * 1e6)));
		/**
		 * 设定地图中心点
		 */
		// GeoPoint p = new GeoPoint((int)(mLat5 * 1E6), (int)(mLon5* 1E6));
		// mMapController.setCenter(p);
		// 定位初始化
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// 修改定位数据后刷新图层生效
		mMapView.refresh();
		level=9;
		
		initActionBar();
	}

	// 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			// 处理点击事件,弹出泡泡

			return true;
		}

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || isLocationClientStop)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			if (isRequest || isFirstLoc) {
				// 移动地图到定位点
				if(Container.getCity()!=null){
					mMapController.animateTo(new GeoPoint(
							(int) (Container.getCity().getLat() * 1e6),
							(int) (Container.getCity().getLng() * 1e6)));
				}else {
				mMapController.animateTo(new GeoPoint(
						(int) (24.872058314636 * 1e6),
						(int) (102.59540044824 * 1e6)));
				}
				isRequest = false;
				getData("9");
			}
			// 首次定位完成
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	/**
	 * 设置地图事件监听
	 */
	public void move() {
		mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * 在此处理地图移动完成回调 缩放，平移等操作完成后，此回调被触发
				 */
				level = mMapView.getZoomLevel();
				if (list != null) {
					if(pop2!=null&&pop2.isShowing()){
						return ;
					}
					getData(String.valueOf(level));
//					updateMapState();
				}
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
				 * mMapController.enableClick(true); 时，此回调才能被触发
				 * 
				 */

			}

			@Override
			public void onGetCurrentMap(Bitmap b) {

			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 * 地图完成带动画的操作（如: animationTo()）后，或是放大缩小按钮此回调被触发
				 */
				level = mMapView.getZoomLevel();
				if (list != null) {
//					updateMapState();
					if(pop2!=null&&pop2.isShowing()){
						return ;
					}
					getData(String.valueOf(level));
				}
			}
		};
		mMapView.regMapViewListener(RealApplication.getInstance().mBMapManager,
				mMapListener);
	}

	/**
	 * 移动的回调
	 * 
	 * @param level
	 *            地图缩放级别
	 */
	public void reloadOverlay(float level) {

		// 清除自定义点
		if(mMapView!=null||mOverlay!=null||null!=mMapView.getOverlays()){
			mMapView.getOverlays().remove(mOverlay);
			mOverlay.removeAll();
		}

		for (OverlayItem item : list) {

			mOverlay.addItem(item);
		}
		mMapView.getOverlays().add(mOverlay);
		mMapView.refresh();
	}

	public void initOverlay() {
		/**
		 * 创建自定义overlay
		 */
		mOverlay = new MyOverlay(getResources().getDrawable(
				R.drawable.icon_marka), mMapView, this);
		/**
		 * 将item 添加到overlay中 注意： 同一个itme只能add一次
		 */
		if (list != null)
			for (OverlayItem item : list) {

				mOverlay.addItem(item);
			}

		/**
		 * 保存所有item，以便overlay在reset后重新添加
		 */
		// mItems = new ArrayList<OverlayItem>();
		// mItems.addAll(mOverlay.getAllItem());
		/**
		 * 将overlay 添加至MapView中
		 */
		mMapView.getOverlays().add(mOverlay);
		/**
		 * 刷新地图
		 */
		mMapView.refresh();

		/**
		 * 向地图添加自定义View.点击定位点显示“我的位置”
		 */
		// viewCache = getLayoutInflater().inflate(R.layout.custom_text_view,
		// null);
		// popupText =(TextView) viewCache.findViewById(R.id.textcache);
		// button = new Button(this);
		// button.setBackgroundResource(R.drawable.popup);

		/**
		 * 坐标点 点击监听
		 */
		PopupClickListener popListener = new PopupClickListener() {

			@Override
			public void onClickedPopup(int arg0) {
				// TODO Auto-generated method stub

			}
		};
		pop = new PopupOverlay(mMapView, popListener);

	}

	/**
	 * 获取屏幕边界坐标范围
	 */
	private HashMap<String, String> updateMapState() {
		// 获取屏幕右上左下角坐标的另一种方法
//		 GeoPoint centerPoint = mMapView.getMapCenter();// 地图中心坐标点
//		 int tpSpan = mMapView.getLatitudeSpan();// 当前纬线的跨度（从地图的上边缘到下边缘）
//		 int lrSpan = mMapView.getLongitudeSpan();// 当前经度的跨度（从地图的左边缘到右边缘）
//		 GeoPoint point = new GeoPoint(centerPoint.getLatitudeE6()- tpSpan /
//		 2, centerPoint.getLongitudeE6() + lrSpan / 2);// 右上角
//		 GeoPoint point2 = new GeoPoint(centerPoint.getLatitudeE6()+ tpSpan /
//		 2, centerPoint.getLongitudeE6() - lrSpan / 2);// 左下角
		// 隐藏“我的位置”的显示信息
		//		pop.hidePop();
		// 获得屏幕右上角和左下角的经纬度
		GeoPoint pointLeft = mMapView.getProjection().fromPixels(0,
				mMapView.getHeight());

		GeoPoint pointRight = mMapView.getProjection().fromPixels(
				mMapView.getWidth(), 0);
		HashMap<String, String> options = new HashMap<String, String>();
		if(pointLeft!=null){
			StringBuffer minlat =new StringBuffer(String.valueOf(pointLeft.getLatitudeE6()));
			minlat.insert(2,".");
			StringBuffer maxlat =new StringBuffer(String.valueOf(pointRight.getLatitudeE6()));
			maxlat.insert(2,".");
			StringBuffer minlng =new StringBuffer(String.valueOf(pointLeft.getLongitudeE6()));
			minlng.insert(3,".");
			StringBuffer maxlng =new StringBuffer(String.valueOf(pointRight.getLongitudeE6()));
			maxlng.insert(3,".");
			options.put("minlat", minlat.toString());
			options.put("maxlat", maxlat.toString());
			options.put("minlng", minlng.toString());
			options.put("maxlng", maxlng.toString());
			Log.v(Thread.currentThread().getName(),
					 "纬度范围:" + minlat + "~"
					 + maxlat + "\n" + "经度范围:"
					 + minlng + "~"
					 + maxlng)
					 ;
		}else {
			options.put("minlat", "24.872058314636");
			options.put("maxlat", "24.973079315736");
			options.put("minlng", "102.59540044824");
			options.put("maxlng", "102.69840055924");
		}
	
	
		return options;
	}

	public void ListConversionPotions(List<SecondHouseMapValue> source) {
		list.clear();
		LayoutInflater inflate = LayoutInflater.from(this);
		for (SecondHouseMapValue value : source) {
			GeoPoint p = new GeoPoint((int) (value.getLat() * 1E6),
					(int) (value.getLng() * 1E6));
			OverlayItem item = new OverlayItem(p, "", "");
			View view = inflate.inflate(R.layout.itme, null);
			TextView text = (TextView) view.findViewById(R.id.text1);
			text.setText(String.valueOf(value.getCount()==0?"":value.getCount()));
			ImageView image= (ImageView) view.findViewById(R.id.imageView1);
			if (level > 14.0 || level == 14) { // 返回小区+数量
				image.setImageResource(R.drawable.icon_gcoding);
			}else{
				image.setImageResource(R.drawable.icon_ping_city);
			}

			Bitmap bit = BMapUtil.getBitmapFromView(view);
			Drawable drawable = (Drawable) new BitmapDrawable(bit);
			item.setMarker(drawable);
			list.add(item);
		}
		reloadOverlay(level);
	}

	/**
	 * 手动触发一次定位请求
	 */
	public void requestLocClick() {
		isRequest = true;
		mLocClient.requestLocation();
		Toast.makeText(OverlayDemo.this, "正在定位…", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	// public void clearOverlay(View view){
	// mOverlay.removeAll();
	// if (pop != null){
	// pop.hidePop();
	// }
	// mMapView.removeView(button);
	// mMapView.refresh();
	// }
	/**
	 * 重新添加Overlay
	 * 
	 * @param view
	 */
	// public void resetOverlay(View view){
	// clearOverlay(null);
	// //重新add overlay
	// mOverlay.addItem(mItems);
	// mMapView.refresh();
	// }

	@Override
	protected void onPause() {
		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		/**
		 * MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		/**
		 * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	private void getData(final String level) {
		showToast("请求网络中....", 3000);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Request request = new Request();
				if (Container.getCurrentPage() == Page.FORREN) {
					request.setCommandcode("122");
				} else if (Container.getCurrentPage() == Page.OLD) {
					request.setCommandcode("118");
				} else {
					request.setCommandcode("118");
				}
				
				HashMap<String, String> map = updateMapState();
				map.put("city", "昆明");
//				map.put("minlat", "24.872058314636");
//				map.put("maxlat", "24.973079315736");
//				map.put("minlng", "102.59540044824");
//				map.put("maxlng", "102.69840055924");
				map.put("zoomLevel", level);
				request.setREQUEST_BODY(map);
				Responds<SecondHouseMapValue> responds = (Responds<SecondHouseMapValue>) new HttpConnect()
						.httpUrlConnection(request,
								new TypeToken<Responds<SecondHouseMapValue>>() {
								}.getType());

				if (responds != null) {
					result.obtainMessage(1, responds).sendToTarget();
				}else 
					result.obtainMessage(0).sendToTarget();
			}
		});
		thread.start();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub
		
		actionbar = getSupportActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setIcon(R.drawable.back);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		if(Container.getCurrentPage()==Page.FORREN){
			actionbar.setTitle("租房地图展示");
		}else if(Container.getCurrentPage()==Page.OLD){
			actionbar.setTitle("二手房地图展示");
		}else {
			actionbar.setTitle("地图展示");
		}
			

	}
	MapHousePop pop2 =null;
	@Override
	public void Onclick(int index) {
		// TODO Auto-generated method stub
		if(null!=temp_data&&(level>14||level==14)){
			SecondHouseMapValue value=temp_data.get(index);
			String commandcode="";
			if(Container.getCurrentPage()==Page.FORREN){
				commandcode ="123";
			}else if(Container.getCurrentPage()==Page.OLD){
				commandcode= "119";
			}
			pop2 =new MapHousePop(this,  value.getMid(), commandcode);

			pop2.init();
			pop2.setItemOnclick(this);
			DisplayMetrics metrie = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrie);
			int height = metrie.heightPixels;
			pop2.showAtLocation(findViewById(R.id.map_bottomline), Gravity.TOP, 0, height);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		SecondHouseValue value=pop2.data_newHouse.get(index);
		Bundle bundle =new Bundle();
		if(Container.getCurrentPage()==Page.FORREN){
			bundle.putSerializable(Share.FORRENT.getType(), value);
			openActivity(ForrentDetailsActivity.class	, bundle);
		}else if(Container.getCurrentPage()==Page.OLD){
			bundle.putSerializable(Share.OLD.getType(), value);
			openActivity(OldHouseDetailsActivity.class	, bundle);
		}
		
	}

	@Override
	protected void onActivityResult(int request, int response, Intent intent) {
		// TODO Auto-generated method stub
		switch (response) {
		case 200:
			String xid =intent.getStringExtra("xid");
			String title =intent.getStringExtra("title");
			String lat =intent.getStringExtra("lat");
			String lng =intent.getStringExtra("lng");
			if(xid!=null){
//				getLocal(xid);
				mMapController.setZoom(15);
				mMapController.animateTo(new GeoPoint(
						(int) (Float.valueOf(lat) * 1e6),
						(int) (Float.valueOf(lng) * 1e6)));
				List<SecondHouseMapValue> list =new ArrayList<SecondHouseMapValue>();
				SecondHouseMapValue map =new SecondHouseMapValue();
				map.setCount(0);
				map.setLat(Float.valueOf(lat));
				map.setLng(Float.valueOf(lng));
				map.setMid(xid);
				map.setTitle(title);
				list.add(map);
				ListConversionPotions(list);
				
				String commandcode="";
				if(Container.getCurrentPage()==Page.FORREN){
					commandcode ="123";
				}else if(Container.getCurrentPage()==Page.OLD){
					commandcode= "119";
				}
				pop2 =new MapHousePop(this,  xid, commandcode);
				pop2.init();
				pop2.setItemOnclick(this);
				DisplayMetrics metrie = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrie);
				int height = metrie.heightPixels;
				pop2.showAtLocation(findViewById(R.id.map_bottomline), Gravity.TOP, 0, height);
				
			}else {
				showToast("获取失败， 请重试", 2000);
			}
			

		default:
			break;
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.sreach, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:

			finish();

			break;
		case R.id.sreach_map:
			Bundle bundle =new Bundle();
			bundle.putString("from", "Map");
			openActivityForResult(SearchActivity.class,bundle,500);
			break;
		}
		return true;
	}

	
//
//	Handler result_local = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			int result = msg.what;
//			Responds<SecondHouseValue> responde = (Responds<SecondHouseValue>) msg.obj;
//			switch (result) {
//			case 0:
//				showToast("请求失败，请重试", 3000);
//				break;
//
//			default:
//				if (responde.getRESPONSE_CODE_INFO().equals("成功")) {
//					List<SecondHouseValue> value =(List<SecondHouseValue>) responde.getRESPONSE_BODY();
//					
//				} else {
//					showToast("请求失败，请重试", 3000);
//				}
//				break;
//			}
//
//		}
//
//	};
//	//FIXME 未知
//	private void getLocal(final String xid){
//		Thread thread = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				Request request = new Request();
//				if(Container.getCurrentPage()==Page.FORREN){
//					request.setCommandcode("123");
//				}else if(Container.getCurrentPage()==Page.OLD){
//					request.setCommandcode("119");
//				}else {
//					request.setCommandcode("119");
//				}
//				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("xid", xid);
//				request.setREQUEST_BODY(map);
//				Responds<SecondHouseValue> responds = (Responds<SecondHouseValue>) new HttpConnect()
//						.httpUrlConnection(request,
//								new TypeToken<Responds<SecondHouseValue>>() {
//								}.getType());
//
//				if (responds != null) {
//					result_local.obtainMessage(1, responds).sendToTarget();
//				}else
//					result_local.obtainMessage(0).sendToTarget();
//			}
//		});
//		thread.start();
//	}
}
