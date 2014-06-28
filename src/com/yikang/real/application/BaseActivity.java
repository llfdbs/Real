package com.yikang.real.application;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yikang.real.R;
import com.yikang.real.until.ToastTools;

public abstract class BaseActivity extends ActionBarActivity{

	public static final int REQUEST_FIRST_USER = 1;

	private int mShowingDialogID;
	private long mLastPressBackTime;

	// private StartThread mStartThread = null;

	/**
	 * 显示Toast消息
	 * 
	 * @param pMsg
	 *            消息内容
	 */
	public void showToast(String pMsg, int duration) {
		ToastTools.showToast(this, pMsg, duration);
	}

	/**
	 * 显示Toast消息
	 * 
	 * @param pMsg
	 *            消息资源ID
	 */
	protected void showToastResources(int pMsg, int duration) {
		ToastTools.showToastResources(this, pMsg, duration);
	}

	/**
	 * 运行其他Activity
	 * 
	 * @param pClass
	 *            目标Activity的类对象
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 打印输出
	 * 
	 * @param str
	 */
	protected void print(String str) {
		boolean isprint = true;
		if (isprint)
			System.out.println(str);

	}

	/**
	 * 运行其他Activity
	 * 
	 * @param pClass
	 *            目标Activity的类对象
	 * @param pBundle
	 *            额外信息
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent _Intent = new Intent(this, pClass);
		if (pBundle != null) {
			_Intent.putExtras(pBundle);
		}

		startActivity(_Intent);
//		 overridePendingTransition(R.anim.new_in_from_right,
//		 R.anim.new_out_to_left);//苹果的效果
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
//		 overridePendingTransition(android.R.anim.fade_in,
//		 android.R.anim.fade_out); // 淡进淡出
		// overridePendingTransition(android.R.anim.slide_out_right,
		// android.R.anim.slide_in_left); // 有劲做出
	}

	/**
	 * 运行其他Activity，并期待返回结果�?
	 * 
	 * @param pClass
	 *            目标Activity的类对象
	 * @param pBundle
	 *            存放数据的Bundle对象，若不需要传递数据，传入null�?
	 * @param pRequestCode
	 *            请求Code
	 */
	protected void openActivityForResult(Class<?> pClass, Bundle pBundle,
			int pRequestCode) {
		Intent _Intent = new Intent(this, pClass);
		if (pBundle != null) {
			_Intent.putExtras(pBundle);
		}
		startActivityForResult(_Intent, pRequestCode);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);// 左进右出
//		 overridePendingTransition(android.R.anim.fade_in,
//				 android.R.anim.fade_out); // 淡进淡出
	}

	/**
	 * 加载Layout文件，生成View组件�?
	 * 
	 * @param pLayoutId
	 *            Layout文件的资源Id
	 * @param pRoot
	 *            生成的View�?��的ViewGroup，传入null表示没有父ViewGroup�?
	 * @return 若传入了pRoot，则返回pRoot；反之，则返回生成的View
	 */
	protected View inflateView(int pLayoutId, ViewGroup pRoot) {
		return getLayoutInflater().inflate(pLayoutId, pRoot);
	}

	/**
	 * 加载MenuLayout文件，生成菜单（选项菜单、上下文菜单等）
	 * 
	 * @param pMenuLayoutId
	 *            MenuLayout文件的资源Id，例如�?R.menu.main_activity�?
	 * @param pMenu
	 *            用来存放菜单项和子菜单的Menu对象
	 */
	protected void inflateMenu(int pMenuLayoutId, Menu pMenu) {
		getMenuInflater().inflate(pMenuLayoutId, pMenu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add(0, MENU_CHANGE_USER, 1,
		// "切换用户").setIcon(android.R.drawable.ic_menu_revert);
		// menu.add(0, MENU_SETTING, 2,
		// "设置").setIcon(android.R.drawable.ic_menu_preferences);
		// menu.add(0, MENU_EXIT, 3,
		// "�?��").setIcon(android.R.drawable.ic_menu_close_clear_cancel);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	protected void onPrepareDialog(int pId, Dialog pDialog, Bundle pArgs) {
		super.onPrepareDialog(pId, pDialog, pArgs);
		mShowingDialogID = pId;
	}

	protected int getShowingDialogID() {
		return mShowingDialogID;
	}



	/**
	 * 初始化成员变量 本方法应该在子类的onCreate方法中，绑定Layout文件后被调用
	 * 
	 */
	protected abstract void initData();

	/**
	 * 初始化成员View 本方法应该在子类的onCreate方法中，绑定Layout文件后被调用
	 */
//	protected abstract void initViews();

	/**
	 * 初始化监听器 本方法应该在子类的onCreate方法中，绑定Layout文件后被调用
	 */
	protected abstract void initListeners();
	
	protected abstract void initActionBar();

 
	

}
