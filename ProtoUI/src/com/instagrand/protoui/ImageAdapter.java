package com.instagrand.protoui;

import java.io.File;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	
	private Context mContext;
	
	public ImageAdapter(Context c){
		mContext = c;
	}

	//private File theImage = new File("/sdcard/sample_0.jpg");
	private PictureItem[] mThumbIds = {
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_0.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_1.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_2.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_3.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","Also, a cat!", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_4.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_5.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_6.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_7.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
	};
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}
	
	

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mThumbIds[arg0];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if(convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(4, 4, 4, 4);
		} else {
			imageView = (ImageView) convertView;
		}
		
		imageView.setImageBitmap(mThumbIds[position].getPicture());
		return imageView;
	}
	
	public PictureItem getPictureItem(int pos){
		return mThumbIds[pos];
	}

}
