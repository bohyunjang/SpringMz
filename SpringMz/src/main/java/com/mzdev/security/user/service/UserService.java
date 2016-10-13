package com.mzdev.security.user.service;

import com.mzdev.security.user.model.UserModel;

public interface UserService{

	public UserModel selectUserByUserName(String userName);

	public int insertUser(UserModel userModel);

}
