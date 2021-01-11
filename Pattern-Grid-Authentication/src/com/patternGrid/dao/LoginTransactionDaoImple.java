package com.patternGrid.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.patternGrid.dto.LoginTransaction;

@Repository
public class LoginTransactionDaoImple implements LoginTransactionDao {

	@Autowired
	HibernateTemplate ht;

	@Override
	public boolean loginTransaction(LoginTransaction transaction) {

		return ht.execute(new HibernateCallback<Boolean>() {

			@Override
			public Boolean doInHibernate(Session session) throws HibernateException {
				try {
					Query query = session.createSQLQuery(
							"INSERT INTO login_transaction (Login_Time, Session_Id, Status, User_Id) VALUES (now(),?,?,?)");
					query.setParameter(0, transaction.getSessionId());
					query.setParameter(1, transaction.isStatus());
					query.setParameter(2, transaction.getUser().getUserId());
					query.executeUpdate();
					return true;
				} catch (Exception e) {
					System.err.println(e);
					return false;
				}

			}
		});
	}

	@Override
	public boolean logoutTransaction(LoginTransaction transaction) {
		return ht.execute(new HibernateCallback<Boolean>() {

			@Override
			public Boolean doInHibernate(Session session) throws HibernateException {
				try {
					Query query = session.createSQLQuery(
							"UPDATE login_transaction SET Logut_Time = now(), status = ? WHERE (User_Id = ? AND Session_Id = ?)");
					query.setParameter(0, transaction.isStatus());
					query.setParameter(1, transaction.getUser().getUserId());
					query.setParameter(2, transaction.getSessionId());
					query.executeUpdate();
					return true;
				} catch (Exception e) {
					System.err.println(e);
					return false;
				}

			}
		});
	}

}
