package com.query.maker.Core;

import com.query.maker.Config.HibernateConnector;
import com.query.maker.Entity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.capitalize;

public class DaoManager
{
    private String entityName = null;
    private Session session = HibernateConnector.getInstance().getSession();

    public DaoManager setEntityName(String entityName)
    {
        this.entityName = capitalize(entityName);
        return this;
    }

    public List<Entity> findAll()
    {
        try {
            Query query = this.session.createQuery("from "+this.entityName+" s");

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<Entity>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> findByCriteria(Map<String, String> criteria, int limit)
    {
        try {
            Query query = criteria (criteria, limit);
            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<Entity>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Query criteria (Map<String, String> criteria, int limit)
    {
        String querySql = "";
        for (Object key: criteria.keySet()){
            querySql += "s."+key.toString()+" = :"+key.toString();
        }
        Query query = this.session.createQuery("from "+this.entityName+" s where "+querySql);
        for (Object key: criteria.keySet()){
            String type = getAttribute(key.toString());
            if (type.equals("long")) {
                query.setParameter(key.toString(), Long.parseLong(criteria.get(key)));
            } else {
                query.setParameter(key.toString(), criteria.get(key));
            }
        }

        query.setMaxResults(limit);
        return query;
    }

    /*public void updateUser(User user) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            session.saveOrUpdate(user);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User addUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            System.out.println("session : "+session);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUser(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Transaction beginTransaction = session.beginTransaction();
            Query createQuery = session.createQuery("delete from User s where s.id =:id");
            createQuery.setParameter("id", id);
            createQuery.executeUpdate();
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private String getAttribute(String name)
    {
        try {
            Class entityName = Class.forName("com.siteoffice.Entities."+capitalize(this.entityName));
            Field f_titre = entityName.getDeclaredField(name);
            Class type = f_titre.getType();
            return type.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}