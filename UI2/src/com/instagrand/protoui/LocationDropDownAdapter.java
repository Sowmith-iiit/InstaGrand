package com.instagrand.protoui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter for containing the items in the drop down list
 * @author Jacob Iott
 *
 */
public class LocationDropDownAdapter extends BaseAdapter {
	
	//The locations (examples for now)
	private String[] locs = {"location1", "location2", "location3", "location4"};
	
	//The context
	private Context mContext;
	
	//Default constructor
	public LocationDropDownAdapter(Context c){
		mContext = c;
	}
	
	/**
	 * Returns the number of locations
	 */
	@Override
	public int getCount() {
		return locs.length;
	}

	/**
	 * Returns the item at the specified location
	 */
	@Override
	public Object getItem(int position) {
		return locs[position];
	}

	/**
	 * Required to implement BaseAdapter
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Returns the item at the specified position
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView theText;
		if (convertView == null){
			theText = new TextView(mContext);
		} else {
			theText = (TextView) convertView;
		}
		theText.setText(locs[position]);
		return theText;
	}

}
