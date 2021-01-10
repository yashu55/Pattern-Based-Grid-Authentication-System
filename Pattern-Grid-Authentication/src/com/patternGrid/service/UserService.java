package com.patternGrid.service;

import com.patternGrid.dto.User;

public interface UserService {
	boolean registerUser(User user);

	boolean isUserValid(User user);

}
