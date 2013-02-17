package com.instagrand.protoui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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
		EditText newCom = (EditText) findViewById(R.id.editText1);
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

	public void postComment(View v){
		EditText newCom = (EditText) findViewById(R.id.editText1);
		if (!newCom.getText().toString().trim().equals("")){
			pic.addComment("Anonymous", newCom.getText().toString());
		}
		newCom.setText("");
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		newCom.clearFocus();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
