package domain.dao;

import domain.HibernateUtils;
import domain.model.Game;
import domain.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GameDAO {
    public static void persist(Game game){
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(game);
        transaction.commit();
        session.close();
    }

    public static User getById(long id) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.createQuery("FROM Users u WHERE u.id = :id").setParameter("id", id).getSingleResult();
        transaction.commit();
        session.close();
        return user;
    }


    public static void update(Game game) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(game);
        transaction.commit();
        session.close();
    }

    public static void delete(Game game) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(game);
        transaction.commit();
        session.close();
    }
}
