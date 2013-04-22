package com.instagrand.protoui;

import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for storing individual user comments about the pictures
 * @author Jacob Iott
 * @version 1.0
 */
public class Comment implements Parcelable{
	private String user;
	private String comment;
	private Date date;
	
	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
		public Comment createFromParcel(Parcel in) {
			return new Comment(in);
		}

		public Comment[] newArray(int size) {
				return new Comment[size];
		}
	};
		
	/**
	 * Create Parcel from instance
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(user);
		dest.writeString(comment);
		dest.writeInt(date.getMonth());
		dest.writeInt(date.getDate());
		dest.writeInt(date.getYear());
		
	}
	
	/**
	 * Date given Comment Constructor
	 * @param u The author's name
	 * @param c The comment
	 * @param d The date on which the comment was made
	 */
	public Comment(String u, String c, Date d){
		user = u;
		comment = c;
		date = d;
	}
	
	/**
	 * Comment Constructor for new comments (date is set to time comment was created)
	 * @param u The author's name
	 * @param c The comment
	 */
	public Comment(String u, String c){
		user = u;
		comment = c;
		Calendar cal = Calendar.getInstance();
		date = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
	}
	
	/**
	 * Constructor for creating comments from parcels
	 * @param in
	 */
	public Comment(Parcel in) {
		user = in.readString();
		comment = in.readString();
		int month = in.readInt();
		int day = in.readInt();
		int year = in.readInt();
		date = new Date(year, month, day);
	}

	/**
	 * Returns the author's user name
	 * @return the user name
	 */
	public String getUser(){
		return user;
	}
	
	/**
	 * Sets the author's user name
	 * @param u
	 */
	public void setUser(String u){
		user = u;
	}
	
	/**
	 * Sets the comment
	 */
	public void setComment(String c){
		comment = c;
	}
	
	/**
	 * Returns the comment
	 * @return the comment
	 */
	public String getComment(){
		return comment;
	}
	
	/**
	 * Returns the date on which the comment was made
	 * @return the date
	 */
	public Date getDate(){
		return date;
	}
	
	/**
	 * Sets the date of the comment
	 * @param m The month
	 * @param d The day
	 * @param y The year
	 */
	public void setDate(int m, int d, int y){
		date = new Date(m,d,y);
	}

	/**
	 * Created from implementing ParcelAble
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
