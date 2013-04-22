package com.instagrand.protoui;

import java.util.Vector;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter for a ListView with Comments
 * @author Jacob Iott
 * @version 1.0
 *
 */
public class CommentAdapter extends BaseAdapter{
		
		//The Context
		private Context mContext;
		
		//The Comments
		private Vector<Comment> comments;
		
		/**Default Constructor
		 * 
		 * @param c
		 */
		public CommentAdapter(Context c){
			mContext = c;
		}
		
		/**
		 * Set the comments
		 */
		public void setComments(Vector<Comment> c){
			comments = c;
		}
		
		/**
		 * Returns the number of comments
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments.size();
		}
		
		/**
		 * Returns the comment at the given index
		 */
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return comments.get(arg0);
		}

		/**
		 * Created from implementing BaseAdapter
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		/**
		 * Returns the view at the specified position. Used by the ListView to get the view for each position
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView text;
			if(convertView == null) {
				text = new TextView(mContext);
				
				
				text.setPadding(2, 2, 2, 2);
			} else {
				text = new TextView(mContext);
				
				text.setPadding(2, 2, 2, 2);
			}
			Comment theCom = comments.get(comments.size() - 1 - position);
			text.setText(theCom.getUser() + " - "+ theCom.getDate().getMonth() +"/" + theCom.getDate().getDate() + "/" + theCom.getDate().getYear() + "\n" + theCom.getComment());
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			text.setLayoutParams(params);
			return text;
		}
}
