package com.instagrand.protoui;

import java.util.Date;
import java.util.Vector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Extension of the comment class that allows the comments to contain replies.
 * @author Jacob Iott
 * @author 1.0
 */
public class Question extends Comment implements Parcelable{

	
	
	private Vector<Comment> answers;
	
	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
		public Comment createFromParcel(Parcel in) {
			return new Comment(in);
		}

		public Comment[] newArray(int size) {
				return new Comment[size];
		}
	};
	
	
	
	/**
	 * Constructor for new Questions
	 * @param u the author's username
	 * @param c The question
	 */
	public Question(String u, String c) {
		super(u, c);
		answers = new Vector<Comment>();
	}
	
	public Question(Parcel in){
		super("new", "new");
		String u = in.readString();
		String c = in.readString();
		setUser(u);
		setUser(c);
		int month = in.readInt();
		int day = in.readInt();
		int year = in.readInt();
		setDate(year, month, day);
		int coms = in.readInt();
		answers = new Vector<Comment>();
		while(coms > 0){
			answers.add((Comment) in.readParcelable(getClass().getClassLoader()));
			coms--;
		}
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
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getUser());
		dest.writeString(getComment());
		dest.writeInt(getDate().getMonth());
		dest.writeInt(getDate().getDate());
		dest.writeInt(getDate().getYear());
		dest.writeInt(answers.size());
		for(int i = 0; i < answers.size(); i++){
			dest.writeParcelable(answers.get(i), flags);
		}
	}
	
	
	/**
	 * Returns the list of answers
	 * @return the answers
	 */
	public Vector<Comment> getAnswers(){
		return answers;
	}

}
