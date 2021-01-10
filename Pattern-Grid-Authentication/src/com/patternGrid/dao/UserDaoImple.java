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
		}catch (Exception e) {
			System.out.println("Error in saving!!!");
			return false;
		}
	}


	@Override
	public boolean isUserValid(User user) {
		try
		{
		return ht.execute(new HibernateCallback<Boolean>() {

			@Override
			public Boolean doInHibernate(Session session) throws HibernateException {
				
				Transaction tr = session.beginTransaction();
				Query q = session.createQuery("from User where userId = ? and userPatternPassword = ?");
				q.setString(0, user.getUserId());
				q.setString(1, user.getUserPatternPassword());
				List<User> li = q.list();
				boolean flag = !li.isEmpty();
				tr.commit();
				session.flush();
				session.close();
				return flag;
			}	
		});
	}catch (Exception e) {
		return  false;
		
	}
 }
}


