package com.yikang.real.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class DisTouchViewPager extends ViewPager{

    private boolean isCanScroll = false;  
    
    public DisTouchViewPager(Context context) {  
        super(context);  
    }  
  
    public DisTouchViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
      return false;
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
      return false;
    }
  
   
}
