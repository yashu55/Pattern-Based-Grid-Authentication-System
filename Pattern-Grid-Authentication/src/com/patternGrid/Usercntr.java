package com.patternGrid;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.patternGrid.dto.User;
import com.patternGrid.service.UserService;

@Controller
public class Usercntr {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String prepareUserRegister() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String submitUserRegister(HttpSession session, HttpServletRequest req, HttpServletResponse res, User user)
			throws IOException {

		boolean isSuccessfulRegister = userService.registerUser(user);

		if (isSuccessfulRegister)
			res.sendRedirect("register?msg=successful");
		else
			res.sendRedirect("register?msg=unSuccessful");
		return null;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String prepareUserLogin() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitUserLogin(HttpSession session, HttpServletRequest req, HttpServletResponse res, User user)
			throws IOException {

		boolean isSuccessfulLogin = userService.isUserValid(user);
		if (isSuccessfulLogin) {
			session.setAttribute("sessionUserId", user.getUserId());
			// session.setAttribute("sessionUserEmail", user.getUserEmail());
			res.sendRedirect("home");
			System.out.println("sessionID" + session.getId());
		} else
			res.sendRedirect("login?msg=invalid");
		return null;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession hs, HttpServletResponse r) throws IOException {
		checkSessionTeacher(hs, r);
		return "home";
	}

	@RequestMapping(value = "/About")
	public String About() {
		return "About";
	}

	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletResponse response) throws IOException {

		session.invalidate();

		response.sendRedirect("index");
		return null;
	}

	public static void checkSessionTeacher(HttpSession hs, HttpServletResponse r) throws IOException {
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		// String sessionUserEmail = (String) hs.getAttribute("sessionUserEmail");
		System.out.println(sessionUserId);
		if (sessionUserId == null) {
			System.out.println("abc");
			r.sendRedirect("index");

		}

	}

}
