package com.patternGrid.dao;

import com.patternGrid.dto.User;

public interface UserDao {
	boolean registerUser(User user);

	boolean isUserValid(User user);

	User getUser(User user);

	boolean resetPattern(User user);
}
