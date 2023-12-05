package com.example.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.stereotype.Controller;

import com.example.thuvien.model.Book;
public class BookDAO {
	//login info
	private String jdbcURL = "jdbc:mysql://localhost/library";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	//query
	private static final String GET_All_BOOK = "SELECT *FROM book";
	private static final String GET_A_BOOK_BY_ID = "SELECT *FROM book WHERE ID = ?";
	private static final String UPDATE_A_BOOK = "UPDATE book SET  title = ?, author = ?, description = ?, publishdate = ?, numberofpage = ?, cover = ?, category = ? WHERE id = ?";
	private static final String CHECK_BOOK_EXISTED = "SELECT *FROM book WHERE title = ? AND author =?";
	private static final String ADD_A_BOOK = "insert into book(title, author, category, publishdate,  numberofpage, sold,description, cover) values (?,?,?,?,?,0,?,?);";
	private static final String DELETE_A_BOOK = "DELETE FROM book WHERE id =?";
	public BookDAO() {}
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
	public List<Book> getAllBooks(){
		List<Book> books = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_All_BOOK);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setCover(rs.getString("cover"));
				book.setDescription(rs.getString("description"));
				book.setId(rs.getInt("id"));
				book.setNumberofpage(rs.getInt("numberofpage"));
				book.setPublishdate(rs.getDate("publishdate"));
				book.setSold(rs.getInt("sold"));
				book.setTitle(rs.getString("title"));
				books.add(book);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public Book getABookById(int id) {
		Book book = new Book();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_A_BOOK_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category"));
				book.setCover(rs.getString("cover"));
				book.setDescription(rs.getString("description"));
				book.setId(rs.getInt("id"));
				book.setNumberofpage(rs.getInt("numberofpage"));
				book.setPublishdate(rs.getDate("publishdate"));
				book.setSold(rs.getInt("sold"));
				book.setTitle(rs.getString("title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public boolean updateABook(Book book) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_A_BOOK);
//			"UPDATE book SET  title = ?, author = ?, description = ?, publishdate =?, numberofpage = ?, cover = ?, category = ? WHERE id = ?";
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setDate(4, (java.sql.Date) book.getPublishdate());
			ps.setInt(5, book.getNumberofpage());
			ps.setString(6, book.getCover());
			ps.setString(7, book.getCategory());
			ps.setInt(8, book.getId());
			ps.execute();
			ps.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean checkExisted(Book book) {
		boolean notExisted = true;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(CHECK_BOOK_EXISTED);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				notExisted = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notExisted;
	}
	
	public boolean addABook(Book book) {
		boolean result = false;
		try {
//			private static final String ADD_A_BOOK = "insert into book(title, author, category, publishdate,  numberofpage, sold,description, cover) values (?,?,?,?,?,0,?,?);";

			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(ADD_A_BOOK);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getCategory());
			ps.setDate(4, (java.sql.Date) book.getPublishdate());
			ps.setInt(5, book.getNumberofpage());
			ps.setString(6, book.getDescription());
			ps.setString(7, book.getCover());
			ps.execute();
			ps.close();
			result = true;

			
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;
	}
	public boolean deleteABook(int id) {
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_A_BOOK);
			ps.setInt(1, Integer.valueOf(id));
			ps.execute();
			ps.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
