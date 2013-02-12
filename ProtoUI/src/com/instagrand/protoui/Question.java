package com.instagrand.protoui;

import java.util.Date;
import java.util.Vector;

/**
 * Extension of the comment class that allows the comments to contain replies.
 * @author Jacob Iott
 * @author 1.0
 */
public class Question extends Comment {

	private Vector<Comment> answers;
	
	/**
	 * Constructor for new Questions
	 * @param u the author's username
	 * @param c The question
	 */
	public Question(String u, String c) {
		super(u, c);
		answers = new Vector<Comment>();
	}
	
	/**
	 * Constructor for Question's without answers
	 * @param u the author's username
	 * @param c The question
	 * @param d the date the question was posted
	 */
	public Question(String u, String c, Date d) {
		super(u, c, d);
		answers = new Vector<Comment>();
	}
	
	/**
	 * Constructor for Questions with answers
	 * @param u the author's username
	 * @param c The question
	 * @param d the date the question was posted
	 * @param a the list of answers
	 */
	public Question(String u, String c, Date d, Vector<Comment> a) {
		super(u, c, d);
		answers = a;
	}
	
	/**
	 * Adds a new answer to the list
	 * @param a the new answer
	 */
	public void addAnswer(Comment a){
		answers.add(a);
	}
	
	/**
	 * Returns the list of answers
	 * @return the answers
	 */
	public Vector<Comment> getAnswers(){
		return answers;
	}

}
