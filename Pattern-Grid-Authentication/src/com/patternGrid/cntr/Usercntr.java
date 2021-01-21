package com.patternGrid.cntr;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
import com.patternGrid.dto.PatternType;
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

    @Autowired
    private MailSender mailSender;

    public static String encodePassword(String password) {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	String hashedPassword = passwordEncoder.encode(password);
	return hashedPassword;
    }

    public static boolean checkSession(HttpSession hs, HttpServletResponse r) throws IOException {
	String sessionUserId = (String) hs.getAttribute("sessionUserId");
	// String sessionUserEmail = (String) hs.getAttribute("sessionUserEmail");
	System.out.println(sessionUserId);
	if (sessionUserId == null) {
	    r.sendRedirect("login");
	    return false;
	}
	return true;
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
    public String prepareUserRegister(HttpSession hs, Config config, PatternType defaultPatternType, ModelMap map) {
	hs.setAttribute("loginValue", false);
	String sessionUserId = (String) hs.getAttribute("sessionUserId");
	if (sessionUserId != null)
	    return "home";

	config = configDao.getConfigDefaultPatternType("patternGridSize");
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	map.put("defaultPatternType", defaultPatternType);

	return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitUserRegister(PatternType defaultPatternType, HttpSession session, HttpServletRequest req,
	    HttpServletResponse res, User user, Config config) throws IOException {
	session.setAttribute("loginValue", false);
	String sessionUserId = (String) session.getAttribute("sessionUserId");
	if (sessionUserId != null)
	    return "home";

	User tempUser = userService.getUser(user);
	System.out.println(tempUser + "tempuser!!");
	if (tempUser != null) {
	    res.sendRedirect("register?msg=userIdExists");
	    return null;
	}

	// Adding default patternType according to set config
	config = configDao.getConfigDefaultPatternType("patternGridSize");
	System.out.println(config);
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	user.setPatternType(defaultPatternType);
	int defaultRows = defaultPatternType.getPatternRowSize();
	int defaultCols = defaultPatternType.getPatternColSize();

	// Parsing the submitted pattern
	String pattern = user.getUserPatternPassword();
	System.out.println(pattern);
	String patternArr[] = pattern.split(",");
	System.out.println(Arrays.toString(patternArr));
	String actualPattern = "";
	if (patternArr.length > (defaultRows * defaultCols)) {
	    res.sendRedirect("register?msg=invalidPattern");
	    return null;
	}
	for (int i = 0; i < patternArr.length; i++) {
	    int currentNumber = -1;
	    try {
		currentNumber = Integer.parseInt(patternArr[i]);
		if (currentNumber <= 0 || currentNumber > (defaultRows * defaultCols))
		    throw new Exception();
	    } catch (Exception e) {
		res.sendRedirect("register?msg=unSuccessful");
		return null;
	    }
	    currentNumber--;
	    Number first = currentNumber / defaultCols;
	    actualPattern += first.toString();
	    Number second = currentNumber % defaultCols;
	    actualPattern += second.toString();
	}
	System.out.println(actualPattern);

	// Encoding Pattern Password
	user.setUserPatternPassword(encodePassword(actualPattern));

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
	    System.out.println(user);
	    if (user == null) {
		res.sendRedirect("login?msg=invalidUser");
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
		    res.sendRedirect("login?msg=invalidPattern");
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
		res.sendRedirect("login?msg=invalidCredentials");
		return null;
	    }
	}

	return null;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpSession hs, HttpServletResponse r) throws IOException {
	hs.setAttribute("loginValue", false);
	if (!checkSession(hs, r)) {
	    return null;
	}
	return "home";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String settings(PatternType defaultPatternType, HttpSession session, HttpServletRequest req,
	    HttpServletResponse r, User user, Config config, ModelMap map) throws IOException {
	session.setAttribute("loginValue", false);
	if (!checkSession(session, r)) {
	    return null;
	}
	user.setUserId((String) session.getAttribute("sessionUserId"));
	user = userService.getUser(user);
	config = configDao.getConfigDefaultPatternType("patternGridSize");
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	map.put("defaultPatternType", defaultPatternType);
	map.put("userDetails", user);

	return "settings";
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String settingsResetPattern(PatternType defaultPatternType, HttpSession hs, HttpServletRequest req,
	    HttpServletResponse res, User user, Config config) throws IOException {
	hs.setAttribute("loginValue", false);
	if (!checkSession(hs, res)) {
	    return null;
	}

	user.setUserId((String) hs.getAttribute("sessionUserId"));
	user = userService.getUser(user);
	// Adding default patternType according to set config
	config = configDao.getConfigDefaultPatternType("patternGridSize");
	System.out.println(config);
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	user.setPatternType(defaultPatternType);
	int defaultRows = defaultPatternType.getPatternRowSize();
	int defaultCols = defaultPatternType.getPatternColSize();

	// Parsing the submitted pattern
	String pattern = user.getUserPatternPassword();
	System.out.println(pattern);
	String patternArr[] = pattern.split(",");
	System.out.println(Arrays.toString(patternArr));
	String actualPattern = "";
	if (patternArr.length > (defaultRows * defaultCols)) {
	    res.sendRedirect("logout");
	    return null;
	}
	for (int i = 0; i < patternArr.length; i++) {
	    int currentNumber = -1;
	    try {
		currentNumber = Integer.parseInt(patternArr[i]);
		if (currentNumber <= 0 || currentNumber > (defaultRows * defaultCols))
		    throw new Exception();
	    } catch (Exception e) {
		res.sendRedirect("logout");
		return null;
	    }
	    currentNumber--;
	    Number first = currentNumber / defaultCols;
	    actualPattern += first.toString();
	    Number second = currentNumber % defaultCols;
	    actualPattern += second.toString();
	}
	System.out.println(actualPattern);

	// Encoding Pattern Password
	user.setUserPatternPassword(encodePassword(actualPattern));

	boolean isSuccessfulRegister = userService.resetPattern(user);
	if (isSuccessfulRegister)
	    res.sendRedirect("settings?msg=resetSuccessful");
	else
	    res.sendRedirect("logout");
	return null;
    }

    @RequestMapping("/forgotPattern")
    public String forgotPattern(HttpSession hs, User user) {
	hs.setAttribute("loginValue", false);
	String sessionUserId = (String) hs.getAttribute("sessionUserId");
	if (sessionUserId != null)
	    return "home";
	return "forgotPattern";
    }

    @RequestMapping(value = "/forgotPattern", method = RequestMethod.POST)
    public String requestOTP(HttpSession hs, User user, HttpServletResponse r, HttpServletRequest req)
	    throws IOException {
	hs.setAttribute("loginValue", false);
	String sessionUserId = (String) hs.getAttribute("sessionUserId");
	if (sessionUserId != null)
	    return "home";

	try {
	    User user2 = userService.getUser(user);
	    if (user2 == null) {
		r.sendRedirect("forgotPattern?msg=invalidUser");
		return null;
	    }
	    String OTP = RandomGridGenerator.getRandomOTP();

	    hs.setAttribute("OTP", OTP);
	    hs.setAttribute("OTPUser", user2);

	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setFrom("cdacmumbai3@gmail.com");
	    message.setTo(user.getUserEmail());
	    message.setSubject("pAuthenticate account pattern password reset");
	    message.setText("Pattern Password reset OTP\n\n" + "Dear \"" + user.getUserId()
		    + "\" please use this OTP to reset the password for your account: " + user.getUserEmail() + ".\n"
		    + "" + "\n\nHere is your code: " + OTP + "\n" + "\n\n\nThanks,\n" + "pAuthenticate Team");
	    mailSender.send(message);
	    r.sendRedirect("verifyOTP");

	} catch (Exception e) {
	    System.out.println("fsdgfsdgdfgdfhbdghdghdghdg");
	    r.sendRedirect("forgotPattern?msg=invalidUser");
	}
	return null;
    }

    @RequestMapping("/verifyOTP")
    public String verifyOTP(PatternType defaultPatternType, HttpSession session, HttpServletRequest req,
	    HttpServletResponse r, User user, Config config, ModelMap map) throws IOException {
	session.setAttribute("loginValue", false);
	String sessionUserId = (String) session.getAttribute("sessionUserId");
	if (sessionUserId != null)
	    return "home";
	user = (User) session.getAttribute("OTPUser");
	if (user == null) {
	    r.sendRedirect("forgotPattern?msg=invalidUser");
	    return null;
	}
	user = userService.getUser(user);
	config = configDao.getConfigDefaultPatternType("patternGridSize");
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	map.put("defaultPatternType", defaultPatternType);
	map.put("userDetails", user);
	return "verifyOTP";
    }

    @RequestMapping(value = "/verifyOTP", method = RequestMethod.POST)
    public String verifyOTPSubmit(@RequestParam String otp, PatternType defaultPatternType, HttpSession hs,
	    HttpServletRequest req, HttpServletResponse r, User user, Config config, ModelMap map) throws IOException {
	hs.setAttribute("loginValue", false);
	String sessionUserId = (String) hs.getAttribute("sessionUserId");

	if (sessionUserId != null)
	    return "home";
	String actualOTP = (String) hs.getAttribute("OTP");

	if (!actualOTP.equals(otp)) {
	    r.sendRedirect("verifyOTP?msg=invalidOTP");
	    return null;
	} else {

	    hs.setAttribute("isOTPVerified", true);
	    r.sendRedirect("resetForgotPattern");
	}
	return null;
    }

    @RequestMapping("/resetForgotPattern")
    public String resetForgotPattern(PatternType defaultPatternType, HttpSession session, HttpServletRequest req,
	    HttpServletResponse r, User user, Config config, ModelMap map) throws IOException {
	session.setAttribute("loginValue", false);
	String sessionUserId = (String) session.getAttribute("sessionUserId");
	if (sessionUserId != null)
	    return "home";
	Boolean isOTPVerified = (Boolean) session.getAttribute("isOTPVerified");

	if (isOTPVerified == null || !isOTPVerified) {

	    r.sendRedirect("forgotPattern?msg=OTPError");
	    return null;
	}
	user = (User) session.getAttribute("OTPUser");

	user = userService.getUser(user);
	config = configDao.getConfigDefaultPatternType("patternGridSize");
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	map.put("defaultPatternType", defaultPatternType);
	map.put("userDetails", user);

	return "resetForgotPattern";
    }

    @RequestMapping(value = "/resetForgotPattern", method = RequestMethod.POST)
    public String resetForgotPattern(PatternType defaultPatternType, HttpSession hs, HttpServletRequest req,
	    HttpServletResponse res, User user, Config config) throws IOException {
	hs.setAttribute("loginValue", false);
	String sessionUserId = (String) hs.getAttribute("sessionUserId");
	User user2 = (User) hs.getAttribute("OTPUser");
	user.setUserId(user2.getUserId());
	user.setUserEmail(user2.getUserEmail());
	if (sessionUserId != null)
	    return "home";
	boolean isOTPVerified = (boolean) hs.getAttribute("isOTPVerified");

	if (!isOTPVerified) {
	    res.sendRedirect("forgotPattern?msg=OTPError");
	    return null;
	}

	// Adding default patternType according to set config
	config = configDao.getConfigDefaultPatternType("patternGridSize");
	System.out.println(config);
	defaultPatternType = patternTypeDao.getPatternType(Integer.parseInt(config.getParamValue()));
	user.setPatternType(defaultPatternType);
	int defaultRows = defaultPatternType.getPatternRowSize();
	int defaultCols = defaultPatternType.getPatternColSize();

	System.out.println(user + " " + user2 + "Hellloooooooo");
	// Parsing the submitted pattern
	String pattern = user.getUserPatternPassword();
	System.out.println(pattern);
	String patternArr[] = pattern.split(",");
	System.out.println(Arrays.toString(patternArr));
	String actualPattern = "";
	if (patternArr.length > (defaultRows * defaultCols)) {
	    res.sendRedirect("logout");
	    return null;
	}
	for (int i = 0; i < patternArr.length; i++) {
	    int currentNumber = -1;
	    try {
		currentNumber = Integer.parseInt(patternArr[i]);
		if (currentNumber <= 0 || currentNumber > (defaultRows * defaultCols))
		    throw new Exception();
	    } catch (Exception e) {
		res.sendRedirect("logout");
		return null;
	    }
	    currentNumber--;
	    Number first = currentNumber / defaultCols;
	    actualPattern += first.toString();
	    Number second = currentNumber % defaultCols;
	    actualPattern += second.toString();
	}
	System.out.println(actualPattern);

	// Encoding Pattern Password
	user.setUserPatternPassword(encodePassword(actualPattern));

	boolean isSuccessfulRegister = userService.resetPattern(user);
	if (isSuccessfulRegister)
	    res.sendRedirect("login?msg=successful");
	else
	    res.sendRedirect("logout");
	return null;
    }

}
