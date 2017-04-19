package com.orange.weather.service;

import java.util.List;

import com.orange.weather.model.User;


public interface UserService {
	
	User findById(int id);
	
	User findBySSO(String sso);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserBySSO(String sso);

	List<User> findAllUsers(); 
	
	boolean isUserSSOUnique(Integer id, String sso);

	User findByEmial(String email);

	boolean isUserEmailUnique(Integer id, String email);

}