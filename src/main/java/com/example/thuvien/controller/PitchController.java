package com.example.thuvien.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.thuvien.dao.UserDAO;
import com.example.thuvien.model.Book;
import com.example.thuvien.model.User;

import jakarta.servlet.http.HttpSession;
@Controller
public class PitchController {
	private UserDAO userDAO = new UserDAO();	
	@GetMapping("/qlsbuser")
	public String getQlsbUserPage(Model model, HttpSession httpSession) {
		boolean isUserLogin = false;
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			isUserLogin = true;
			model.addAttribute("loggedIn", isUserLogin);
			model.addAttribute("user", user);
			return "qlsbuser";
		}else {
			model.addAttribute("loggedIn", isUserLogin);
			return "login";
		} 
	}
	@GetMapping("/qlsbedituser")
	public String getQlsbEditUserPage(Model model, HttpSession httpSession) {
		boolean isUserLogin = false;
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			model.addAttribute("user", user);
			return "qlsbedituser";
		}
		return "error";
	}
	@PostMapping("/qlsbedituser/save")
	public String saveABookAdmin(Model model, HttpSession httpSession,
			@RequestParam("uName") String name,
			@RequestParam("uUsername") String username,
			@RequestParam("uPassword") String password,
			@RequestParam("uEmail") String email,
			@RequestParam("uRole") String role
			) throws IOException {
		User user = (User)httpSession.getAttribute("user");
		int id = user.getId();
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			user.setId(id);
			user.setName(name);
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setRole(role);
			boolean success = userDAO.updateUser(user);
			if(success) {
				return "redirect:/qlsbedituser";
			}
		}
		return "error";
	}
}


