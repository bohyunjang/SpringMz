package com.mzdev.security.user.repository;

import com.mzdev.security.user.model.UserModel;

public interface UserRepository {

	public UserModel selectUserByUserName(String userName);
	
	public int insertUser(UserModel userModel);
	
}
