package com.instagrand.protoui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class PicCom extends Activity {

	private PictureItem pic;
	
	public void setPic(PictureItem p){
		pic = p;
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		image.setImageBitmap(pic.getPicture());
		TextView title = (TextView) findViewById(R.id.textView1);
		title.setText(p.getTitle());
		TextView desc = (TextView) findViewById(R.id.textView2);
		desc.setText(p.getDescription());
		
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setPic (MainActivity.currPicture);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
