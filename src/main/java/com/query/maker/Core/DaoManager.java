package com.query.maker.Core;

import com.query.maker.Entity;
import com.query.maker.Result;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.apache.commons.beanutils.BeanUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.capitalize;

public class DaoManager
{
    private String entityName = null;
    private SessionFactory sessionFactory = null;

    public void setEntityName(String entityName)
    {
        this.entityName = capitalize(entityName);
    }

    public Entity delete(long id)
    {
        Result result = new Result();
        try {
            Session session = this.session();
            Transaction beginTransaction = session.beginTransaction();
            Query createQuery = session.createQuery("delete from "+this.entityName+" s where s.id =:id");
            createQuery.setParameter("id", id);
            createQuery.executeUpdate();
            beginTransaction.commit();
            return result.setBool(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.setBool(false);
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
        StringBuilder querySql = new StringBuilder();
        criteria(criteria, querySql);
        return query(criteria, limit, "from "+this.entityName+" s "+querySql.toString());
    }

    private List<Entity> queryExec (Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {
        StringBuilder querySql = new StringBuilder();
        querySql = criteria(criteria, querySql);
        querySql = group(group, querySql);
        return query(criteria, limit, "from "+this.entityName+" s "+querySql.toString());
    }

    private StringBuilder criteria (Map<String, Object> criteria, StringBuilder querySql)
    {
        querySql.append(criteriaSql(criteria, new StringBuilder(), "and"));
        querySql.insert(0, "where ");
        return querySql;
    }

    private String criteriaSql (Map<String, Object> criteria, StringBuilder criteriaSql, String operator)
    {
        for (String key: criteria.keySet()){
            String keyName = key.replaceAll("(^KEY[0-9]+)", "");
            String patternNot = "(_not$)";

            if (criteria.get(key) instanceof Map<?,?>) {
                Map<String, Object> orValue = new HashMap<String, Object>();

                try {
                    BeanUtils.populate(orValue, (Map) criteria.get(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                criteriaSql = operator(criteriaSql, key);
                criteriaSql.append("(")
                        .append(this.criteriaSql(orValue, new StringBuilder(), key))
                        .append(")");
            } else if (criteria.get(key) == null && operator.matches(".*"+patternNot)) {
                String operatorCut = key.replaceAll(patternNot, "");
                criteriaSql = operator(criteriaSql, operatorCut);
                criteriaSql.append("s.")
                        .append(keyName)
                        .append(" is not null");
            } else if (criteria.get(key) == null) {
                criteriaSql = operator(criteriaSql, operator);
                criteriaSql.append("s.")
                        .append(keyName)
                        .append(" is null");
            } else if (operator.matches(".*"+patternNot)) {
                String operatorCut = key.replaceAll(patternNot, "");
                criteriaSql = operator(criteriaSql, operatorCut);
                criteriaSql.append("s.")
                        .append(keyName)
                        .append(" != :")
                        .append(key);
            } else {
                criteriaSql = operator(criteriaSql, operator);
                criteriaSql.append("s.")
                        .append(keyName)
                        .append(" = :")
                        .append(key);
            }
        }
        return criteriaSql.toString();
    }

    private StringBuilder operator (StringBuilder criteriaSql, String operator)
    {
        if (!criteriaSql.toString().equals("")) {
            criteriaSql.append(" ")
                    .append(operator)
                    .append(" ");
        }
        return criteriaSql;
    }

    private StringBuilder group(Map<String, String> group, StringBuilder querySql)
    {
        StringBuilder groupSql = new StringBuilder();
        for (String key: group.keySet()){
            if (!groupSql.toString().equals("")) {
                groupSql.append(", ");
            }

            if (group.get(key) == null) {
                groupSql.append("s.")
                        .append(key);
            } else {
                groupSql.append("s.")
                        .append(key)
                        .append(" ")
                        .append(group.get(key));
            }
        }
        if (!groupSql.toString().equals("")) {
            querySql.append(" group by ")
                    .append(groupSql.toString());
        }
        return querySql;
    }

    private List<Entity> query(Map<String, Object> criteria, Integer limit, String queryString)
    {
        Session session = this.session();
        Query query = session.createQuery(queryString);

        if (criteria != null) {
            query = setParameters(query, criteria);
        }

        if (limit != null) {
            query.setMaxResults(limit);
        }


        List<Entity> queryList = new ArrayList<Entity>();
        for (Object o : query.list()) queryList.add((Entity) o);

        if (queryList.isEmpty()) {
            session.clear();
            return null;
        } else {
            session.clear();
            return queryList;
        }

    }

    private Query setParameters(Query query, Map<String, Object> criteria)
    {
        for (String key: criteria.keySet()){
            if (criteria.get(key) instanceof Map<?,?>) {
                Map<String, Object> map = new HashMap<String, Object>();

                try {
                    BeanUtils.populate(map, (Map) criteria.get(key));
                    query = this.setParameters(query, map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (criteria.get(key) != null) {
                query.setParameter(key, criteria.get(key));
            }
        }
        return query;
    }

    private Session session()
    {
        return this.sessionFactory.openSession();
    }

    void createSession(Map<String, String> properties)
    {
        Configuration cfg = new Configuration().configure();
        cfg.setProperty("hibernate.connection.url", properties.get("url"));
        cfg.setProperty("hibernate.connection.username", properties.get("username"));
        cfg.setProperty("hibernate.connection.password", properties.get("password"));
        this.sessionFactory = cfg.buildSessionFactory();
    }

    void closeSession()
    {
        this.sessionFactory.close();
    }
}