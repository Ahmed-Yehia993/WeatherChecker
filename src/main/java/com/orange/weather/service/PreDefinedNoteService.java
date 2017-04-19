package com.orange.weather.service;

import java.util.List;

import com.orange.weather.model.PreDefinedNote;

public interface PreDefinedNoteService {

	public PreDefinedNote getPreDefineNote(Integer degree);

	public List<PreDefinedNote> getAllPreDefineNotes();

	public void update(Integer id, String newDescription);

}
