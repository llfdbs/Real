package com.yikang.real.application;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yikang.real.until.ToastTools;

public class RealApplication extends Application{

	private List<WeakReference<Activity>> mActivityList = new ArrayList<WeakReference<Activity>>();
	private static RealApplication mSingleton;
	private long mLastPressBackTime;


	@Override
	public void onCreate() {
		super.onCreate();
		mSingleton = this;
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
				context).threadPoolSize(7).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
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
			ToastTools.showToast(this, "退出程序",
					500);

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
	
	public ArrayList<HashMap<String,String>> getPicese(int resouce){
		ArrayList<HashMap<String,String>> list =new ArrayList<HashMap<String,String>>();
		String[] strs =getResources().getStringArray(resouce);
		for(String str :strs){
			HashMap<String,String> map =new HashMap<String, String>();
			map.put("item", str);
			list.add(map);
		}
		
		return list;
	}
}
