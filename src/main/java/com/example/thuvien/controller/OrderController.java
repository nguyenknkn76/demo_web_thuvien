package com.example.thuvien.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.thuvien.dao.OrderDAO;
import com.example.thuvien.model.Book;
import com.example.thuvien.model.Order;
import com.example.thuvien.model.User;

import jakarta.servlet.http.HttpSession;
@Controller
public class OrderController {
	private OrderDAO orderDAO = new OrderDAO();
	@GetMapping("/orders")
	public String getOrder(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if(user != null && user.getLoggedIn()&& user.getRole().equals("user")) {
			List<Order> orders = orderDAO.getAllOrder(user);
			List<Order> canceledOrders = orderDAO.getAllCanceledOrder(user);
			model.addAttribute("user", user);
			model.addAttribute("orders", orders);
			model.addAttribute("canceledorders", canceledOrders);
			return "orders";
		}
		return"error";
	}
	@PostMapping("/order/new")
	public String newOrder (Model model, HttpSession httpSession,
			@RequestParam("id") String id,
			@RequestParam("title") String title,
			@RequestParam("quantity") String quantity) {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			
			Order order = new Order();
			order.setBookid(Integer.valueOf(id));
			order.setBookname(title);
			order.setQuantity(Integer.valueOf(quantity));
			order.setUserid(user.getId());
			boolean success = orderDAO.createNewOrder(order);
			if(success) {
				return "redirect:/orders";
			}
		}
		return "error";
	}
	@PostMapping("/order/cancel")
	public String cancelOrder(Model model,HttpSession httpSession, @RequestParam("id") String id ) {
		User user = (User)httpSession.getAttribute("user");
		if(user!= null && user.getLoggedIn() && user.getRole().equals("user")) {
			boolean success = orderDAO.cancelOrder(Integer.valueOf(id));
			if(success) {
				return "redirect:/orders";
			}
			
		}
		return "error";
	}
	
}

