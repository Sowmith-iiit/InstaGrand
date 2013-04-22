package com.instagrand.protoui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Adapter for images that appear in the picture selection grid in MainActivity
 * @author Jacob Iott
 *
 */
public class ImageAdapter extends BaseAdapter {
	
	//The context
	private Context mContext;
	
	//A vector of questions for the statically created sample pictures
	
	/**Constructor to create the vectors of questions
	 * 
	 * @param c The context
	 */
	public ImageAdapter(Context c){
		mContext = c;
		
	}
	
	//The pictures displayed to the user
	PictureItem[] mThumbIds;
	
	public void setImages(PictureItem[] pics){
		mThumbIds = pics;
	}
	
	/**
	 * Returns the number of pictures
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}
	
	/**
	 * Returns the PictureItem at the given position.
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mThumbIds[arg0];
	}

	/**
	 * Created by implementing BaseAdapter
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Returns the view at the given position. Returned views are displayed in the GridView in the MainActivity
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if(convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		} else {
			imageView = (ImageView) convertView;
		}
		
		imageView.setImageBitmap(mThumbIds[position].getPicture());
		return imageView;
	}
	
}
