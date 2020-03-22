package com.user.wongi5.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.user.wongi5.dao.AuthDao;
import com.user.wongi5.model.LoginInfo;
import com.user.wongi5.model.User;

@Controller
@SessionAttributes("User")
public class LoginController {
	
	@Autowired
	AuthDao authDao;
	
	
	/**
	 * Create new signUpForm object for empty from
	 * 
	 * @return
	 */
	@ModelAttribute("loginInfo")
	public LoginInfo loginForm() {
		return new LoginInfo();
	}

	/**
	 * Method to show the initial HTML form
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String login(HttpSession session) {
	    User user = (User) session.getAttribute("email");

	    if(user != null) {
	    	return "login-success";
	    }
	    return "login";
	}

	@PostMapping("/login") 
	public String login(@ModelAttribute("loginInfo") LoginInfo loginInfo, Model model){
		boolean status=authDao.checkUser(loginInfo.getUserType(), loginInfo.getEmail(), loginInfo.getPassword());
		model.addAttribute("message", "Login Fail");
		System.out.println("status : "+status);
		if(status==true) {
			model.addAttribute("user_email", loginInfo.getEmail());
			model.addAttribute("message", "Login Successful");
			return "login-success";
		}
		return "login";
	}
	
}
