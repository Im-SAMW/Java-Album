package cn.l0v3.album.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cn.l0v3.album.vo.Image;
import cn.l0v3.album.vo.Photo;
import cn.l0v3.album.vo.User;

public class PhotoDao {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List list(User user) {
		
		Session session = sessionFactory.openSession();
		session.clear();
		User user2 = (User)session.get(User.class, user.getUserID());
	
		List photos = user2.getPhotos();
		
		session.close();
		
		return photos;
    }

	public boolean addPhoto(Photo photo) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer photoID = 0;
		try {
			tx = session.beginTransaction();
			session.clear();
			photoID = (Integer)session.save(photo);
			tx.commit();
			if(photoID > 0) {
				return true;
			}
		} catch (HibernateException e) {
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return false;
	}

	public boolean updatePhotoImage(String fileName, int photoID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.clear();
			
			String hql = 	"UPDATE Photo set photoImage = :photo_image "  + 
		             		"WHERE photoID = :photo_id";
			Query query = session.createQuery(hql);
			query.setParameter("photo_image", fileName);
			query.setParameter("photo_id", photoID);
			int result = query.executeUpdate();
			
			tx.commit();
			if(result > 0) {
				return true;
			}
		} catch (HibernateException e) {
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return false;
	}	
		

}
