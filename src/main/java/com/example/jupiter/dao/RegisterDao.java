package com.example.jupiter.dao;

import com.example.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;

@Repository
public class RegisterDao {

    @Autowired
    private SessionFactory sessionFactory;
    //Once a user is authenticated, the server uses a session to maintain his/her status. The session object is stored on the server-side, only session ID is returned to the client-side. Users need to provide a session ID to access resources that require authentication.
    //When a user logs out, the session is destroyed. Next time a user comes, he/she has to authenticate again to get a new session.
    //https://sherryhsu.medium.com/session-vs-token-based-authentication-11a6c5ac45e4
    public boolean register(User user) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (PersistenceException | IllegalStateException ex) {
            // if hibernate throws this exception, it means the user already be register
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
        return true;
    }
}

