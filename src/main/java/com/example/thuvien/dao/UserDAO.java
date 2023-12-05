package com.example.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.spi.DirStateFactory.Result;

import com.example.thuvien.model.User;

public class UserDAO {
	//	login info
	private String jdbcURL = "jdbc:mysql://localhost/library";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	//query
	private static final String CHECK_LOGIN_QUERY = "SELECT *FROM user WHERE username = ? AND password = ?";
	private static final String CHECK_USER_EXIST = "SELECT*FROM user WHERE username =?";
	private static final String REGISTER_NEW_USER = "INSERT INTO user(name, email, username, password, role) VALUES (?,?,?,?,'user');";
	private static final String UPDATE_AN_USER = "UPDATE user SET  name = ?, username = ?, password = ?, email = ?, role = ? WHERE id = ?";
	public UserDAO() {}

	//connection
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername, jdbcPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	//function
	public User checkLogin(String username, String password) {
		User user = new User();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(CHECK_LOGIN_QUERY);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public boolean checkExist(String username) {
		boolean result = false;
		try {
			Connection connection =getConnection();
			PreparedStatement ps = connection.prepareStatement(CHECK_USER_EXIST);
			ps.setString(1, username);
			ResultSet rs = null;
			rs = ps.executeQuery();
			if(rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean registerUser(User user) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(REGISTER_NEW_USER);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getUsername());
			ps.setString(4,	user.getPassword());
			ps.execute();
			ps.close();
			result =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean updateUser(User user) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_AN_USER);
//			"UPDATE book SET  title = ?, author = ?, description = ?, publishdate =?, numberofpage = ?, cover = ?, category = ? WHERE id = ?";
			ps.setString(1, user.getName());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getId());
			ps.execute();
			ps.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
}
