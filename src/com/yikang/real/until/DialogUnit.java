package com.yikang.real.until;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

public class DialogUnit {

	private Context context;
	Builder builder =null;
	public DialogUnit(Context context){
		this.context=context;
		create();
	}
	public void create(){
		builder=new Builder(context);
//		builder.setAdapter(adapter, listener)
	}
}
