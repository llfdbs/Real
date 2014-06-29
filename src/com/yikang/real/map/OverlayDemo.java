package com.yikang.real.map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yikang.real.R;
import com.yikang.real.application.RealApplication;
/**
 * @author 袁天祥
 * 功能1:地图移动缩放时显示边界坐标范围
 * 功能2:地图缩放到一定级别的时候 地图上四个点变成一个点
 * 功能3:加载地图实现首次定位，通过按钮实现手动定位
 */
public class OverlayDemo extends Activity {

	/**
	 *  MapView 是地图主控件
	 */
	private MapView mMapView = null;
	/**
	 *  用MapController完成地图控制 
	 */
	private MapController mMapController = null;
	private MyOverlay mOverlay = null;
	private PopupOverlay   pop  = null;
	private TextView  popupText = null;
//	private View viewCache = null;
	private Button button = null;
	private Button requestLocButton=null;
	ArrayList<OverlayItem> list=null;
	float level=0;
	
	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
		
	//定位图层
	locationOverlay myLocationOverlay = null;
	MKMapViewListener mMapListener = null;
	OnTouchListener listener = null; 
	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	boolean isLocationClientStop = false;
	private Map<String, Float> map1=new HashMap<String, Float>();
	private Map<String, Float> map2=new HashMap<String, Float>();
	private Map<String, Float> map3=new HashMap<String, Float>();
	private Map<String, Float> map4=new HashMap<String, Float>();
	private Map<String, Float> map5=new HashMap<String, Float>();
	private List<Map<String,Float>> srcoue=new ArrayList<Map<String,Float>>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 使用地图sdk前需先初始化BMapManager.
         * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
        RealApplication app = (RealApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(this);
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            app.mBMapManager.init(RealApplication.strKey,new RealApplication.MyGeneralListener());
        }
        /**
          * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
          */
        setContentView(R.layout.activity_overlay);
        mMapView = (MapView)findViewById(R.id.bmapView);
        requestLocButton = (Button)findViewById(R.id.button1);
        OnClickListener btnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//手动定位请求
				requestLocClick();
			}
		};
	    requestLocButton.setOnClickListener(btnClickListener);
        /**
         * 获取地图控制器
         */
        mMapController = mMapView.getController();
        /**
         *  设置地图是否响应点击事件  .
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
        init();
        initOverlay();
        move();
       
        /**
         * 设定地图中心点
         */
//        GeoPoint p = new GeoPoint((int)(mLat5 * 1E6), (int)(mLon5* 1E6));
//        mMapController.setCenter(p);
      //定位初始化
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        //定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		//设置定位数据
	    myLocationOverlay.setData(locData);
	    //添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		//修改定位数据后刷新图层生效
		mMapView.refresh();
    } 
  /**
   * 初始化坐标点的数据
   */
    private void init(){
    	map1.put("lon", (float) 113.670141);
    	map1.put("lat", (float) 34.837953);
    	map2.put("lon", (float) 113.602553);
    	map2.put("lat", (float) 34.757838);
    	map3.put("lon", (float) 113.755606);
    	map3.put("lat", (float) 34.769381);
    	map4.put("lon", (float) 113.684901);
    	map4.put("lat", (float) 34.714861);
    	map5.put("lon", (float) 113.676268);
    	map5.put("lat", (float) 34.780611);
    	srcoue.add(map1);
    	srcoue.add(map2);
    	srcoue.add(map3);
    	srcoue.add(map4);
    	srcoue.add(map5);
    	createOverLays(srcoue);
    }
    
    //继承MyLocationOverlay重写dispatchTap实现点击处理
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  			// TODO Auto-generated constructor stub
  		}
  		@Override
  		protected boolean dispatchTap() {
  			// TODO Auto-generated method stub
  			//处理点击事件,弹出泡泡
//  			popupText.setBackgroundResource(R.drawable.popup);
//			popupText.setText("我的位置");
//			pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
//					8);
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
                return ;
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            mMapView.refresh();
            //是手动触发请求或首次定位时，移动到定位点
            if (isRequest || isFirstLoc){
            	//移动地图到定位点
                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;
            }
            //首次定位完成
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    /**
     * 设置地图事件监听
     */
    public void move(){
    	  mMapListener = new MKMapViewListener() {
  			@Override
  			public void onMapMoveFinish() {
  				/**
  				 * 在此处理地图移动完成回调
  				 * 缩放，平移等操作完成后，此回调被触发
  				 */
  				level=mMapView.getZoomLevel();
  				reloadOverlay(level);
  				updateMapState();
  			}
  			
  			@Override
  			public void onClickMapPoi(MapPoi mapPoiInfo) {
  				/**
  				 * 在此处理底图poi点击事件
  				 * 显示底图poi名称并移动至该点
  				 * 设置过： mMapController.enableClick(true); 时，此回调才能被触发
  				 * 
  				 */
  				level=mMapView.getZoomLevel();
  				reloadOverlay(level);
  			}

  			@Override
  			public void onGetCurrentMap(Bitmap b) {
                  
  			}

  			@Override
  			public void onMapAnimationFinish() {
  				/**
  				 *  地图完成带动画的操作（如: animationTo()）后，或是放大缩小按钮此回调被触发
  				 */
  				level=mMapView.getZoomLevel();
  				reloadOverlay(level);
  				updateMapState();
  			}
  		};
  		mMapView.regMapViewListener(RealApplication.getInstance().mBMapManager, mMapListener);
    }
    /**
     * 
     * @param level 地图缩放级别
     */
	public void reloadOverlay(float level){
    	//清除自定义点
    	mMapView.getOverlays().remove(mOverlay);
    	if(level<8){
    		 mOverlay.removeAll();
             mOverlay.addItem(list.get(5));
    	}else {
    		mOverlay.removeAll();
    		mOverlay.addItem(list.get(0));
    		mOverlay.addItem(list.get(1));
    		mOverlay.addItem(list.get(2));
    		mOverlay.addItem(list.get(3));
    		 
    	}
    	mMapView.getOverlays().add(mOverlay);
        mMapView.refresh();
    }
    
    public void initOverlay(){
    	/**
    	 * 创建自定义overlay
    	 */
         mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.icon_marka),mMapView);	
         /**
          * 将item 添加到overlay中
          * 注意： 同一个itme只能add一次
          */
			mOverlay.addItem(list.get(0));
			mOverlay.addItem(list.get(1));
			mOverlay.addItem(list.get(2));
			mOverlay.addItem(list.get(3));
			
         /**
          * 保存所有item，以便overlay在reset后重新添加
          */
//         mItems = new ArrayList<OverlayItem>();
//         mItems.addAll(mOverlay.getAllItem());
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
//         viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
//         popupText =(TextView) viewCache.findViewById(R.id.textcache);
//         button = new Button(this);
//         button.setBackgroundResource(R.drawable.popup);
         
         /**
          * 坐标点 点击监听
          */
         PopupClickListener popListener = new PopupClickListener(){

			@Override
			public void onClickedPopup(int arg0) {
				// TODO Auto-generated method stub
				
			}
         }; 
         pop = new PopupOverlay(mMapView,popListener);
         
    }
    /**
     * @param srcoue 四个点的坐标集合
     * @return
     */
    private List<OverlayItem> createOverLays(List<Map<String,Float>> srcoue){
    	LayoutInflater inflate=LayoutInflater.from(this);
    	list =new ArrayList<OverlayItem>();
    	for(Map<String, Float> map :srcoue){
    		GeoPoint p = new GeoPoint ((int)(map.get("lat")*1E6),(int)(map.get("lon")*1E6));
    		OverlayItem item = new OverlayItem(p,"","");
    		View view= inflate.inflate(R.layout.itme, null);
    		Bitmap bit=convertViewToBitmap(view);
    		 Drawable drawable = (Drawable)new BitmapDrawable(bit);
    		item.setMarker(drawable);
    		list.add(item);
    	}
    	
    	GeoPoint p = new GeoPoint ((int)(34.780611*1E6),(int)(113.676268*1E6));
		OverlayItem item = new OverlayItem(p,"","");
		View view= inflate.inflate(R.layout.itme, null);
		TextView text =(TextView)view.findViewById(R.id.text1);
		text.setText("4");
		Bitmap bit=convertViewToBitmap(view);
		 Drawable drawable = (Drawable)new BitmapDrawable(bit);
		item.setMarker(drawable);
		list.add(item);
    	
    	return list;
    	
    }
    
    
    public static Bitmap convertViewToBitmap(View view) {
    	System.out.println(view.getMeasuredHeight());
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());  //根据字符串的长度显示view的宽度
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
  }
    
    /**
     * 获取屏幕边界坐标范围
     */
    private void updateMapState(){
 		//获取屏幕右上左下角坐标的另一种方法
// 		GeoPoint centerPoint = mMapView.getMapCenter();// 地图中心坐标点 
// 		int tpSpan = mMapView.getLatitudeSpan();// 当前纬线的跨度（从地图的上边缘到下边缘）
// 		int lrSpan = mMapView.getLongitudeSpan();// 当前经度的跨度（从地图的左边缘到右边缘）
// 		GeoPoint point = new GeoPoint(centerPoint.getLatitudeE6()- tpSpan / 2, centerPoint.getLongitudeE6() + lrSpan / 2);// 右上角
// 		GeoPoint point2 = new GeoPoint(centerPoint.getLatitudeE6()+ tpSpan / 2, centerPoint.getLongitudeE6() - lrSpan / 2);// 左下角
    	//隐藏“我的位置”的显示信息
 		pop.hidePop();
 		// 获得屏幕右上角和左下角的经纬度
 		GeoPoint pointLeft = mMapView.getProjection().fromPixels(0,
 				mMapView.getHeight());

 		 GeoPoint pointRight = mMapView.getProjection().fromPixels(
 				 mMapView.getWidth(), 0); 
 		  
        Toast.makeText(this, 
   	         "纬度范围:"+pointLeft.getLatitudeE6()+"~"+pointRight.getLatitudeE6()+"\n"+"经度范围:"+pointLeft.getLongitudeE6()+"~"+pointRight.getLongitudeE6(), Toast.LENGTH_SHORT)
   		     .show();
        
 }
    /**
     * 手动触发一次定位请求
     */
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        Toast.makeText(OverlayDemo.this, "正在定位…", Toast.LENGTH_SHORT).show();
    }
    /**
     * 清除所有Overlay
     * @param view
     */
//    public void clearOverlay(View view){
//    	mOverlay.removeAll();
//    	if (pop != null){
//            pop.hidePop();
//    	}
//    	mMapView.removeView(button);
//    	mMapView.refresh();
//    }
    /**
     * 重新添加Overlay
     * @param view
     */
//    public void resetOverlay(View view){
//    	clearOverlay(null);
//    	//重新add overlay
//    	mOverlay.addItem(mItems);
//    	mMapView.refresh();
//    }
   
    @Override
    protected void onPause() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
    	 */
        mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    	 */
        mMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
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
    
}
