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
import com.patternGrid.dao.ConfigDao;
import com.patternGrid.dao.LoginTransactionDao;
import com.patternGrid.dao.PatternTypeDao;
import com.patternGrid.dto.Config;
import com.patternGrid.dto.LoginTransaction;
import com.patternGrid.dto.User;
import com.patternGrid.randomization.RandomGridGenerator;
import com.patternGrid.service.UserService;

@Controller
public class Usercntr {

	@Autowired
	UserService userService;

	@Autowired
	ConfigDao configDao;

	@Autowired
	PatternTypeDao patternTypeDao;

	@Autowired
	LoginTransactionDao loginTransactionDao;

	public static String encodePassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	public static void checkSession(HttpSession hs, HttpServletResponse r) throws IOException {
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		// String sessionUserEmail = (String) hs.getAttribute("sessionUserEmail");
		System.out.println(sessionUserId);
		if (sessionUserId == null) {
			r.sendRedirect("login");
		}
	}

	@RequestMapping("/")
	public String main(HttpSession hs) {
		hs.setAttribute("loginValue", false);
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		if (sessionUserId != null)
			return "home";
		return "login";
	}

	@RequestMapping(value = "/about")
	public String About(HttpSession session) {
		session.setAttribute("loginValue", false);
		return "about";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession hs) {
		hs.setAttribute("loginValue", false);
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		if (sessionUserId != null)
			return "home";
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletResponse response, LoginTransaction loginTransaction,
			User user) throws IOException {
		user.setUserId((String) session.getAttribute("sessionUserId"));
		System.out.println(user);
		loginTransaction.setUser(user);
		loginTransaction.setSessionId(session.getId());
		System.out.println(session.getId());
		loginTransaction.setStatus(false);
		loginTransactionDao.logoutTransaction(loginTransaction);

		session.invalidate();
		response.sendRedirect("login");
		return null;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String prepareUserRegister(HttpSession hs) {
		hs.setAttribute("loginValue", false);
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		if (sessionUserId != null)
			return "home";
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String submitUserRegister(HttpSession session, HttpServletRequest req, HttpServletResponse res, User user,
			Config config) throws IOException {
		session.setAttribute("loginValue", false);
		user.setUserPatternPassword(encodePassword(user.getUserPatternPassword()));
		// Adding default patternType according to set config

		config = configDao.getConfigDefaultPatternType("patternGridSize");
		System.out.println(config);
		user.setPatternType(patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue())));

		boolean isSuccessfulRegister = userService.registerUser(user);
		if (isSuccessfulRegister)
			res.sendRedirect("register?msg=successful");
		else
			res.sendRedirect("register?msg=unSuccessful");
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String prepareUserLogin(HttpSession hs) {
		hs.setAttribute("loginValue", false);
		String sessionUserId = (String) hs.getAttribute("sessionUserId");
		if (sessionUserId != null)
			return "home";
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitUserLogin(@RequestParam String randomGrid, HttpSession session, ModelMap map,
			HttpServletRequest req, HttpServletResponse res, User user, Config config,
			LoginTransaction loginTransaction) throws IOException {

		if (!(Boolean) session.getAttribute("loginValue")) {
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

			System.out.println(user);
			boolean isSuccessfulLogin = userService.isUserValid(user);
			if (isSuccessfulLogin) {

				// Start Session
				session.setAttribute("sessionUserId", user.getUserId());

				System.out.println("sessionID" + session.getId());

				// Transaction Entry
				loginTransaction.setUser(user);
				loginTransaction.setSessionId(session.getId());
				loginTransaction.setStatus(true);
				loginTransactionDao.loginTransaction(loginTransaction);

				// Login Successful
				res.sendRedirect("home");
				return null;
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
		checkSession(hs, r);
		return "home";
	}

}
