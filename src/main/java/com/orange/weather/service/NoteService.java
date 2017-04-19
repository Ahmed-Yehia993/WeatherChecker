package com.orange.weather.service;

import java.util.List;

import com.orange.weather.model.Note;

public interface NoteService {
	void save(Note note);

	List<Note> findAllNotes();

	List<Note> getTodayNote();
}
