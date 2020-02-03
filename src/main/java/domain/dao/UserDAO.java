package domain.dao;

import domain.HibernateUtils;
import domain.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.NoResultException;

public class UserDAO {
    public static void persist(User user){
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public static User getById(long id) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = (User) session.createQuery("FROM Users u WHERE u.id = :id").setParameter("id", id).getSingleResult();
        } catch (NoResultException nre) {
            System.err.print("No user found by ");
            System.out.println("USER_ID");
        }
        transaction.commit();
        session.close();
        return user;
    }

    public static User getByUsername(String username) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = (User) session
                    .createQuery("FROM Users u WHERE u.username = :username")
                    .setParameter("username", username).getSingleResult();
            transaction.commit();
        } catch (NoResultException e) {
            System.err.print("No user found by ");
            System.out.println("USERNAME");
        } catch (SQLGrammarException e) {
            System.err.println("UserDAO - getByUsername: Query error");
        }
        session.close();
        return user;
    }

    public static void update(User user) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public static void delete(User user) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
