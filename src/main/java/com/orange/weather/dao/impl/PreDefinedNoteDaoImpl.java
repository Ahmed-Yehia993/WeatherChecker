package com.orange.weather.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.orange.weather.dao.AbstractDao;
import com.orange.weather.dao.PreDefinedNoteDao;
import com.orange.weather.model.PreDefinedNote;

@Repository("preDefinedNoteDao")
public class PreDefinedNoteDaoImpl extends AbstractDao<Integer, PreDefinedNote> implements PreDefinedNoteDao {

	static final Logger logger = LoggerFactory.getLogger(PreDefinedNoteDaoImpl.class);

	@Override
	public PreDefinedNote getPreDefineNote(Integer degree) {
		logger.info("Degree : {}", degree);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.sqlRestriction(degree + " > this_.from_degree  and " + degree + " <= this_.to_degree"));
		PreDefinedNote preDefinedNote = (PreDefinedNote) crit.uniqueResult();
		return preDefinedNote;

	}

	@Override
	public List<PreDefinedNote> getAllPreDefineNotes() {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		@SuppressWarnings("unchecked")
		List<PreDefinedNote> notes = (List<PreDefinedNote>) criteria.list();
		return notes;
	}

	@Override
	public PreDefinedNote findById(Integer id) {
		PreDefinedNote preDefinedNote = getByKey(id);
		return preDefinedNote;
	}

}
