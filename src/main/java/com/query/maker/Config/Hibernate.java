package com.query.maker.Config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by sdugene on 13/02/17.
 */
public class Hibernate
{
    private Configuration cfg;
    private SessionFactory sessionFactory;

    public void Hibernate() throws HibernateException
    {
        cfg = new Configuration().configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    public Session newSession() throws HibernateException
    {
        Session session = sessionFactory.openSession();
        if (!session.isConnected()) {
            this.reconnect();
        }
        return session;
    }

    private void reconnect() throws HibernateException
    {
        this.sessionFactory = cfg.buildSessionFactory();
    }
}
