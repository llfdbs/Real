package com.yikang.real.application;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import cn.Bean.util.City;
import cn.Bean.util.More;
import cn.Bean.util.MoreValue;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yikang.real.R;
import com.yikang.real.bean.User;
import com.yikang.real.until.Container;
import com.yikang.real.until.ToastTools;

public class RealApplication extends Application{

	private List<WeakReference<Activity>> mActivityList = new ArrayList<WeakReference<Activity>>();
	private static RealApplication mSingleton;
	private long mLastPressBackTime;

	public boolean m_bKeyRight = true;
	public BMapManager mBMapManager = null;

	public static final String strKey = "5C2B70DCF6E387561F7FCDF5C7B893430AFD3966";

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(
					RealApplication.getInstance().getApplicationContext(),
					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						RealApplication.getInstance().getApplicationContext(),
						"您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						RealApplication.getInstance().getApplicationContext(),
						"输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				
				RealApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initEngineManager(this);
		mSingleton = this;
		SharedPreferences share =getSharedPreferences("city", Context.MODE_PRIVATE);
		City city = new City();
		if(share!=null){
			city.setCity(share.getString("name", "昆明"));
			city.setLat(share.getFloat("lat", (float) 24.872058314636));
			city.setLng(share.getFloat("lng", (float) 102.59540044824));
		}
		Container.setCity(city);
		SharedPreferences share2 =getSharedPreferences("user", 0);
		if(share2!=null&&share2.getString("tel", null)!=null){
			User user =new User();
			user.setUsername(share.getString("tel", "15639932022"));
			user.setUid(share.getString("id", ""));
			Container.setUSER(user);
		}
		initImageLoader(getApplicationContext());
	}

	public static RealApplication getInstance() {
		return mSingleton;
	}

	/**
	 * 图片加载设置属性
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPoolSize(7)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		WeakReference<Activity> _WeakReference = new WeakReference<Activity>(
				activity);
		mActivityList.add(_WeakReference);
	}

	// 遍历所有的Activty并退出程序
	public void exit() {

		if ((System.currentTimeMillis() - mLastPressBackTime) > 2000) {
			ToastTools.showToast(this, "退出程序", 500);

			mLastPressBackTime = System.currentTimeMillis();
			for (int i = mActivityList.size() - 1; i >= 0; i--) {
				WeakReference<Activity> _Tem = mActivityList.get(i);
				if (_Tem != null) {
					Activity _Activity = _Tem.get();
					if (_Activity != null) {
						_Activity.finish();
					}
				}
			}
		} else {
			RealApplication.getInstance().exit();
		}

		// System.exit(0);
	}

	public ArrayList<HashMap<String, String>> getPicese(int resouce) {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String[] strs = getResources().getStringArray(resouce);
		for (String str : strs) {
			HashMap<String, String> map = new HashMap<String, String>();
			if (!str.equals("不限")) {
				map.put("item", str + "万");
			} else
				map.put("item", str);
			list.add(map);
		}

		return list;
	}

	// 创建全局基本参数
	public List<More> createMore() {
		List<More> temp = new ArrayList<More>();
		String[] strs = null;
		Integer[] ids = new Integer[] { R.array.housetype, R.array.Mj,
				R.array.age, R.array.ztype, R.array.desc };
		for (int i = 0; i < ids.length; i++) {
			strs = getResources().getStringArray(ids[i]);
			More more = new More();
			List<MoreValue> morev = new ArrayList<MoreValue>();
			switch (ids[i]) {
			case R.array.housetype:
			case R.array.ztype:
			case R.array.desc:
				int t = 0;
				for (String str : strs) {
					MoreValue mv = new MoreValue();
					mv.setName(str);
					mv.setValue(String.valueOf(t));
					t++;
					morev.add(mv);
				}

				break;

			case R.array.age:
			case R.array.Mj:
				for (String str : strs) {
					MoreValue mv = new MoreValue();
					mv.setName(str);
					if (str.equals("不限")) {
						mv.setValue("");
					} else
						mv.setValue(str);
					morev.add(mv);
				}
				break;
			}
			switch (ids[i]) {
			case R.array.housetype:
				more.setMoreName("房型");
				more.setKey("rType");
				break;
			case R.array.ztype:
				more.setMoreName("类型");
				more.setKey("ztype");
				break;
			case R.array.desc:
				more.setMoreName("排序");
				more.setKey("desc");
				break;
			case R.array.age:
				more.setMoreName("房龄");
				more.setKey("age");
				break;
			case R.array.Mj:
				more.setMoreName("面积");
				more.setKey("MJ");
				break;
			}

			more.setDetail(morev);
			temp.add(more);
		}
		return temp;
	}
}
