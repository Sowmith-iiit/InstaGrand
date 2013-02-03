package com.instagrand.protoui;

import java.util.Date;

/**
 * Class for storing individual user comments about the pictures
 * @author Jacob Iott
 * @version 1.0
 */
public class Comment {
  private String user;
	private String comment;
	private Date date;
	
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
		date = new Date();
	}
	
	/**
	 * Returns the author's name
	 * @return the user name
	 */
	public String getUser(){
		return user;
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
}
