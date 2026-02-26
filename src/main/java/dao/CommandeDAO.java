package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bo.Commande;
import bo.Ligne_Commande;

public class CommandeDAO {

	public void create(Commande commande) {

		Transaction tx = null;

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			session.save(commande);

			for (Ligne_Commande l : commande.getLignes()) {
				l.setCommande(commande);
				session.saveOrUpdate(l);
			}

			tx.commit();
			session.close();

		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	// ===============================
	// NOUVELLE METHODE
	// ===============================

	public List<Commande> findAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Commande> commandes = session.createQuery(
				"select distinct c from Commande c " +
						"left join fetch c.client " +
						"left join fetch c.lignes",
				Commande.class
		).list();

		session.close();

		return commandes;
	}
}