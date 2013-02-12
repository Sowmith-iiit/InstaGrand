package com.instagrand.protoui;

import java.util.Vector;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for Storing information on individual pictures
 * It can handle either comments or questions, depending on how we want to implement that feature
 * @author Jacob Iott
 * @version 1.0
 */

public class PictureItem implements Parcelable {
	private String user;
	private Bitmap picture;
	private Vector<Comment> comments;
	private Vector<Question> questions;
	private String locName;
	private String location;
	private String description;
	private String title;
	
	/**
	 * Constructor for New Picture
	 * @param u The Picture-Taker/Uploder's User name
	 * @param bm the picture in bitmap form
	 * @param ln The name of the location in which the picture was taken
	 * @param coords The String given to Google maps to find this picture's location
	 */
	public PictureItem(String t, String d, String u, Bitmap bm, String ln, String coords){
		title = t;
		description = d;
		user = u;
		picture = bm;
		comments = new Vector<Comment>();
		questions = new Vector<Question>();
		locName = ln;
		location = coords;
	}
	
	/**
	 * Constructor for Picture with comments
	 * @param u The Picture-Taker/Uploder's User name
	 * @param bm the picture in bitmap form
	 * @param coms A vector full of comments about the picture
	 * @param ln The name of the location in which the picture was taken
	 * @param coords The String given to Google maps to find this picture's location
	 */
	public PictureItem(String t, String d, String u, Bitmap bm, Vector<Comment> coms, String ln, String coords){
		title = t;
		description = d;
		user = u;
		picture = bm;
		comments = coms;
		questions = new Vector<Question>();
		locName = ln;
		location = coords;
	}
	
	/**
	 * Constructor for Picture with questions
	 * @param u The Picture-Taker/Uploder's User name
	 * @param bm the picture in bitmap form
	 * @param questions A vector full of questions about the picture
	 * @param ln The name of the location in which the picture was taken
	 * @param coords The String given to Google maps to find this picture's location
	 */
	public PictureItem(String t, String d, String u, Vector<Question> quest, Bitmap bm,  String ln, String coords){
		title = t;
		description = d;
		user = u;
		picture = bm;
		questions = quest;
		comments = new Vector<Comment>();
		locName = ln;
		location = coords;
	}
	
	/**
	 * Adds a new comment
	 * @param c the new comment
	 */
	public void addComment(Comment c){
		comments.add(c);
	}
	
	/**
	 * Adds a new question
	 * @param q the new question
	 */
	public void addQuestion(Question q){
		questions.add(q);
	}
	
	/**
	 * Returns the comments about this picture
	 * @return
	 */
	public Vector<Comment> getComments(){
		return comments;
	}
	
	/**
	 * Returns the questions asked about this picture
	 * @return
	 */
	public Vector<Question> getQuestions(){
		return questions;
	}
	/**
	 * Returns the picture
	 * @return the picture
	 */
	public Bitmap getPicture(){
		return picture;
	}
	
	/**
	 * Returns the name of the Location in which the picture was taken
	 * @return
	 */
	public String getLocationName(){
		return locName;
	}
	
	/**
	 * Returns the location of the picture on Google Maps
	 * @return
	 */
	public String getMapLocation(){
		return location;
	}
	
	/**
	 * Returns the Username of the picture's taker/uploader
	 * @return
	 */
	public String getUser(){
		return user;
	}
	
	/**
	 * Returns the description of the picture
	 * @return 
	 */
	public String getDescription(){
		return description;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String titl) {
		title = titl;
	}
	
	public void setDescription(String desc){
		description = desc;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
