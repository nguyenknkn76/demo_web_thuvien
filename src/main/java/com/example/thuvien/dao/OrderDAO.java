package com.example.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.thuvien.model.Order;
import com.example.thuvien.model.User;

public class OrderDAO {
	private String jdbcURL = "jdbc:mysql://localhost/library";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	//query
	private static final String GET_ALL_ORDER = "SELECT * FROM userorder WHERE userid =? AND status = 1";
	private static final String GET_ALL_CANCEL_ORDER = "SELECT * FROM userorder WHERE userid =? AND status = 0";
	private static final String CREATE_NEW_ORDER = "INSERT INTO userorder(userid, bookid, bookname, quantity,status) VALUES(?,?,?,?,1)";
	private static final String CANCEL_ORDER = "UPDATE userorder SET status = 0 WHERE id = ?";
	
	public OrderDAO() {};
	
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
	public List<Order> getAllOrder(User user){
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ALL_ORDER);
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setUserid(rs.getInt("userid"));
				order.setBookid(rs.getInt("bookid"));
				order.setBookname(rs.getString("bookname"));
				order.setQuantity(rs.getInt("quantity"));
				order.setStatus(rs.getInt("status"));
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	public List<Order> getAllCanceledOrder(User user){
		List<Order> orders = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ALL_CANCEL_ORDER);
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setUserid(rs.getInt("userid"));
				order.setBookid(rs.getInt("bookid"));
				order.setBookname(rs.getString("bookname"));
				order.setQuantity(rs.getInt("quantity"));
				order.setStatus(rs.getInt("status"));
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	public boolean createNewOrder(Order order) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_NEW_ORDER);
			ps.setInt(1,order.getUserid());
			ps.setInt(2, order.getBookid());
			ps.setString(3, order.getBookname());
			ps.setInt(4, order.getQuantity());
			ps.execute();
			ps.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean cancelOrder (int id) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(CANCEL_ORDER);
			ps.setInt(1, id);
			ps.execute();
			ps.close();
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
