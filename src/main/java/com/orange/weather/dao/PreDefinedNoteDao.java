package com.orange.weather.dao;

import java.util.List;

import com.orange.weather.model.PreDefinedNote;

public interface PreDefinedNoteDao {

	public PreDefinedNote getPreDefineNote(Integer degree);

	public List<PreDefinedNote> getAllPreDefineNotes();

	public PreDefinedNote findById(Integer id);

}
