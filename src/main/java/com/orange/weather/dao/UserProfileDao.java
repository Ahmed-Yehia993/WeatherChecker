package com.orange.weather.dao;

import java.util.List;

import com.orange.weather.model.UserProfile;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
