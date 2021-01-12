package com.patternGrid.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.patternGrid.dto.User;

@Repository
public class UserDaoImple implements UserDao {

	@Autowired
	HibernateTemplate ht;

	@Override
	public boolean registerUser(User user) {

		try {
			return ht.execute(new HibernateCallback<Boolean>() {

				@Override
				public Boolean doInHibernate(Session session) throws HibernateException {

					Transaction tr = session.beginTransaction();
					session.save(user);
					tr.commit();
					session.flush();
					session.close();
					return true;
				}
			});
		} catch (Exception e) {
			System.out.println("Error in saving!!!");
			return false;
		}
	}

	@Override
	public boolean isUserValid(User user) {
		try {
			return ht.execute(new HibernateCallback<Boolean>() {

				@Override
				public Boolean doInHibernate(Session session) throws HibernateException {

					Transaction tr = session.beginTransaction();
					Query q = session.createQuery("from User where userId = ?");
					q.setString(0, user.getUserId());
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					List<User> li = q.list();

					if (li == null)
						return false;

					boolean flag = passwordEncoder.matches(user.getUserPatternPassword(),
							li.get(0).getUserPatternPassword());
					tr.commit();
					session.flush();
					session.close();
					return flag;
				}
			});
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public User getUser(User user) {

		return ht.execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session session) throws HibernateException {
				try {
					Transaction tr = session.beginTransaction();
					Query q = session.createQuery("from User where userId = ?");
					if (user.getUserId() != null)
						q.setString(0, user.getUserId());
					List<User> li = q.list();
					if (li == null)
						return null;
					else {
						user.setPatternType(li.get(0).getPatternType());
						user.setUserEmail((li.get(0).getUserEmail()));
						tr.commit();
						session.flush();
						session.close();
					}
				} catch (Exception e) {
					System.out.println("user");
				}
				return user;
			}
		});
	}

	@Override
	public boolean resetPattern(User user) {

		try {
			return ht.execute(new HibernateCallback<Boolean>() {

				@Override
				public Boolean doInHibernate(Session session) throws HibernateException {

					Transaction tr = session.beginTransaction();
					session.update(user);
					tr.commit();
					session.flush();
					session.close();
					return true;
				}
			});
		} catch (Exception e) {
			System.out.println("Error in saving!!!");
			return false;
		}
	}
}
