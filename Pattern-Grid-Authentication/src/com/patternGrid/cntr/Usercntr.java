package com.patternGrid.cntr;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.patternGrid.dto.User;
import com.patternGrid.randomization.RandomGridGenerator;
import com.patternGrid.service.UserService;

@Controller
public class Usercntr {

	@Autowired
	UserService userService;

	public static String encodePassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
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
		user.setUserPatternPassword(encodePassword(user.getUserPatternPassword()));
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
	public String submitUserLogin(@RequestParam String randomGrid, HttpSession session, ModelMap map,
			HttpServletRequest req, HttpServletResponse res, User user) throws IOException {

		if (!(Boolean) session.getAttribute("loginValue")) {
			// check username if exists-
			user = userService.getUser(user);
			if (user == null) {
				res.sendRedirect("login?msg=invalid");
				return null;
			}

			int row = user.getPatternType().getPatternRowSize();
			int col = user.getPatternType().getPatternColSize();
			String[][] randomPatternGrid = RandomGridGenerator.randomizor(row, col, 2);

			map.put("randomPatternGrid", randomPatternGrid);
			map.put("user", user);
			map.put("row", row);
			map.put("col", col);
			session.setAttribute("loginValue", true);
			return "login";

		} else if ((Boolean) session.getAttribute("loginValue")) {
			session.setAttribute("loginValue", false);

			String[] key = user.getUserPatternPassword().split("(?<=\\G.{2})");
			System.out.println(Arrays.toString(key));
			Gson gson = new Gson();
			System.out.println(randomGrid);

			String[][] randomPatternGrid = gson.fromJson(randomGrid, String[][].class);

			String actualPassword = "";

			for (int keyIndex = 0; keyIndex < key.length; keyIndex++) {
				boolean flag = false;
				int i = 0;
				int j = 0;
				for (i = 0; i < randomPatternGrid.length; i++) {
					for (j = 0; j < randomPatternGrid[i].length; j++) {
						if (key[keyIndex].contentEquals(randomPatternGrid[i][j])) {
							flag = true;
							System.out.println(i + " " + j + " " + key[keyIndex]);
							break;
						}
					}
					if (flag == true)
						break;
				}
				if (flag != true) {
					res.sendRedirect("login?msg=invalid");
					return null;
				} else {
					actualPassword += i + "" + j;
					System.out.println(actualPassword);
				}
			}

			user.setUserPatternPassword(actualPassword);
			System.out.println(actualPassword);

			boolean isSuccessfulLogin = userService.isUserValid(user);
			if (isSuccessfulLogin) {
				session.setAttribute("sessionUserId", user.getUserId());
				// session.setAttribute("sessionUserEmail", user.getUserEmail());
				res.sendRedirect("home");
				System.out.println("sessionID" + session.getId());
			} else {
				res.sendRedirect("login?msg=invalid");
				return null;
			}
		}

		return null;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession hs, HttpServletResponse r) throws IOException {
		hs.setAttribute("loginValue", false);

		checkSessionTeacher(hs, r);
		return "home";
	}

}
