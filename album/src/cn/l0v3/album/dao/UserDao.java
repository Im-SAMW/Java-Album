package cn.l0v3.album.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cn.l0v3.album.vo.*;

public class UserDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public User login(User user) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer userState = 0;
		try {
			tx = session.beginTransaction();
			session.clear();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userName", user.getUserName()));
			cr.add(Restrictions.eq("passWord",user.getPassWord()));
			List<User> list = cr.list();
			
			userState = list.get(0).getUserID();
			tx.commit();
			if(userState > 0) {
//				return userState;
				return list.get(0);
			}
		} catch (HibernateException e) {
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
    }
	
	public boolean register(User user) {
		Session session = null;
		Transaction tx = null;
		Integer userID = 0;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			userID = (Integer) session.save(user);
			tx.commit();
			if(userID > 0) {
				return true;
			}
		} catch (HibernateException e) {
			if (tx != null) { // 如果存在事务，则回滚
				tx.rollback();
			}
			throw e; // 抛出异常
		} finally {
			session.close();
		}
		return false;
	}
	
	public void add(User user) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) { // 如果存在事务，则回滚
				tx.rollback();
			}
			throw e; // 抛出异常
		} finally {
			session.close();
		}
	}
	
	public User get(Class clazz, Serializable id) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			User user = (User)session.get(clazz, id);
			return user;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public static void main(String[] args) {
		UserDao l =new UserDao();
		System.out.println(l.login(new User("sam","sam")));
	}
}
