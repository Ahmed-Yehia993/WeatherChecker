package com.orange.weather;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.orange.weather.configuration.AppConfig;
import com.orange.weather.configuration.HibernateConfiguration;
import com.orange.weather.service.UserService;

@ContextConfiguration(classes={AppConfig.class,HibernateConfiguration.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

public class UserServiceTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void testFindById() {
		Integer id = 1;
		assertEquals(userService.findByEmial("ahmedyehiamokhtar@gmail.com").getId(), id);
	}

	@Test
	public void testFindByEmail() {
		String id = "1";
		assertEquals(userService.findByEmial("ahmedyehiamokhtar@gmail.com").getId().toString(), id);
	}

	@Test
	public void testIsUserEmailUnique() {
		int id = 1;
		assertEquals(userService.isUserEmailUnique(id, "ahmedyehiamokhtar@gmail.com"), true);
	}

}
