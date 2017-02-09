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
    private HibernateConnector connector = HibernateConnector.getInstance();

    public DaoManager setEntityName(String entityName)
    {
        this.entityName = capitalize(entityName);
        return this;
    }

    public List<Entity> findAll()
    {
        try {
            Session session =  connector.getSessionFactory().openSession();
            Query query = session.createQuery("from "+this.entityName+" s");

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

    public List<Entity> findByCriteria(Map<String, Object> criteria, int limit)
    {
        try {
            Query query = queryExec (criteria, limit);
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

    public List<Entity> findByCriteria(Map<String, Object> criteria, int limit, Map<String, String> group)
    {
        try {
            Query query = queryExec (criteria, limit, group);
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

    private Query queryExec (Map<String, Object> criteria, int limit)
    {
        String querySql = "";
        querySql = criteria(criteria, querySql);
        return query(criteria, limit, querySql);
    }

    private Query queryExec (Map<String, Object> criteria, int limit, Map<String, String> group)
    {
        String querySql = "";
        querySql = criteria(criteria, querySql);
        querySql = group(group, querySql);
        return query(criteria, limit, querySql);
    }

    private String criteria (Map<String, Object> criteria, String querySql)
    {
        String criteriaSql = "";
        for (Object key: criteria.keySet()){
            if (criteriaSql != "") {
                criteriaSql += " and ";
            }

            if (criteria.get(key) == null) {
                criteriaSql += "s." + key.toString() + " is null";
            } else {
                criteriaSql += "s." + key.toString() + " = :" + key.toString();
            }
        }
        querySql += criteriaSql;
        return querySql;
    }

    private String group (Map<String, String> group, String querySql)
    {
        String groupSql = "";
        for (Object key: group.keySet()){
            if (groupSql != "") {
                groupSql += ", ";
            }

            if (group.get(key) == null) {
                groupSql += "s." + key.toString();
            } else {
                groupSql += "s." + key.toString() + " " + group.get(key);
            }
        }
        if (!groupSql.equals("")) {
            querySql += " group by " + groupSql;
        }
        return querySql;
    }

    private Query query (Map<String, Object> criteria, int limit, String querySql)
    {
        Session session =  connector.getSessionFactory().openSession();
        Query query = session.createQuery("from "+this.entityName+" s where "+querySql);
        for (Object key: criteria.keySet()){
            if (criteria.get(key) != null) {
                query.setParameter(key.toString(), criteria.get(key));
            }
        }
        session.flush();
        session.clear();
        session.close();
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