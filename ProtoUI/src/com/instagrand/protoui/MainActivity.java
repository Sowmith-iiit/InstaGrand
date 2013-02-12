package com.instagrand.protoui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {

	public static PictureItem currPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_table);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ImageAdapter theImages = new ImageAdapter(this);
	    gridview.setAdapter(theImages);
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	//Bundle b = new Bundle();
	        	currPicture = (PictureItem) parent.getAdapter().getItem(position);
	        	//b.putParcelable("picItem", b);
	        	Intent intent = new Intent(MainActivity.this, PicCom.class);
	        	//intent.putExtras(b);
	            startActivity(intent);
	            
	        }
	    });
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
