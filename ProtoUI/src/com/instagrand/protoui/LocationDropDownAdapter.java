package com.instagrand.protoui;

import java.util.Vector;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocationDropDownAdapter extends BaseAdapter{
	
	//The Context
	private String[] locs;
	
	private Context mContext;
	
	/**Default Constructor
	 * 
	 * @param c
	 */
	public LocationDropDownAdapter(Context c){
		mContext = c;
	}
	
	/**
	 * Set the comments
	 */
	public void setLocations(String[] l){
		locs = l;
	}
	
	/**
	 * Returns the number of comments
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return locs.length;
	}
	
	/**
	 * Returns the comment at the given index
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return locs[arg0];
	}

	/**
	 * Created from implementing BaseAdapter
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Returns the view at the specified position. Used by the ListView to get the view for each position
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text;
		if(convertView == null) {
			text = new TextView(mContext);
			
			
			//text.setPadding(2, 2, 2, 2);
		} else {
			text = new TextView(mContext);
			
			//text.setPadding(2, 2, 2, 2);
		}
		text.setTextColor(0xFFFFFFFF);
		//text.setBackgroundColor(0x00004684);
		text.setBackgroundColor(0xFF00284D);
		text.setGravity(Gravity.CENTER);
		String theLoc = locs[position];
		text.setText(theLoc);
		text.setTextSize(21);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		text.setLayoutParams(params);
		return text;
	}
}