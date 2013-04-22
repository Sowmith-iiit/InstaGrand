package com.instagrand.protoui;


import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * The Activity for viewing a picture and its comments
 * @author Jacob Iott
 *
 */
public class PicCom extends Activity {

	//The PictureItem
	private PictureItem pic;
	//The Adapter for the ListView of Questions associated with the picture
	private QuestionAdapter theQuests;
	
	//True if image is taking up th entire screen
	private boolean imageOpen = false;
	
	/**
	 * Sets the Picture Item for this activity and updates all the elements accordingly.
	 * @param p
	 */
	public void setPic(PictureItem p){
		pic = p;
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		image.setImageBitmap(pic.getPicture());
		image.setOnClickListener(new OnClickListener() {
			//Toggle whether the image gets the entire screen or not.
			@Override
			public void onClick(View v) {
				RelativeLayout relLay1 = (RelativeLayout) findViewById(R.id.RelativeLayout1);
				RelativeLayout relLay2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
				if (!imageOpen){ //The image is not already taking up the entire screen
					if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){ //In Landscape
						relLay1.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2f));
						relLay2.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 0f));
						
					} else { //In Portrait
						relLay1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 2f));
						relLay2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 0f));
					}
					imageOpen = true;
				} else { //The image is already taking up the entire screen
					if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){//In Landscape
						relLay1.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
						relLay2.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
						
					} else {//In Portrait
						relLay1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
						relLay2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
					}
					
					imageOpen = false;
				}
				
			}
			
		});
		TextView title = (TextView) findViewById(R.id.textView1);
		title.setText(pic.getTitle());
		TextView user = (TextView) findViewById(R.id.textView3);
		user.setText("By " + pic.getUser());
		ListView coms = (ListView) findViewById(R.id.listView1);
		TextView des = new TextView(this);
		des.setText(pic.getDescription());
		des.setTextSize(21);
		des.setTextColor(0xFFFFFFFF);
		coms.addHeaderView(des);
		coms.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            if (position != 0){
	        		//QuestionAdapter quests = (QuestionAdapter) parent.getAdapter();
	        		theQuests.setOpenQuestion(position-1);
	        		Button pushBut = (Button) findViewById(R.id.button1);
	        		if(theQuests.getOpenQuestion() == -1){
	        			pushBut.setText("Post");
	        		} else {
	        			pushBut.setText("Reply");
	        		}
	        	}
	        }
	    });
		theQuests = new QuestionAdapter(this);
		theQuests.setQuestions(pic.getQuestions());
		coms.setAdapter(theQuests);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	/**
	 * Run when the Activity begins.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout rel = (RelativeLayout) findViewById(R.id.theRel);
		GradientDrawable qgd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF00284D, 0xFF000000});
		rel.setBackgroundDrawable(qgd);
		Button post = (Button) findViewById(R.id.button1);
		GradientDrawable qgd2 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
		post.setBackground(qgd2);
		if (MainActivity.userNym == null){
			post.setVisibility(View.INVISIBLE);
			post.setHeight(0);
			EditText et = (EditText) findViewById(R.id.editText1);
			et.setVisibility(View.INVISIBLE);
			et.setHeight(0);
		} else {
			post.setVisibility(View.VISIBLE);
			//LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
			//post.setLayoutParams(params1);
			EditText et = (EditText) findViewById(R.id.editText1);
			et.setVisibility(View.VISIBLE);
			//LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
			//et.setLayoutParams(params2);
			
		}
		
		//The Code below is for retrieving the PictureItem to be viewed from a Parcel
		//Bundle b = this.getIntent().getExtras();
		//PictureItem p = null;
		//if (b != null){
			//p = b.getParcelable("picItem");
		//}
		//setPic(p);
		
		//The code below is for retrieving the PictureItem directly from a static variable
		setPic(MainActivity.currPicture);
		
	}

	/**
	 * Post a new Comment. Called by the Post button on the menu
	 * @param v
	 */
	public void postComment(View v){
		if (MainActivity.userNym != null){
			EditText newCom = (EditText) findViewById(R.id.editText1);
			if (!newCom.getText().toString().trim().equals("")){ //If there is text in the enter comment section
				if (theQuests.getOpenQuestion() == -1){ //If there are no open questions, enter the new comment as a new question
					pic.addQuestion("Anonymous", newCom.getText().toString());
				} else { //Otherwise, add it as an answer to the currently open question
					pic.getQuestions().get(pic.getQuestions().size() - theQuests.getOpenQuestion() - 1).addAnswer("Anonymous", newCom.getText().toString());
				}
			}
			newCom.setText("");
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //Gets rid of the keyboard
			newCom.clearFocus();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
