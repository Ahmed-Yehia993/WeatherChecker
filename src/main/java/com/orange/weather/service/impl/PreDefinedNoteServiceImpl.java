package com.orange.weather.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orange.weather.dao.PreDefinedNoteDao;
import com.orange.weather.model.PreDefinedNote;
import com.orange.weather.service.PreDefinedNoteService;

@Service("preDefinedNoteService")
@Transactional
public class PreDefinedNoteServiceImpl implements PreDefinedNoteService {

	@Autowired
	PreDefinedNoteDao preDefinedNoteDao;

	@Override
	public PreDefinedNote getPreDefineNote(Integer degree) {
		return preDefinedNoteDao.getPreDefineNote(degree);
	}

	@Override
	public List<PreDefinedNote> getAllPreDefineNotes() {
		return preDefinedNoteDao.getAllPreDefineNotes();
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	@Override
	public void update(Integer id, String newDescription) {
		PreDefinedNote entity = preDefinedNoteDao.findById(id);
		if (entity != null) {
			entity.setDescription(newDescription);
		}

	}

}
