package com.patternGrid.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.patternGrid.dto.PatternType;

@Repository
public class PatternTypeDaoImple implements PatternTypeDao {

	@Autowired
	HibernateTemplate ht;

	@Override
	public PatternType getPatternType(int patternId) {
		// TODO Auto-generated method stub
		return ht.execute(new HibernateCallback<PatternType>() {

			@Override
			public PatternType doInHibernate(Session session) throws HibernateException {
				Transaction tr = session.beginTransaction();
				Query q = session.createQuery("from PatternType where PatternTypeId = ?");
				q.setInteger(0, patternId);
				List<PatternType> li = q.list();
				if (li == null)
					return null;

				tr.commit();
				session.flush();
				session.close();

				return li.get(0);
			}
		});
	}

}
