package com.query.maker.Core;

import com.query.maker.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.capitalize;

public class DaoManager
{
    private String entityName = null;
    private SessionFactory sessionFactory = null;


    public DaoManager setEntityName(String entityName)
    {
        this.entityName = capitalize(entityName);
        return this;
    }

    public Entity insert(Entity entity, Map<String, Object> input)
    {
        try {
            BeanUtils.populate(entity, input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Session session = this.session();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(entity);
        transaction.commit();

        if (id != null && id > 0) {
            return entity;
        }
        return null;
    }

    public List<Entity> findAll()
    {
        try {
            return queryExec();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> findByCriteria(Map<String, Object> criteria, Integer limit)
    {
        try {
            return queryExec(criteria, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> findByCriteria(Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {
        try {
            return queryExec(criteria, limit, group);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> findWithJoin(Map<String, Object> joinCriteria, Map<String, Object> criteria, Integer limit)
    {
        try {
            return queryExec(joinCriteria, criteria, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> findWithJoin(Map<String, Object> joinCriteria, Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {
        try {
            return queryExec(joinCriteria, criteria, limit, group);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Entity> queryExec ()
    {
        return query(null, null, "from "+this.entityName+" s");
    }

    private List<Entity> queryExec (Map<String, Object> criteria, Integer limit)
    {
        String querySql = "";
        querySql = criteria(criteria, querySql);
        return query(criteria, limit, "from "+this.entityName+" s "+querySql);
    }

    private List<Entity> queryExec (Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {
        String querySql = "";
        querySql = criteria(criteria, querySql);
        querySql = group(group, querySql);
        return query(criteria, limit, "from "+this.entityName+" s "+querySql);
    }

    private List<Entity> queryExec (Map<String, Object> joinCriteria, Map<String, Object> criteria, Integer limit)
    {
        String querySql = "";
        querySql = joinCriteria(joinCriteria, querySql);
        if (criteria == null) {
            return query(criteria, limit, "from "+this.entityName+" s "+querySql);
        } else {
            querySql = criteria(criteria, querySql);
            return query(criteria, limit, "from "+this.entityName+" s "+querySql);
        }
    }

    private List<Entity> queryExec (Map<String, Object> joinCriteria, Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {

        String querySql = "";
        querySql = joinCriteria(joinCriteria, querySql);
        if (criteria == null) {
            querySql = group(group, querySql);
            return query(criteria, limit, "from "+this.entityName+" s "+querySql);
        } else {
            querySql = criteria(criteria, querySql);
            querySql = group(group, querySql);
            return query(criteria, limit, "from "+this.entityName+" s "+querySql);
        }
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
        return "where "+querySql;
    }

    private String joinCriteria (Map<String, Object> joinCriteria, String querySql)
    {
        System.out.println(joinCriteria);
        String joinSql = "";
        String method = (String) joinCriteria.keySet().toArray()[0];
        Map<String, Object> joinOn = (Map) joinCriteria.get(method);
        System.out.println(joinOn);
        String EntityName = (String) joinOn.keySet().toArray()[0];
        String Entity = EntityName.substring(0, 1).toLowerCase() + EntityName.substring(1);
        Map<String, Object> criteria = (Map) joinOn.get(EntityName);
        System.out.println(criteria);

        if (criteria.get("on") != null || (criteria.get("on") == null && criteria.containsKey("on"))) {
            joinSql += "on t." + criteria.get("on").toString() + " = s." + criteria.get("on").toString();
        } else if (criteria.get("ON") != null || (criteria.get("ON") == null && criteria.containsKey("ON"))) {
            joinSql += "on t." + criteria.get("ON").toString() + " = s." + criteria.get("ON").toString();
        }

        for (Object key: criteria.keySet()){
            if (key != "ON" && key != "on") {
                if (joinSql != "") {
                    joinSql += " and ";
                }
                joinSql += "t." + key.toString() + " = :" + key.toString();
            }
        }
        querySql += joinSql;
        //return method+" JOIN s."+table+" as t "+querySql;
        return method+" JOIN s."+Entity+" as t ";
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

    private List<Entity> query(Map<String, Object> criteria, Integer limit, String queryString)
    {
        Session session = this.session();
        Query query = session.createQuery(queryString);

        if (criteria != null) {
            for (Object key: criteria.keySet()){
                if (criteria.get(key) != null) {
                    query.setParameter(key.toString(), criteria.get(key));
                }
            }
        }

        if (limit != null) {
            query.setMaxResults(limit);
        }

        List queryList = query.list();
        if (queryList != null && queryList.isEmpty()) {
            session.clear();
            return null;
        } else {
            session.clear();
            return (List<Entity>) queryList;
        }

    }

    /*private Query query (Map<String, Object> criteria, int limit, String querySql)
    {
        Query query = this.session().createQuery("from "+this.entityName+" s where "+querySql);
        for (Object key: criteria.keySet()){
            if (criteria.get(key) != null) {
                query.setParameter(key.toString(), criteria.get(key));
            }
        }

        query.setMaxResults(limit);
        return query;
    }*/

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

    private Session session()
    {
        return this.sessionFactory.openSession();
    }

    public void createSession()
    {
        Configuration cfg = new Configuration().configure();
        this.sessionFactory = cfg.buildSessionFactory();
    }

    public void closeSession()
    {
        this.sessionFactory.close();
    }
}