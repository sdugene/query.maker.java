package com.query.maker.Core;

import com.query.maker.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
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

    public Entity update(Entity entity, Map<String, Object> input)
    {
        try {
            BeanUtils.populate(entity, input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Session session = this.session();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();

        return entity;
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

    private String criteria (Map<String, Object> criteria, String querySql)
    {
        System.out.println(criteria);
        querySql += criteriaSql(criteria, "", "and");
        return "where "+querySql;
    }

    private String criteriaSql (Map<String, Object> criteria, String criteriaSql, String operator)
    {

        System.out.println(criteriaSql);
        for (String key: criteria.keySet()){
            String keyName = key.replaceAll("(^[0-9]+KY)", "");

            if (criteria.get(key) == null) {
                criteriaSql += operator(criteriaSql, operator);
                criteriaSql += "s." + keyName.toString() + " is null";
                System.out.println(criteriaSql);
            } else if (criteria.get(key) instanceof Map<?,?>) {
                Map<String, Object> orValue;
                orValue = (Map) criteria.get(key);
                criteriaSql += operator(criteriaSql, key.toString());
                criteriaSql += "("+this.criteriaSql(orValue, "", key)+")";
                System.out.println(criteriaSql);
            } else {
                criteriaSql += operator(criteriaSql, operator);
                criteriaSql += "s." + keyName.toString() + " = :" + key.toString();
                System.out.println(criteriaSql);
            }
        }
        System.out.println(criteriaSql);
        return criteriaSql;
    }

    private String operator (String criteriaSql, String operator)
    {
        if (criteriaSql != "") {
            criteriaSql += " "+operator+" ";
        }
        return criteriaSql;
    }

    private String joinCriteria (Map<String, String> joinEntity, Map<String, Object> joinCriteria, String querySql)
    {
        String joinSql = "";
        String EntityName = (String) joinEntity.keySet().toArray()[0];
        String Entity = EntityName.substring(0, 1).toLowerCase() + EntityName.substring(1);
        String method = joinEntity.get(EntityName);

        for (Object key: joinCriteria.keySet()){
            if (key != "ON" && key != "on") {
                if (joinSql != "") {
                    joinSql += " and ";
                }
                joinSql += "t." + key.toString() + " = :" + key.toString();
            }
        }
        querySql += joinSql;
        return method+" JOIN s."+Entity+" t where "+querySql;
    }

    private String group(Map<String, String> group, String querySql)
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

    /*public void deleteUser(int id) {
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