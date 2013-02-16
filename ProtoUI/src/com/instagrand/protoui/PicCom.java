package com.instagrand.protoui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PicCom extends Activity {

	private PictureItem pic;
	
	public void setPic(PictureItem p){
		pic = p;
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		image.setImageBitmap(pic.getPicture());
		TextView title = (TextView) findViewById(R.id.textView1);
		title.setText(pic.getTitle());
		TextView user = (TextView) findViewById(R.id.textView3);
		user.setText("By " + pic.getUser());
		TextView desc = (TextView) findViewById(R.id.textView2);
		desc.setText(pic.getDescription());
		ListView coms = (ListView) findViewById(R.id.listView1);
		CommentAdapter theComs = new CommentAdapter(this);
		theComs.setComments(pic.getComments());
		coms.setAdapter(theComs);	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle b = this.getIntent().getExtras();
		PictureItem p = null;
		if (b != null){
			p = b.getParcelable("picItem");
		}
		setPic(p);
		//setPic (MainActivity.currPicture);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
