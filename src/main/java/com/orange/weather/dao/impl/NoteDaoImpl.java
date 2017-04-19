package com.orange.weather.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.orange.weather.dao.AbstractDao;
import com.orange.weather.dao.NoteDao;
import com.orange.weather.model.Note;

@Repository("noteDao")
public class NoteDaoImpl extends AbstractDao<Integer, Note> implements NoteDao {

	@Override
	public void save(Note note) {
		persist(note);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> findAllNotes() {
		Criteria criteria = createEntityCriteria().addOrder(Order.desc("date"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<Note> notes = (List<Note>) criteria.list();
		return notes;
	}

	@Override
	public List<Note> getTodayNotes() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("date", dateFormat.format(date)));
		@SuppressWarnings("unchecked")
		List<Note> notes = (List<Note>) crit.list();

		return notes;
	}

}
