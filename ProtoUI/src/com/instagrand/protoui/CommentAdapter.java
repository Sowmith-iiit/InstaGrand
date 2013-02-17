package com.instagrand.protoui;

import java.util.Vector;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{
		
		private Context mContext;
		private Vector<Comment> comments;
		
		public CommentAdapter(Context c){
			mContext = c;
		}
		
		public void setComments(Vector<Comment> c){
			comments = c;
		}

		//private File theImage = new File("/sdcard/sample_0.jpg");
		/**
		private PictureItem[] mThumbIds = {
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_0.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_1.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_2.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_3.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","Also, a cat!", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_4.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_5.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_6.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
			new PictureItem("A Dog","What else do you want to know?", "Bill", BitmapFactory.decodeFile(new File("/sdcard/sample_7.jpg").getAbsolutePath()), "Fieldhouse", "1 Campus Dr."),
		};
		*/
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments.size();
		}
		
		

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return comments.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView text;
			if(convertView == null) {
				text = new TextView(mContext);
				//text.setLayoutParams(new ListView.LayoutParams(85, 85));
				
				text.setPadding(2, 2, 2, 2);
			} else {
				text = (TextView) convertView;
			}
			Comment theCom = comments.get(comments.size() - 1 - position);
			text.setText(theCom.getUser() + " - "+ theCom.getDate().getMonth() +"/" + theCom.getDate().getDate() + "/" + theCom.getDate().getYear() + "\n" + theCom.getComment());
			return text;
		}
		

}
