package com.example.thuvien.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.thuvien.dao.UserDAO;
import com.example.thuvien.model.User;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	private UserDAO userDAO = new UserDAO();
	@GetMapping("/login")
	public String loginPage(Model model, HttpSession httpSession) throws IOException{
		User user = (User) httpSession.getAttribute("user");
		if(user != null && user.getLoggedIn()) {
			if(user.getRole().equals("admin")) {
				return "redirect:/admin";
			}else if(user.getRole().equals("user")) {
				return "redirect:/user";
			}
		}
		return "login";
	}
	
	@PostMapping("/login")
	public String login(HttpSession httpSession, @RequestParam("username")String username, @RequestParam("password") String password, Model model) {
		User user = userDAO.checkLogin(username, password);
		if(user.getId() != -1) {
			user.setLoggedIn(true);
			httpSession.setAttribute("user", user);
			if(user.getRole().equals("admin")) {
				return "redirect:/admin";
			}else if(user.getRole().equals("user")) {
				return "redirect:/user";
			}
		}
		return "loginincorrect";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) throws IOException{
		return "register";
	}
	@PostMapping("/register")
	public String register(@RequestParam("name")String name, @RequestParam("email") String email, @RequestParam("username")String username, @RequestParam("password") String password, Model model) {
		boolean isexisted = userDAO.checkExist(username);
		if(!isexisted) {
			User user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setUsername(username);
			user.setPassword(password);
			boolean registerSuccess = userDAO.registerUser(user);
			if(registerSuccess) {
				return "registersuccess";
			}
		}else {
			return"userexist";
		}
		return "error";
	}
	@GetMapping("/logout")
	public String logout(Model model, HttpSession httpSession) {
		User user = (User)httpSession.getAttribute("user");
		if(user!=null) {
			user.setLoggedIn(false);
		}
		httpSession.invalidate();
		return "redirect:/login";
	}
}