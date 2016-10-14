package com.mzdev.security.user.repository.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mzdev.security.user.model.UserModel;
import com.mzdev.security.user.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public UserModel selectUserByUserName(String userName) {
		// TODO Auto-generated method stub
		System.out.println("UserDao.. select UserByUserName");
		System.out.println("userName :: "+ userName);
		return sqlSession.selectOne("userName :: ", userName);
	}

	@Override
	public int insertUser(UserModel userModel) {
		// TODO Auto-generated method stub
		System.out.println("UserRepository... insertUser!!");
		return sqlSession.insert("insertUser", userModel);
	}

}
