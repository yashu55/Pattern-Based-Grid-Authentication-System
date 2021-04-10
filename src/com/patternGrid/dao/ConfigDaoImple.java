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

import com.patternGrid.dto.Config;

@Repository
public class ConfigDaoImple implements ConfigDao {

	@Autowired
	HibernateTemplate ht;

	@Override
	public Config getConfigDefaultPatternType(String paramName) {

		return ht.execute(new HibernateCallback<Config>() {

			@Override
			public Config doInHibernate(Session session) throws HibernateException {

				Transaction tr = session.beginTransaction();
				Query q = session.createQuery("from Config where paramName = ?");
				q.setString(0, paramName);
				List<Config> li = q.list();
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
