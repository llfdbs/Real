package com.yikang.real.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.yikang.real.R;


/**
 * 搜索界面
 * 
 * @author vv
 * 
 */
public class MainActivity extends Activity{

	private AutoCompleteTextView autoCompleteTextView;
	private Button searchButton;
	private String[] histories;
	private static final String LOCALHISTORY ="localhistory";
	private Bundle bundle_send;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.materialsearch);
		initViews();
		initData();
		initListeners();

	}

	protected void initData() {
		// TODO Auto-generated method stub
		 initAutoComplete("history", autoCompleteTextView); 
	}

	protected void initViews() {
		// TODO Auto-generated method stub
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto);
		searchButton = (Button) findViewById(R.id.search);
	} 

	protected void initListeners() {
		// TODO Auto-generated method stub
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
					if(null== autoCompleteTextView.getText().toString().trim()||autoCompleteTextView.getText().toString().trim().equals("")){
						Toast.makeText(MainActivity.this,"关键字为空,请输入关键字",Toast.LENGTH_SHORT).show();
						return;
					}else{
					saveHistory("history", autoCompleteTextView);
					
					bundle_send = new Bundle();
					
					bundle_send.putString("keyword",autoCompleteTextView.getText().toString().trim());
//					bundle_send.putString("title",title);
					Intent intent=new Intent(MainActivity.this,Result.class);
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
					finish();
					}
					
				}
			
		});
	}

	

	/** 
	 
     * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段 
 
     *  
 
     * @param name 
 
     *            保存在sharedPreference中的字段名 
 
     * @param autoCompleteTextView 
 
     *            要操作的AutoCompleteTextView 
 
     */  
 
    private void saveHistory(String name,  
 
            AutoCompleteTextView autoCompleteTextView) {  
 
        String text = autoCompleteTextView.getText().toString().trim();  
 
        SharedPreferences sp = getSharedPreferences(LOCALHISTORY, MODE_PRIVATE);  
 
        String longhistory = sp.getString(name, "");  
 
        if (!longhistory.contains(text + ",")) {  
 
            StringBuilder sb = new StringBuilder(longhistory);  
 
            sb.insert(0, text + ",");  
 
            sp.edit().putString(name, sb.toString().trim()).commit();  
 
        }  
 
    }  
    
    /** 
    
     * 初始化AutoCompleteTextView，最多显示12项提示，使 AutoCompleteTextView在一开始获得焦点时自动提示 
 
     *  
 
     * @param field 
 
     *            保存在sharedPreference中的字段名 
 
     * @param autoCompleteTextView 
 
     *            要操作的AutoCompleteTextView 
 
     */  
 
    private void initAutoComplete(String name,  
 
            final AutoCompleteTextView autoCompleteTextView) {  
 
        SharedPreferences sp = getSharedPreferences(LOCALHISTORY, 0);  
 
        String longhistory = sp.getString(name,"");  
 
        histories = longhistory.split(",");  
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  
 
                android.R.layout.simple_dropdown_item_1line, histories);  
 
        // 只保留最近的12条的记录  
 
        if (histories.length > 12) {  
 
            String[] newHistories = new String[12];  
 
            System.arraycopy(histories, 0, newHistories, 0, 12);  
 
            adapter = new ArrayAdapter<String>(this,  
 
                    android.R.layout.simple_dropdown_item_1line, newHistories);  
 
        }  
 
        autoCompleteTextView.setAdapter(adapter);  
        
        autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				bundle_send = new Bundle();
				bundle_send.putString("keyword",autoCompleteTextView.getText().toString().trim());
			
					Intent intent=new Intent(MainActivity.this,Result.class);
					bundle_send.putString("keyword",autoCompleteTextView.getText().toString().trim());
					startActivity(intent);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
					finish();
				
				
				//MaterialSearchActivity.this.finish();
			}
		});
 
        autoCompleteTextView  
 
                .setOnFocusChangeListener(new OnFocusChangeListener() {  
 
                    @Override  
 
                    public void onFocusChange(View v, boolean hasFocus) {  
 
                        AutoCompleteTextView view = (AutoCompleteTextView) v;  
 
                        if (hasFocus) {  
                        	if(!histories[0].equals("")){
                            view.showDropDown();  
                        	}
 
                        }  
 
                    }  
 
                });  
 
    }  
}
