package com.yikang.real.view;

import java.util.List;

import com.yikang.real.R;
import com.yikang.real.bean.Location;
import com.yikang.real.view.PinnedHeaderListView.PinnedHeaderAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter  
		implements OnScrollListener , PinnedHeaderAdapter {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final String TAG = CustomAdapter.class.getSimpleName();
	
	// ===========================================================
	// Fields
	// ===========================================================

	private Context mContext;
	private List<Location> mData;
	private LayoutInflater mLayoutInflater;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public CustomAdapter(Context pContext, List<Location> pData) {
		mContext = pContext;
		mData = pData;
		
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 // �������Ż�ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
            
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title); 
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.contentIcon = (ImageView) convertView.findViewById(R.id.content_icon);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // ��ȡ����
        Location Location = (Location) getItem(position);
        viewHolder.content.setText(Location.getContent());
        viewHolder.contentIcon.setImageResource(R.drawable.ic_launcher);

        if ( needTitle(position) ) {
            // ��ʾ���Ⲣ�������� 
            viewHolder.title.setText(Location.getTitle());
            viewHolder.title.setVisibility(View.VISIBLE);
        } else {
            // ���������ر���
            viewHolder.title.setVisibility(View.GONE);
        }
        
        return convertView;
	}
	
	@Override
	public int getCount() {
		if (null != mData) {
			return mData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null != mData && position < getCount()) {
			return mData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		if ( view instanceof PinnedHeaderListView) {
			((PinnedHeaderListView) view).controlPinnedHeader(firstVisibleItem);	
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
	
	
	@Override
	public int getPinnedHeaderState(int position) {
		if (getCount() == 0 || position < 0) {
			return PinnedHeaderAdapter.PINNED_HEADER_GONE;
		}
		
		if (isMove(position) == true) {
			return PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP;
		}
		
		return PinnedHeaderAdapter.PINNED_HEADER_VISIBLE;
	}


	@Override
	public void configurePinnedHeader(View headerView, int position, int alpaha) {
		// ���ñ��������
		Location Location = (Location) getItem(position);
		String headerValue = Location.getTitle();
		
		Log.e(TAG, "header = " + headerValue);
		
		if (!TextUtils.isEmpty(headerValue)) {
			TextView headerTextView = (TextView) headerView.findViewById(R.id.header);
			headerTextView.setText( headerValue );
		}
		
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 * @param position
	 * @return
	 */
	private boolean needTitle(int position) {
		if (position == 0) {
			return true;
		}
		
        if (position < 0) {
            return false;
        }
		 
		// ��ǰ  // ��һ��
		Location currentEntity = (Location) getItem(position);
		Location previousEntity = (Location) getItem(position - 1);
		if (null == currentEntity || null == previousEntity) {
            return false;
        }
		
		String currentTitle = currentEntity.getTitle();
		String previousTitle = previousEntity.getTitle();
		if (null == previousTitle || null == currentTitle) {
            return false;
        }
		
		if (currentTitle.equals(previousTitle)) {
			return false;
		}
		
		return true;
	}


	private boolean isMove(int position) {
		
	    Location currentEntity = (Location) getItem(position);
	    Location nextEntity = (Location) getItem(position + 1);
	    if (null == currentEntity || null == nextEntity) {
            return false;
        }

	    // ��ȡ����header����
	    String currentTitle = currentEntity.getTitle();
	    String nextTitle = nextEntity.getTitle();
	    if (null == currentTitle || null == nextTitle) {
            return false;
        }
	    
	    // ��ǰ��������һ��header����ǰ����Ҫ�ƶ���
	    if (!currentTitle.equals(nextTitle)) {
            return true;
        }
	    
	    return false;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	private class ViewHolder {
        TextView title;
        TextView content;
        ImageView contentIcon;
    }
	
}
