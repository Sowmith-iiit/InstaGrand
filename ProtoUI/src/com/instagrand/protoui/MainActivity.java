package com.instagrand.protoui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.instagrand.networking.ChatConnection;
import com.instagrand.networking.NsdHelper;

/**
 * This is the activity for the picture selector component of the instagrand app.
 * @author Jacob Iott
 * @version 1.1
 *
 */
public class MainActivity extends FragmentActivity{
	
	
	
	//The selected picture
	public static PictureItem currPicture;
	
	//The Questions for each of the picture Items
	Vector<Question> coms = new Vector<Question>();
	
	//The adapter for the images
	public ImageAdapter theImages;
	//The array of picture Items is now a vector
	private static Vector<PictureItem> mThumbIds = new Vector<PictureItem>(); 
	//The spinner
	private static Spinner spinner;

	private MainFragment mainFragment;
	
	public static String userNym;
	private String toast;
	
	private TextView mStatusView;
    private Handler mUpdateHandler;
    private boolean updated;
	private NsdHelper mNsdHelper;
	private ChatConnection mConnection;
	
	private ArrayList<PictureItem> picturesList = new ArrayList<PictureItem>();

	private ArrayList<PictureItem> myPicturesList = new ArrayList<PictureItem>();
	
	/**
	 * Begins when the activity begins.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_table);
		if (userNym == null){
			Button up = (Button) findViewById(R.id.button1);
			up.setVisibility(View.INVISIBLE);
		}
		
		theImages = new ImageAdapter(this);
		
		
		Session.openActiveSession(this, true, new Session.StatusCallback(){
			
	    	@Override
	    	public void call(Session session, SessionState state, Exception exception){
	    		Log.i("Debug","here" + state);
	    		if (session.isOpened()){
	    			Request.executeMeRequestAsync(session, new Request.GraphUserCallback(){
	    				@Override
	    				public void onCompleted(GraphUser user, Response response){
	    					if (user != null){
	    						userNym = user.getName();
	    						Log.i("user: ","" + userNym);
	    						Button up = (Button) findViewById(R.id.button1);
	    						up.setVisibility(View.VISIBLE);
	    					} else {
	    						userNym = "nope";
	    					}
	    				}
	    			});
	    		}
	    		
	    	}
	    });
	    RelativeLayout rel = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		GradientDrawable qgd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF00284D, 0xFF000000});
		rel.setBackgroundDrawable(qgd);
		spinner = (Spinner) findViewById(R.id.Spinner1);
		
		LocationDropDownAdapter locs = new LocationDropDownAdapter(this);
		locs.setLocations(getResources().getStringArray(R.array.locations_array));
		
		spinner.setAdapter(locs);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		theImages = new ImageAdapter(this);
		
		//Change items in layout to match location
		Vector<PictureItem> currentImages = new Vector<PictureItem>();
		for (int i = 0; i < mThumbIds.size(); i++){
			if (mThumbIds.get(i).getLocationName().equals(spinner.getSelectedItem())){
				currentImages.add(mThumbIds.get(i));
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
				for (int i = 0; i < mThumbIds.size(); i++){
					if (mThumbIds.get(i).getLocationName().equals(parent.getSelectedItem())){
						currentImages.add(mThumbIds.get(i));
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
	    
	 // NSD setup
	    
	    mUpdateHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	String chatLine = "";
	        	if(msg.getData().getString("msg") != null){
	        		chatLine = msg.getData().getString("msg");
	        	}
	        	if(msg.getData().getParcelableArrayList("images") != null){
	        		addPictureToList(msg.getData().getParcelableArrayList("images"));
	        	}
	        	
	        	
	            //Toast.makeText(getApplicationContext(), chatLine, Toast.LENGTH_SHORT);
	        }
	    };
	    
	    
	    picturesList = new ArrayList<PictureItem>();
	    
	    mConnection = new ChatConnection(mUpdateHandler, this);
	
	    mNsdHelper = new NsdHelper(this);
	    mNsdHelper.initializeNsd();
	    
	    if(mConnection.getLocalPort() > -1) {
	        mNsdHelper.registerService(mConnection.getLocalPort());
	    } else {
	        Log.d("", "ServerSocket isn't bound.");
	    }
	    
	    mNsdHelper.discoverServices();
	    
		    
		}
	
    public void clickConnect(View v) {
        NsdServiceInfo service = mNsdHelper.getChosenServiceInfo();
        Log.d("Connecting", "Attempting to connect");
        if (service != null) {
            
            String info = mConnection.connectToServer(service.getHost(), service.getPort());
            System.out.println("Updated value is: " + updated);
	        updated = true;
	        mConnection.update();
        } else {
            Log.d("Failed", "No service to connect to!");
        }
        if(toast != null){
        	Toast.makeText(getApplicationContext(), "Count is: " + mNsdHelper.getCount() + "\nInfo is: " + toast.split(",,,")[0] + "\nIP: " + toast.split(",,,")[1], Toast.LENGTH_SHORT).show();
        	
        }
	}
	
	/**
	 * Adds an image to the vector of images 
	 * @param p
	 */
	public void addImage(PictureItem p){
		mThumbIds.add(p);
		Vector<PictureItem> currentImages = new Vector<PictureItem>();
		for (int i = 0; i < mThumbIds.size(); i++){
			if (mThumbIds.get(i).getLocationName().equals(spinner.getSelectedItem())){
				currentImages.add(mThumbIds.get(i));
			}
		}
		theImages.setImages((PictureItem[]) currentImages.toArray(new PictureItem[currentImages.size()]));
		theImages.notifyDataSetChanged();
	}
	
	
	
	public void assembleToast(Message message){
		this.toast = message.getData().getString("msg");
		message.getData().getParcelableArrayList("images");
	}
	
	/**
	 * Starts the upload picture activity
	 * @param v
	 */
	public void uploadPic(View v){
		if (userNym != null){
			Intent intent = new Intent(MainActivity.this, Upload.class);
			Bundle b = new Bundle();
			b.putParcelableArrayList("pictureList", myPicturesList);
			intent.putExtras(b);
			startActivityForResult(intent, 1, b);
		}
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  if(data.getParcelableArrayListExtra("myPictures") != null){
		  myPicturesList = data.getParcelableArrayListExtra("myPictures");
		  picturesList.addAll(myPicturesList);
	  }
	}

	public void addPictureToList(ArrayList<Parcelable> imageList){
		
		//Make sure we don't get duplicates, does work!
		if(picturesList.size() == 0){
			for(int i = 0; i < imageList.size(); i++){
				picturesList.add((PictureItem) imageList.get(i));
				addImage((PictureItem) imageList.get(i));
			}
		}
			
		if(imageList != null){
			for(int j = 0; j < picturesList.size(); j++){
				Boolean duplicate = false;
				for(int i = 0; i < imageList.size(); i++){
					if(picturesList.get(j).getId() == ((PictureItem) imageList.get(i)).getId())
						duplicate = true;
					if(!duplicate && i == imageList.size()-1){
						addImage((PictureItem) imageList.get(i));
						picturesList.add((PictureItem) imageList.get(i));
					}
				}
			}
		}
	}
	
	
	public ArrayList<PictureItem> getPicturesList() {
		return picturesList;
	}

	public void setPicturesList(ArrayList<PictureItem> picturesList) {
		this.picturesList = picturesList;
	}
	
	public void addToPicturesList(ArrayList<PictureItem> picturesList){
		
		if(this.picturesList.size() == 0){
			for(int i = 0; i < picturesList.size(); i++){
				picturesList.add((PictureItem) picturesList.get(i));
				addImage((PictureItem) picturesList.get(i));
			}
		}
		
		for(int i = 0; i < this.picturesList.size(); i++){
			Boolean duplicate = false;
			for(int j = 0; j < picturesList.size(); j++){
				if(this.picturesList.get(i).getId() == ((PictureItem) picturesList.get(j)).getId())
					duplicate = true;
				if(!duplicate && j == picturesList.size() - 1)
					this.picturesList.add(picturesList.get(j));
			}
		}
	}
	
	public ArrayList<PictureItem> getMyPicturesList() {
		return myPicturesList;
	}

	public void setMyPicturesList(ArrayList<PictureItem> myPicturesList) {
		this.myPicturesList = myPicturesList;
	}

	public void updateMyImagesToSend(PictureItem thePic) {
		myPicturesList.add(thePic);
		mConnection.updateImages(myPicturesList);
		
	}

}
