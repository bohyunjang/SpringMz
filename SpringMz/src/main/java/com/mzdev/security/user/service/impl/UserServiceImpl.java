package com.mzdev.security.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mzdev.security.user.model.UserModel;
import com.mzdev.security.user.repository.UserRepository;
import com.mzdev.security.user.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserModel selectUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.selectUserByUserName(userName);
	}

	@Override
	public int insertUser(UserModel userModel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		UserModel userModel = selectUserByUserName(userName);
		/*UserModel user = new UserModel();
		
		final String testUserName = "test@test.com";
		final String testPassword = "test";
		final String testNickName = "Spring Test Nick Name...!!!!! Tester..";
		
		user.setUserName(userName);
		if(user.getUserName().equals(testUserName)){
			user.setUserName(testUserName);
			user.setPassword(encoder.encode(testPassword));
			user.setNickName(testNickName);
			user.setAuthority(AuthorityUtils.createAuthorityList("ROLE_USER"));
		*/
		
		if(userModel !=null){
			System.out.println("authority test!!!");
			System.out.println("authority"+userModel.getAuthority());
			userModel.setAuthorities(AuthorityUtils
					.createAuthorityList(userModel.getAuthority()));
		}else{
			System.out.println("not found authority");
			throw new UsernameNotFoundException(userName);
		}
		return userModel;
	}
	

}
