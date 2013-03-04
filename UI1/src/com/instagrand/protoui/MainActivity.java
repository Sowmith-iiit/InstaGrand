package com.instagrand.protoui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * This is the activity for the picture selector component of the instagrand app.
 * @author Jacob Iott
 * @version 1.0
 *
 */
public class MainActivity extends Activity {

	//The selected picture
	public static PictureItem currPicture;
	
	/**
	 * Begins when the activity begins.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_table);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ImageAdapter theImages = new ImageAdapter(this);
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
