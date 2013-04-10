package com.instagrand.protoui;

import com.facebook.*;
import com.facebook.model.*;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class Starter extends FragmentActivity {
	
	private String userNym;
	private MainFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		Session.openActiveSession(this, true, new Session.StatusCallback(){
	    	@Override
	    	public void call(Session session, SessionState state, Exception exception){
	    		Log.i("Debug","here");
	    		if (session.isOpened()){
	    			Request.executeMeRequestAsync(session, new Request.GraphUserCallback(){
	    				@Override
	    				public void onCompleted(GraphUser user, Response response){
	    					if (user != null){
	    						userNym = user.getName();
	    						TextView welcome = (TextView) findViewById(R.id.welcome);
	    						welcome.setText("Hello " + user.getName() + "!");
	    						Log.i("user: ","" + userNym);
	    					} else {
	    						userNym = "nope";
	    					}
	    				}
	    			});
	    		}
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
