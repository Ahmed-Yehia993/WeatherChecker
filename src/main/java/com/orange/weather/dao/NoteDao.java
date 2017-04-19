package com.orange.weather.dao;

import java.util.List;

import com.orange.weather.model.Note;

public interface NoteDao {

	void save(Note note);

	List<Note> findAllNotes();

	List<Note> getTodayNotes();
}
