package com.orange.weather.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.orange.weather.model.Note;
import com.orange.weather.model.PreDefinedNote;
import com.orange.weather.model.User;
import com.orange.weather.service.NoteService;
import com.orange.weather.service.PreDefinedNoteService;
import com.orange.weather.service.UserService;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * @author Tie 3
 *
 */
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
@Secured("ADMIN")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	NoteService noteService;
	@Autowired
	PreDefinedNoteService preDefineNoteService;
	static final Logger logger = (Logger) LoggerFactory.getLogger(AdminController.class);

	/**
	 * This method will Open dashboard with all required data.
	 */
	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public String dashboard(ModelMap model) {
		logger.info("dashboard page");
		model.addAttribute("NumberOfusers", userService.findAllUsers().size());
		List<User> users = userService.findAllUsers();
		List<Note> notes = noteService.findAllNotes();
		List<PreDefinedNote> preNotes = preDefineNoteService.getAllPreDefineNotes();
		model.addAttribute("todayNote", noteService.getTodayNote().size() > 0 ? true : false);
		model.addAttribute("notes", notes);
		model.addAttribute("preNotes", preNotes);
		model.addAttribute("users", users);

		return "dashboard";
	}

	/**
	 * @param model
	 * @param id
	 * @param newDescription
	 *            edit note with new value with ajax request
	 */
	@RequestMapping(value = { "/editPreNote" }, method = RequestMethod.GET)
	@ResponseBody
	public void editPreNote(ModelMap model, @RequestParam("id") Integer id,
			@RequestParam("newDescription") String newDescription) {
		logger.info("edit pre note");
		logger.setLevel(Level.DEBUG);
		logger.debug("noteId");
		if (newDescription.length() > 0)
			preDefineNoteService.update(id, newDescription);
	}

	/**
	 * @param model
	 * @param note
	 * @return
	 * 
	 * 		insert new note for today
	 */
	@RequestMapping(value = { "/setTodayNote" }, method = RequestMethod.GET)
	@ResponseBody
	public String setTodayNote(ModelMap model, @RequestParam("note") String note) {
		if (note.length() > 0) {
			Note note2 = new Note();
			note2.setDescription(note);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			note2.setDate(dateFormat.format(date));
			noteService.save(note2);
			return dateFormat.format(date);

		} else {
			return "";
		}

	}

}
