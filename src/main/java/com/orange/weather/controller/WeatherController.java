package com.orange.weather.controller;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.orange.weather.model.Note;
import com.orange.weather.service.NoteService;
import com.orange.weather.service.PreDefinedNoteService;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Controller
@SessionAttributes("roles")
public class WeatherController {
	@Autowired
	NoteService notService;

	@Autowired
	PreDefinedNoteService preDefinedNoteService;

	static final Logger logger = (Logger) LoggerFactory.getLogger(WeatherController.class);

	/**
	 * This method redirect to user page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/check" }, method = RequestMethod.GET)
	public String weatherPage(ModelMap model) {
		logger.info("check weather page");
		return "weather";
	}

	/**
	 * This method get today note if exist if not get Note from predefine table
	 * 
	 * @param model
	 * @param degree
	 * @return
	 */
	@RequestMapping(value = { "/getNote" }, method = RequestMethod.GET)
	@ResponseBody
	public String todayNotes(ModelMap model, @RequestParam("degree") String degree) {
		logger.info("get note by degree");
		logger.setLevel(Level.DEBUG);
		logger.debug("degree ");
		model.addAttribute("notes", notService.getTodayNote());
		ArrayList<Note> notes = (ArrayList<Note>) notService.getTodayNote();
		if (notes.size() > 0) {
			return notes.get(0).getDescription();
		} else {
			return preDefinedNoteService.getPreDefineNote((int)Math.round(Float.parseFloat(degree))).getDescription();
		}
	}

}
