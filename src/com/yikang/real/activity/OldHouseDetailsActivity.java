package com.yikang.real.activity;


 
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.yikang.real.R;
import com.yikang.real.application.BaseActivity;

public class OldHouseDetailsActivity  extends BaseActivity implements OnClickListener{

	private ImageView back;

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
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_house_details );
		
			findView();
		
	}

	private void findView() {
		back = (ImageView) findViewById(R.id.back); 
		back.setOnClickListener(this);
		
		
		
		
		
		
		
		
	}
 
	@Override
	public void onClick(View v) {
	 
		switch (v.getId()) {
		case R.id.back:
			
			break;

		default:
			break;
		}
	}

}
