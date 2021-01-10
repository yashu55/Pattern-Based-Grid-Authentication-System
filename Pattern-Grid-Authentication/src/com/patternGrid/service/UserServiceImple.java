package com.patternGrid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patternGrid.dao.UserDao;
import com.patternGrid.dto.User;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	UserDao userDao;
	
	
	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		return userDao.registerUser(user);
	}


	@Override
	public boolean isUserValid(User user) {
		// TODO Auto-generated method stub
		return userDao.isUserValid(user);
	}

}
