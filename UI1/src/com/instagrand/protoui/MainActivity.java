package com.instagrand.protoui;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * This is the activity for the picture selector component of the instagrand app.
 * @author Jacob Iott
 * @version 1.0
 *
 */
public class MainActivity extends Activity {
	
	
	
	//The selected picture
	public static PictureItem currPicture;
	
	//The Questions for each of the picture Items
	Vector<Question> coms = new Vector<Question>();
	
	//The adapter for the images
	ImageAdapter theImages;
	private PictureItem[] mThumbIds = {
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_0.jpg").getAbsolutePath()), "Location1", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_1.jpg").getAbsolutePath()), "Location2", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_2.jpg").getAbsolutePath()), "Location3", "1 Campus Dr."),
		new PictureItem("A Dog", "Also, a cat!", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_3.jpg").getAbsolutePath()), "Location1", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_4.jpg").getAbsolutePath()), "Location2", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_5.jpg").getAbsolutePath()), "Location3", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", coms, BitmapFactory.decodeFile(new File("/sdcard/sample_6.jpg").getAbsolutePath()), "Location1", "1 Campus Dr."),
		new PictureItem("A Dog", "What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_7.jpg").getAbsolutePath()), "Location2", "1 Campus Dr."),
	};
	
	/**
	 * Begins when the activity begins.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Add Answers to the Questions
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_table);
		Spinner spinner = (Spinner) findViewById(R.id.Spinner1);
		//LocationDropDownAdapter locs = new LocationDropDownAdapter(this);
		spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item));
		GridView gridview = (GridView) findViewById(R.id.gridview);
		theImages = new ImageAdapter(this);
		//Change items in layout to match location
		Vector<PictureItem> currentImages = new Vector<PictureItem>();
		for (int i = 0; i < mThumbIds.length; i++){
			if (mThumbIds[i].getLocationName().equals(spinner.getSelectedItem())){
				currentImages.add(mThumbIds[i]);
			}
		}
		theImages.setImages((PictureItem[]) currentImages.toArray(new PictureItem[currentImages.size()]));
	    gridview.setAdapter(theImages);
	    //When an item is clicked
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	
	        	//Get the picture item associated with the selected picture
	        	currPicture = (PictureItem) parent.getAdapter().getItem(position);
	        	
	        	//The commented out code below is used to send the picture in the form of a parcel should we choose to attempt that again.
	        	//Bundle b = new Bundle();
	        	//b.putParcelable("picItem", currPicture);
	        	Intent intent = new Intent(MainActivity.this, PicCom.class);
	        	//intent.putExtras(b);
	            startActivity(intent);
	            
	        }
	    });
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				//Change items in layout to match location
				Vector<PictureItem> currentImages = new Vector<PictureItem>();
				for (int i = 0; i < mThumbIds.length; i++){
					if (mThumbIds[i].getLocationName().equals(parent.getSelectedItem())){
						currentImages.add(mThumbIds[i]);
					}
				}
				theImages.setImages((PictureItem[]) currentImages.toArray(new PictureItem[currentImages.size()]));
				theImages.notifyDataSetChanged();
			}

			//Necessary to implement OnItemSelectedListener
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
	    	
	    });
	    
	}
	
	/**
	 * Loads the options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
