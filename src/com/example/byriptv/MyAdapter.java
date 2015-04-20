package com.example.byriptv;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public /*方便以后扩展*/

class MyAdapter extends BaseAdapter {
	
	private Context mContext;
	private float mDensity= 0;
	private int color;
	private int size;
	
	public MyAdapter(Context context,int color,int size) {
		mDensity = context.getResources().getDisplayMetrics().density;
		mContext = context;
		this.color = color;
		this.size = size;
	}
	@Override
	public int getCount() {
		return AppConfig.iPtvUrls.length/2;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return AppConfig.iPtvUrls[position * 2 + 1];
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = new TextView(mContext);
			
//			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			convertView.setLayoutParams(params);
			
			((TextView)convertView).setTextSize(size);
			((TextView)convertView).setTextColor(color);
			((TextView)convertView).setSingleLine();
			((TextView)convertView).setEllipsize(TruncateAt.END);
			((TextView)convertView).setPadding((int)(10*mDensity), (int) (5*mDensity), 0,(int)(5*mDensity));
			
//			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent,false);
		
		} 
		
//		((TextView)convertView).setTextColor(color);
		((TextView)convertView).setText(AppConfig.iPtvUrls[position * 2  + 1]);
		
		return convertView;
	}
	
}