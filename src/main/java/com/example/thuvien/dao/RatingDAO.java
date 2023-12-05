package com.example.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.example.thuvien.model.Rating;

public class RatingDAO {
	private String jdbcURL = "jdbc:mysql://localhost/library";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	//query
	private static final String GET_RATING_OF_A_BOOK = "SELECT * FROM rating WHERE bookid =?";
	private static final String GET_RATING_OF_A_USER_FOR_A_BOOK = "SELECT* FROM rating WHERE bookid = ? AND userid =?";
	private static final String NEW_RATING = "INSERT INTO rating(userid, username, bookid, rating, comment) VALUES (?,?,?,?,?)";
	private static final String UPDATE_RATING ="UPDATE rating SET rating =? , comment = ? WHERE id = ?";
	public RatingDAO(){};
	
	//connection
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//function
	public List<Rating> getRatingOfABook(int id){
		List<Rating> ratings = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_RATING_OF_A_BOOK);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Rating rating = new Rating();
				rating.setBookid(id);
				rating.setComment(rs.getString("comment"));
				rating.setId(rs.getInt("rating"));
				rating.setRating(rs.getInt("rating"));
				rating.setUserid(rs.getInt("userid"));
				rating.setUsername(rs.getString("username"));
				ratings.add(rating);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratings;
	}
	
	public int getRatingOfAUserForABook(int bookid, int userid) {
		int result = -1;
		try {
			 Connection connection = getConnection();
			 PreparedStatement ps = connection.prepareStatement(GET_RATING_OF_A_USER_FOR_A_BOOK);
			 ps.setInt(1, bookid);
			 ps.setInt(2, userid);
			 ResultSet rs = ps.executeQuery();
			 if(rs.next()){
				 result = rs.getInt("id");
				 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean newRating(Rating rating) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(NEW_RATING);
			ps.setInt(1, rating.getUserid());
			ps.setString(2, rating.getUsername());
			ps.setInt(3, rating.getBookid());
			ps.setInt(4, rating.getRating());
			ps.setString(5, rating.getComment());
			ps.execute();
			ps.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateRating(Rating rating) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_RATING);
			ps.setInt(1, rating.getRating());
			ps.setString(2, rating.getComment());
			ps.setInt(3, rating.getId());
			ps.execute();
			ps.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
