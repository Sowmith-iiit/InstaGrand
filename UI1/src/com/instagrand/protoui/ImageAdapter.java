package com.instagrand.protoui;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.graphics.BitmapFactory;
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
	Vector<Question> coms = new Vector<Question>();
	
	/**Constructor to create the vectors of questions
	 * 
	 * @param c The context
	 */
	public ImageAdapter(Context c){
		mContext = c;
		coms.add(new Question("Bill", "He looks like my dog!"));
		Vector<Comment> subComs = new Vector<Comment>();
		Vector<Comment> subComs1 = new Vector<Comment>();
		subComs1.add(new Comment("Maria", "Mine would be."));
		subComs1.add(new Comment("Lee", "Scaredy Cat!"));
		coms.add(new Question("Ted", "I'll bet cats aren't afraid of him LOL", new Date(), subComs1));
		subComs = new Vector<Comment>();
		subComs.add(new Comment("Fred", "Now I want Taco Bell"));
		subComs.add(new Comment("George", "Dong!"));
		coms.add(new Question("Jill", "Yo Quiero Taco Bell", new Date(), subComs));
	}
	
	//The static sample pictures
	private PictureItem[] mThumbIds = {
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_0.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_1.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_2.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "Also, a cat!", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_3.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_4.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_5.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_6.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_7.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
	};
	
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
