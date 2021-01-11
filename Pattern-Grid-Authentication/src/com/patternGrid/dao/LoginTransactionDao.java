package com.patternGrid.dao;

import com.patternGrid.dto.LoginTransaction;

public interface LoginTransactionDao {

	boolean loginTransaction(LoginTransaction transaction);

	boolean logoutTransaction(LoginTransaction transaction);
}
