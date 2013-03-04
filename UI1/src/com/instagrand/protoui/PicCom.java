package com.instagrand.protoui;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
	
	/**
	 * Sets the Picture Item for this activity and updates all the elements accordingly.
	 * @param p
	 */
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
		coms.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	QuestionAdapter quests = (QuestionAdapter) parent.getAdapter();
	        	quests.setOpenQuestion(position);
	        	Button pushBut = (Button) findViewById(R.id.button1);
	        	if(quests.getOpenQuestion() == -1){
	        		pushBut.setText("Post");
	        	} else {
	        		pushBut.setText("Reply");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
