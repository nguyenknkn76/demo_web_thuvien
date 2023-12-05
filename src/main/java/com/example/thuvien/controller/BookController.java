package com.example.thuvien.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.thuvien.RandomStringGenerator;
import com.example.thuvien.dao.BookDAO;
import com.example.thuvien.dao.RatingDAO;
import com.example.thuvien.model.Book;
import com.example.thuvien.model.Rating;
import com.example.thuvien.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookController {
	private BookDAO bookDAO =  new BookDAO();
	private RatingDAO ratingDAO = new RatingDAO();
	private static String UPLOAD_DIR = "src/main/resources/static/uploads/";
	private RandomStringGenerator stringGenerator = new RandomStringGenerator();
	@GetMapping("/admin")
	public String getAdminPage(Model model, HttpSession httpSession) {
		List<Book> books = bookDAO.getAllBooks();
		model.addAttribute("books", books);
		boolean isAdminLogin = false;
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("admin")) {
			isAdminLogin = true;
			model.addAttribute("loggedIn", isAdminLogin);
			model.addAttribute("user", user);
			return "admin";
		}else {
			model.addAttribute("loggedIn", isAdminLogin);
			return "admin";
		}
	}
	@GetMapping("/admin/view/{id}")
	public String getABookAdmin(Model model, HttpSession httpSession, @PathVariable String id) {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("admin")) {
			Book book = bookDAO.getABookById(Integer.valueOf(id));
			model.addAttribute("book", book);
			return "editbook";
		}
		return "error";
	}
	@PostMapping("/admin/save/{id}")
	public String saveABookAdmin(Model model, HttpSession httpSession, @PathVariable String id,
			@RequestParam("title") String title,
			@RequestParam("author") String author,
			@RequestParam("category") String category,
			@RequestParam("description") String description,
			@RequestParam("publishdate") String publishdate,
			@RequestParam("numberofpage") String numberofpage,
			@RequestParam("cover") MultipartFile cover
			) throws IOException {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("admin")) {
			Book book = new Book();
			book.setId(Integer.valueOf(id));
			book.setTitle(title);
			book.setCategory(category);
			book.setAuthor(author);
			book.setDescription(description);
			book.setPublishdate(Date.valueOf(publishdate));
			book.setNumberofpage(Integer.valueOf(numberofpage));
			if(!cover.isEmpty()) {
				File uploadDir = new File(UPLOAD_DIR);
				if(!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				byte[] bytes = cover.getBytes();
				String newFileName = stringGenerator.gen() + "." + stringGenerator.getFileExtension(cover.getOriginalFilename());
//				Path path = Paths.get(UPLOAD_DIR + cover.getOriginalFilename());
				Path path = Paths.get(UPLOAD_DIR + newFileName);
				Files.write(path, bytes);
//				book.setCover(cover.getOriginalFilename());
				book.setCover(newFileName);
			}
			boolean success = bookDAO.updateABook(book);
			if(success) {
				return "redirect:/admin";
			}
		}
		return "error";
		
	}
	// add new book 
	@GetMapping("/admin/new")
	public String getNewBookPageAdmin(Model model, HttpSession httpSession) {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("admin")) {
			return "newbook";
		}
		return "error";
	}
	
	@PostMapping("/admin/new")
	public String saveABookAdmin(Model model, HttpSession httpSession, 
			@RequestParam("title") String title,
			@RequestParam("author") String author,
			@RequestParam("category") String category,
			@RequestParam("description") String description,
			@RequestParam("publishdate") String publishdate,
			@RequestParam("numberofpage") String numberofpage,
			@RequestParam("cover") MultipartFile cover
			) throws IOException {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("admin")) {
			Book book = new Book();
			book.setTitle(title);
			book.setCategory(category);
			book.setAuthor(author);
			book.setDescription(description);
			book.setPublishdate(Date.valueOf(publishdate));
			book.setNumberofpage(Integer.valueOf(numberofpage));
			if(!cover.isEmpty()) {
				File uploadDir = new File(UPLOAD_DIR);
				if(!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				byte[] bytes = cover.getBytes();
				String newFileName = stringGenerator.gen() + "." + stringGenerator.getFileExtension(cover.getOriginalFilename());
				Path path = Paths.get(UPLOAD_DIR + newFileName);
				Files.write(path, bytes);
				book.setCover(newFileName);
			}
			if(bookDAO.checkExisted(book)){
				boolean result = bookDAO.addABook(book);
				if(result) {
					return  "redirect:/admin";
				}
			}else {
				return"bookexisted";
			}
		}
		return "error";
	}
	@PostMapping("/admin/delete")
	public String deleteABook(Model model,HttpSession httpSession, @RequestParam("id") String id) {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("admin")) {
			boolean success = bookDAO.deleteABook(Integer.valueOf(id));
			if(success) {
				return "redirect:/admin";
			}
		}
		return "error";
	}
	
	@GetMapping("/user")
	public String getUserPage(Model model, HttpSession httpSession) {
		List<Book> books = bookDAO.getAllBooks();
		model.addAttribute("books", books);
		boolean isUserLogin = false;
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			isUserLogin = true;
			model.addAttribute("loggedIn", isUserLogin);
			model.addAttribute("user", user);
			return "user";
		}else {
			model.addAttribute("loggedIn", isUserLogin);
			return "user";
		} 
	}
	
	@GetMapping("/user/view/{id}")
	public String getABookUser(Model model, HttpSession httpSession, @PathVariable String id) {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			Book book =  bookDAO.getABookById(Integer.valueOf(id));
			List<Rating> ratings = ratingDAO.getRatingOfABook(Integer.valueOf(id));
			model.addAttribute("user", user);
			model.addAttribute("book", book);
			model.addAttribute("ratings", ratings);
			return "bookdetail";
		}
		return "error";
	}
	//ham de get tat ca rating cua 1 quyen sach ra 
}
