package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bo.Client;
import bo.Produit;

public class ProduitDAO {
public List<Produit> retreive(){
	Transaction tx=null;
List<Produit> liste=new ArrayList<Produit>();
		try {
		Session session=HibernateUtil.getSessionFactory().openSession();
		tx=session.beginTransaction();
		liste=session.createQuery("From Produit P where P.qtstock>0").list();
		tx.commit();
		session.close();
		return liste;
		
		
	}
	catch(HibernateException e) {
		tx.rollback();
		e.printStackTrace();
		return liste;
	
	}

	
}
public void update(Produit produit){
	Transaction tx=null;

		try {
		Session session=HibernateUtil.getSessionFactory().openSession();
		tx=session.beginTransaction();
		session.update(produit);
		
		tx.commit();
		session.close();
		System.out.println("====================Produit modifié"+produit.getId() );
		
		
		
	}
	catch(HibernateException e) {
		tx.rollback();
		e.printStackTrace();
		
	
	}

	
}

public Produit findById(int id) {
    Transaction tx = null;
    Produit p = null;
    try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        p = session.find(Produit.class, id);
        tx.commit();
        session.close();
        return p;
    } catch (HibernateException e) {
        tx.rollback();
        e.printStackTrace();
        return p;
    }
}

public void create(Produit produit) {
    Transaction tx = null;
    try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        session.save(produit);
        tx.commit();
        session.close();
    } catch (HibernateException e) {
        tx.rollback();
        e.printStackTrace();
    }
}

public boolean delete(Produit produit) {
    Transaction tx = null;
    try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        session.delete(produit);
        tx.commit();
        session.close();
        return true;
    } catch (HibernateException e) {
        tx.rollback();
        return false;
    }
}
}
