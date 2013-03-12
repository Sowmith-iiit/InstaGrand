package com.instagrand.protoui;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

/**
 * Activity that allows users to upload pictures saved from their phone.
 * @author Jacob Iott
 * @version 1.0
 *
 */
public class Upload extends Activity {
	
	//The image to be uploaded
	Bitmap theImage;
	
	//The pictureItem to be sent back to the main activity
	PictureItem thePic = new PictureItem(null, null, null, null, null, null);

	/**
	 * Executes when the activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		image.setImageBitmap(theImage);
		Spinner loc = (Spinner) findViewById(R.id.spinner1);
		loc.setAdapter(ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item));
		EditText titleText = (EditText) findViewById(R.id.editText1);
		EditText desText = (EditText) findViewById(R.id.editText2);
		titleText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		desText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		// Show the Up button in the action bar.
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_upload, menu);
		return true;
	}
	
	/**
	 * Opens the photopicker to allow the user to open a picture on their phone
	 * @param v
	 */
	public void selectPic(View v){
		Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	}
	
	/**
	 * Sends the picture back to the main activity
	 * @param v
	 */
	public void uploadPic(View v){
		boolean notReady = false;
		EditText titleText = (EditText) findViewById(R.id.editText1);
		EditText desText = (EditText) findViewById(R.id.editText2);
		Spinner loc = (Spinner) findViewById(R.id.spinner1);
		if (!titleText.getText().toString().trim().equals("")){
			thePic.setTitle(titleText.getText().toString());
		} else {
			notReady = true;
		}
		if (!desText.getText().toString().trim().equals("") && !notReady){
			thePic.setDescription(desText.getText().toString());
		} else {
			notReady = true;
		}if (!notReady){
			String locText = (String) loc.getSelectedItem();
			thePic.setLocationName(locText);
		} 
		thePic.setUser("you");
		Log.i("Upload.java", "notReady: " + notReady);
		if (!notReady){
			MainActivity.addImage(thePic);
			Intent data = new Intent();
			finish();
		}
		
	}
	
	/**
	 * Accepts the choice of picture from the picture picker
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    super.onActivityResult(requestCode, resultCode, data);
	    if (resultCode == RESULT_OK)
	    {
	        Uri chosenImageUri = data.getData();

	        theImage = null;
	        try {
				theImage = Media.getBitmap(this.getContentResolver(), chosenImageUri);
				ImageView image = (ImageView) findViewById(R.id.imageView1);
				image.setImageBitmap(theImage);
				thePic.setPicture(theImage);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
