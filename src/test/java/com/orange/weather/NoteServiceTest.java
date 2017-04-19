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
import com.orange.weather.service.NoteService;

@ContextConfiguration(classes = { AppConfig.class, HibernateConfiguration.class })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class NoteServiceTest {
	@Autowired
	private NoteService noteService;

	@Test
	public void testGetTodayNote() {
		assertEquals(noteService.getTodayNote().size(), 0);
	}

	@Test
	public void testfindAllNotese() {
		assertEquals(noteService.findAllNotes().size(), 8);
	}

}
