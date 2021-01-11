package com.patternGrid.cntr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.patternGrid.dto.User;
import com.patternGrid.randomization.RandomGridGenerator;
import com.patternGrid.service.UserService;

@Controller
public class Usercntr {

	@Autowired
	UserService userService;

	public static void checkSessionTeacher(HttpSession hs, HttpServletResponse r) throws IOException {
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		// String sessionUserEmail = (String) hs.getAttribute("sessionUserEmail");
		System.out.println(sessionUserId);
		if (sessionUserId == null) {
			System.out.println("abc");
			r.sendRedirect("index");
		}
	}

	@RequestMapping("/")
	public String main(HttpSession session) {
		session.setAttribute("loginValue", false);

		return "login";
	}

	@RequestMapping(value = "/about")
	public String About(HttpSession session) {
		session.setAttribute("loginValue", false);

		return "about";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession session) {
		session.setAttribute("loginValue", false);

		return "login";
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.invalidate();
		response.sendRedirect("index");
		return null;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String prepareUserRegister(HttpSession session) {
		session.setAttribute("loginValue", false);

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String submitUserRegister(HttpSession session, HttpServletRequest req, HttpServletResponse res, User user)
			throws IOException {
		session.setAttribute("loginValue", false);

		boolean isSuccessfulRegister = userService.registerUser(user);
		if (isSuccessfulRegister)
			res.sendRedirect("register?msg=successful");
		else
			res.sendRedirect("register?msg=unSuccessful");
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String prepareUserLogin(HttpSession session) {
		session.setAttribute("loginValue", false);
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitUserLogin(HttpSession session, ModelMap map, HttpServletRequest req, HttpServletResponse res,
			User user) throws IOException {

		if (!(Boolean) session.getAttribute("loginValue")) {
			// check username if exists-
			int row = 4;
			int col = 4;
			String[] randomPatternGrid = RandomGridGenerator.randomizor(row*col, 2);
			String 
			
			map.put("randomPatternGrid", randomPatternGrid);
			map.put("user", user);
			session.setAttribute("loginValue", true);
			return "login";

		}
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
		hs.setAttribute("loginValue", false);

		checkSessionTeacher(hs, r);
		return "home";
	}

}
