package com.example.thuvien.model;

public class Rating {
	private int id;
	private int userid;
	private String username;
	private int bookid;
	private int rating;
	private String comment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Rating(int id, int userid, String username, int bookid, int rating, String comment) {
		this.id = id;
		this.userid = userid;
		this.username = username;
		this.bookid = bookid;
		this.rating = rating;
		this.comment = comment;
	}
	public Rating() {
		
	}
	
}
