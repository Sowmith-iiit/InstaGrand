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
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
//import android.support.v4.app.NavUtils;

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
	
	private boolean savedImage = false;

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
			savedImage = savedInstanceState.getBoolean("savedI");
		} catch (NullPointerException npe){
			savedImage = false;
		}
		try {
			mCapturedImageURI = (Uri) savedInstanceState.getParcelable("uri");
		} catch (NullPointerException npe){
			mCapturedImageURI = null;
		}
		savedImage = false;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			LinearLayout rel = (LinearLayout) findViewById(R.id.RelativeLayout1);
			GradientDrawable qgd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF00284D, 0xFF000000});
			rel.setBackgroundDrawable(qgd);
		} else {
			RelativeLayout rel = (RelativeLayout) findViewById(R.id.RelativeLayout1);
			GradientDrawable qgd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF00284D, 0xFF000000});
			rel.setBackgroundDrawable(qgd);
		}
		
		Button upload = (Button) findViewById(R.id.button1);
		GradientDrawable qgd2 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
		upload.setBackground(qgd2);
		Button fromFile = (Button) findViewById(R.id.button2);
		GradientDrawable qgd3 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
		fromFile.setBackground(qgd3);
		Button camera = (Button) findViewById(R.id.button3);
		GradientDrawable qgd4 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
		camera.setBackground(qgd4);
		image.setImageBitmap(theImage);
		Spinner loc = (Spinner) findViewById(R.id.spinner1);
		//loc.setAdapter(ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item));
		LocationAdapter locs = new LocationAdapter(this);
		locs.setLocations(getResources().getStringArray(R.array.locations_array));
		loc.setAdapter(locs);
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
		if(!uploaded && theImage != null && !savedImage){
			theImage.recycle();
			theImage = null;
			Log.i("upload", "here");
		}
		super.onDestroy();
	}
	
	public void onConfigurationChanged(Configuration newConfig){
		savedImage = true;
		super.onConfigurationChanged(newConfig);
		
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
		thePic.setUser(MainActivity.userNym);
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
	        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        String capturedImageFilePath = cursor.getString(column_index_data);
	        if(theImage != null){
	        	theImage.recycle();
	        	theImage = null;
	        }
	        //theImage = BitmapFactory.decodeFile(capturedImageFilePath);
	        theImage = decodeSampledBitmapFromFile(capturedImageFilePath, 736, 552);
	        ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageBitmap(theImage);
			thePic.setPicture(theImage);
			
	    }
	    else if (resultCode == RESULT_OK) //if this is the picture selector activity
	    {
	        Uri chosenImageUri = data.getData();
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(chosenImageUri, projection, null, null, null);
	        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        String capturedImageFilePath = cursor.getString(column_index_data);
	        if(theImage != null){
	        	theImage.recycle();
	        	theImage = null;
	        }
	        theImage = decodeSampledBitmapFromFile(capturedImageFilePath, 736, 552);
	        ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageBitmap(theImage);
			thePic.setPicture(theImage);
	        /*
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
			*/
	    }
	    
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
			// Raw height and width of image
			int height = options.outHeight;
			int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {

				// Calculate ratios of height and width to requested height and width
				int heightRatio = Math.round((float) height / (float) reqHeight);
				int widthRatio = Math.round((float) width / (float) reqWidth);

				// Choose the smallest ratio as inSampleSize value, this will guarantee
				// a final image with both dimensions larger than or equal to the
				// requested height and width.
				inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			}

			return inSampleSize;
	}
	
	public static Bitmap decodeSampledBitmapFromFile(String fil, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(fil, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(fil, options);
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

	/*
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
	*/
	
	/**
	 * Necessary to preserve the image when flipped.
	 */
	protected void onSaveInstanceState(Bundle outState){
		outState.putParcelable("image", theImage); // These variables would otherwise be lost
		outState.putParcelable("uri", mCapturedImageURI);
		savedImage = true;
		outState.putBoolean("savedI", savedImage);
	}

}
