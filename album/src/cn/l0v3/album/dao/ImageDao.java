package cn.l0v3.album.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cn.l0v3.album.vo.*;

public class ImageDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean image(Image image, int photoID) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer imageID = 0;
		
		//session.delete(photo);
		try {
			tx = session.beginTransaction();
			session.clear();
			Photo photo = (Photo) session.get(Photo.class, photoID);
			image.setPhoto(photo);
			imageID = (Integer)session.save(image);
			tx.commit();
			if(imageID > 0) {
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
	
	public List list(User user) {
		
		Session session = sessionFactory.openSession();
		session.clear();
		User user2 = (User)session.get(User.class, user.getUserID());
	
		List images = user2.getImages();
//		session.get(Photo.class, PhotoID)
		
		session.close();
		
		return images;
    }
	
	public List listPhotoImage(int photoID) {
		
		Session session = sessionFactory.openSession();
		session.clear();
		Photo photo = (Photo)session.get(Photo.class, photoID);
	
		List images = photo.getImages();
		session.close();
		
		return images;
	}

	public boolean deleteImage(int imageID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		//session.delete(photo);
		try {
			tx = session.beginTransaction();
			session.clear();
			
			String hql = 	"DELETE FROM Image " + 
		             		"WHERE imageID = :image_id";
			Query query = session.createQuery(hql);
			query.setParameter("image_id", imageID);
			int result = query.executeUpdate();
			
			System.out.println("Rows affected: " + result);
			
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
