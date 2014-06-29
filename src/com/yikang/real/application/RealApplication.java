package com.yikang.real.application;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import cn.Bean.util.City;
import cn.Bean.util.More;
import cn.Bean.util.MoreValue;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yikang.real.R;
import com.yikang.real.until.Container;
import com.yikang.real.until.ToastTools;

public class RealApplication extends Application {

	private List<WeakReference<Activity>> mActivityList = new ArrayList<WeakReference<Activity>>();
	private static RealApplication mSingleton;
	private long mLastPressBackTime;

	@Override
	public void onCreate() {
		super.onCreate();
		mSingleton = this;
		City city = new City();
		city.setCity("昆明");
		Container.setCity(city);
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
			map.put("item", str+"万");
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
