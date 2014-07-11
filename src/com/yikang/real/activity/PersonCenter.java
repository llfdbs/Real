package com.yikang.real.activity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;
import com.yikang.real.until.Container;

@SuppressLint("ResourceAsColor")
public class PersonCenter extends BaseActivity{

	private ActionBar actionbar;
	private TextView username;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usercenter);
		initActionBar();
		findView();
	}

	protected void findView(){
		
		TextView text =(TextView) findViewById(R.id.usercenter_username);
		text.setText(Container.getUSER().getUsername());
		Button unlogin = (Button) findViewById(R.id.unlogin);
		Button change =(Button) findViewById(R.id.change);
		
		unlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Container.setUSER(null);
				finish();
			}
		});
		change .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub
		actionbar = getSupportActionBar();
		int titleId = Resources.getSystem().getIdentifier(  
                "action_bar_title", "id", "android"); 
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextColor(R.color.black);
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.top));
		actionbar.setTitle("用户中心");
		actionbar.setIcon(R.drawable.back);
	}

}
