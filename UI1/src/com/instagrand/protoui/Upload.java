package com.instagrand.protoui;

import java.io.FileNotFoundException;
import java.io.IOException;


import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;

/**
 * Activity that allows users to upload pictures saved from their phone.
 * @author Jacob Iott
 * @version 1.0
 *
 */
public class Upload extends Activity {
	
	private static final int CAMERA_PIC_REQUEST = 1338;
	
	//The image to be uploaded
	private Bitmap theImage;
	
	//The pictureItem to be sent back to the main activity
	private PictureItem thePic = new PictureItem(null, null, null, null, null, null);
	
	//The uri of the image taken by the camera
	private Uri mCapturedImageURI;
	
	//True if the picture has been uploaded
	private boolean uploaded = false;

	/**
	 * Executes when the activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		try {
			theImage = (Bitmap) savedInstanceState.getParcelable("image");
		} catch (NullPointerException npe){
			theImage = null;
		}
		try {
			mCapturedImageURI = (Uri) savedInstanceState.getParcelable("uri");
		} catch (NullPointerException npe){
			mCapturedImageURI = null;
		}
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
	 * Called whenever the activity ends
	 */
	public void onDestroy(){
		if(!uploaded){
			theImage.recycle();
			theImage = null;
		}
		super.onDestroy();
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
		if (theImage != null){
			thePic.setPicture(theImage);
		} else {
			notReady = true;
		}
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
			uploaded = true;
			finish();
		}
		
	}
	
	/**
	 * Accepts the choice of picture from the picture picker or the picture taken from the camera.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK){ //If this is the camera activity
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
	        int column_index_data = cursor
	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        String capturedImageFilePath = cursor.getString(column_index_data);
	        if(theImage != null){
	        	theImage.recycle();
	        	theImage = null;
	        }
	        theImage = BitmapFactory.decodeFile(capturedImageFilePath);
	        ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageBitmap(theImage);
			thePic.setPicture(theImage);
			
	    }
	    else if (resultCode == RESULT_OK) //if this is the picture selector activity
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
	
	/**
	 * Initiates the camera activity to take a picture
	 * @param v
	 */
	public void takePic(View v) {
		try {
		    String fileName = "temp.jpg";
		    ContentValues values = new ContentValues();
		    values.put(MediaStore.Images.Media.TITLE, fileName);
		    mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
		    startActivityForResult(intent, CAMERA_PIC_REQUEST);
		} catch (Exception e) {
		    Log.e("", "", e);
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
	
	/**
	 * Necessary to preserve the image when flipped.
	 */
	protected void onSaveInstanceState(Bundle outState){
		outState.putParcelable("image", theImage); // These variables would otherwise be lost
		outState.putParcelable("uri", mCapturedImageURI);
	}

}
