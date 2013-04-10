package com.instagrand.protoui;

import java.util.Vector;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Adapter to contain the questions in the PicCom activity.
 * @author Jacob Iott
 *
 */
public class QuestionAdapter extends BaseAdapter{
		
		//The Context
		private Context mContext;
		private final float scale;
		
		//List of Questions
		private Vector<Question> questions;
		
		//The Currently Selected Question (-1 means no question selected)
		private int openQuestion = -1;
		
		/**
		 * Creates a new QuestionAdapter
		 * @param c
		 */
		public QuestionAdapter(Context c){
			mContext = c;
			scale = c.getResources().getDisplayMetrics().density;
			
		}
		
		/**
		 * Sets the list of questions
		 * @param q
		 */
		public void setQuestions(Vector<Question> q){
			questions = q;
		}
		
		/**
		 * Returns the number of questions
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return questions.size();
		}
		
		/**
		 * Returns the question at the given position
		 */
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return questions.get(arg0);
		}

		/**
		 * Created by Implementing BaseAdapter
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		/**
		 * Returns the view at the given position. Used by the ListView to add the views to the various positions
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView text;
			text = new TextView(mContext);
			text.setPadding(2, 2, 2, 2);
			Question theCom = questions.get(questions.size() - 1 - position);
			text.setText(theCom.getUser() + " - "+ (theCom.getDate().getMonth() + 1)  +"/" + theCom.getDate().getDate() + "/" + theCom.getDate().getYear() + "\n" + theCom.getComment());
			LinearLayout.LayoutParams items = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			text.setLayoutParams(items);
			GradientDrawable qgd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
			qgd.setCornerRadius(0f);
			
			text.setBackgroundDrawable(qgd);
			LinearLayout ans = new LinearLayout(mContext);
			ans.setOrientation(LinearLayout.VERTICAL);
			ans.addView(text, 0);
			text.setTextSize(21);
			if (openQuestion == position){
				//GradientDrawable qgd2 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF98BEE0, 0xFF418ACD});
				GradientDrawable qgd2 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
				text.setBackgroundDrawable(qgd2);
				text.setTextColor(0xFFFFFFFF);
				if (theCom.getAnswers().size() > 0){
					
					int ind = 0;
					for(int i = 0; i <theCom.getAnswers().size(); i++){
						if (i > 0){ //Add a line between the items in the form of a view
							View theLine = new View(mContext);
							int pixels = (int) (1 * scale + 0.5f);
							LinearLayout.LayoutParams linPar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
							theLine.setLayoutParams(linPar);
							theLine.setBackgroundColor(Color.parseColor("#00284D"));
							ind++;
							ans.addView(theLine, ind);//ind++ or 1
							
						}
						TextView anAns = new TextView(mContext);
						anAns.setText(theCom.getAnswers().get(i).getUser() + " - "+ (theCom.getAnswers().get(i).getDate().getMonth() + 1)  +"/" + theCom.getAnswers().get(i).getDate().getDate() + "/" + theCom.getAnswers().get(i).getDate().getYear() + "\n" + theCom.getAnswers().get(i).getComment());
						LinearLayout.LayoutParams ansPar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
						ansPar.setMargins(30, 0, 0, 0);
						anAns.setLayoutParams(ansPar);
						ind++;
						anAns.setTextSize(21);
						//GradientDrawable qgd3 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF98BEE0, 0xFF418ACD});
						//GradientDrawable qgd3 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
						anAns.setTextColor(0xFFFFFFFF);
						//anAns.setBackgroundDrawable(qgd3);
						ans.addView(anAns, ind); //ind or 1
					}
				} else {
					TextView anAns = new TextView(mContext);
					anAns.setText("no answers");
					LinearLayout.LayoutParams ansPar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					ansPar.setMargins(30, 0, 0, 0);
					anAns.setLayoutParams(ansPar);
					anAns.setTextSize(21);
					anAns.setTextColor(0xFFFFFFFF);
					//GradientDrawable qgd3 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF004684, 0xFF00284D});
					//anAns.setBackgroundDrawable(qgd3);
					ans.addView(anAns); //ind or 1
				}
			}
			else if (openQuestion == -1){
				
				text.setTextColor(0xFFFFFFFF);
			} else {
				GradientDrawable qgd4 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0x08004684, 0x8000284D});
				text.setBackgroundDrawable(qgd4);
				text.setTextColor(0x80FFFFFF);
			}
			
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			ans.setLayoutParams(params);
			return ans;
		}

		/**
		 * Sets the currently open question
		 * @param index
		 */
		public void setOpenQuestion(int index) {
			// TODO Auto-generated method stub
			if (openQuestion != index){
				openQuestion = index;
				
			} else {
				openQuestion = -1;
			}
			this.notifyDataSetChanged();
		}
		
		/**
		 * Gets the currently open question
		 * @return
		 */
		public int getOpenQuestion(){
			return openQuestion;
		}
		

}
