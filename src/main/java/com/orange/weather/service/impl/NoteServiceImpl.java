package com.orange.weather.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orange.weather.dao.NoteDao;
import com.orange.weather.model.Note;
import com.orange.weather.service.NoteService;

@Service("noteeService")
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao dao;

	@Override
	public void save(Note note) {	
		dao.save(note);
	}

	@Override
	public List<Note> findAllNotes() {
		return dao.findAllNotes();
	}

	@Override
	public List<Note> getTodayNote() {
		return dao.getTodayNotes();
	}

}
