package com.query.maker.Config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate
{
    private Configuration cfg;
    private SessionFactory sessionFactory;

    public SessionFactory sessionFactory() throws HibernateException
    {
        this.cfg = new Configuration().configure();
        this.sessionFactory = this.cfg.buildSessionFactory();
        return this.sessionFactory;
    }

    /*public Session newSession() throws HibernateException
    {
        Session session = this.sessionFactory.openSession();
        if (!session.isConnected()) {
            this.reconnect();
        }
        return session;
    }

    private void reconnect() throws HibernateException
    {
        this.sessionFactory = this.cfg.buildSessionFactory();
    }*/
}
