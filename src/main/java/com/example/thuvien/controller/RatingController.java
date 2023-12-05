package com.example.thuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.thuvien.dao.RatingDAO;
import com.example.thuvien.model.Rating;

import jakarta.servlet.http.HttpSession;
@Controller
public class RatingController {
	private RatingDAO ratingDAO = new RatingDAO();
	@PostMapping("/rate")
	public RedirectView newRating(Model model, HttpSession httpSession,
			@RequestParam("userid") String userid,
			@RequestParam("username") String username,
			@RequestParam("bookid") String bookid,
			@RequestParam("rating") String rating,
			@RequestParam("comment") String comment) {
		Rating rating2 = new Rating();
		rating2.setBookid(Integer.valueOf(bookid));
		rating2.setComment(comment);
		rating2.setRating(Integer.valueOf(rating));
		rating2.setUserid(Integer.valueOf(userid));
		rating2.setUsername(username);
		boolean success = false;
		int id = ratingDAO.getRatingOfAUserForABook(Integer.valueOf(bookid), Integer.valueOf(userid));
		if(id == -1) {
			success = ratingDAO.newRating(rating2);
		}else {
			rating2.setId(id);
			success = ratingDAO.updateRating(rating2);
		}
		String redirectUrl = "/user/view/"+ bookid;
		if(success) {
			return  new RedirectView(redirectUrl);
		}
		return new RedirectView("/error");
	}
}
