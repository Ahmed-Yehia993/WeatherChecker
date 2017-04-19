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
import com.orange.weather.service.PreDefinedNoteService;

@ContextConfiguration(classes={AppConfig.class,HibernateConfiguration.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PreDefindNoteServiceTest {

	@Autowired
	PreDefinedNoteService preDefinedNoteService;
	

	@Test
	public void testGetPreDefineNote() {
		Integer x = 3;
		assertEquals(preDefinedNoteService.getPreDefineNote(17).getId(), x);
	}

}
