package com.query.maker.Core;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.query.maker.Entity;
import com.query.maker.Result;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.apache.commons.beanutils.BeanUtils;
import static org.springframework.util.StringUtils.capitalize;

public class DaoManager
{
    private String entityName = null;
    private SessionFactory sessionFactory = null;

    /**
     * Set the entity name
     *
     * @param entityName name of entity
     */
    public void setEntityName(String entityName)
    {
        this.entityName = capitalize(entityName);
    }

    /**
     * Delete the line in database
     *
     * @param id line id
     * @return Result Object with boolean
     */
    public Entity delete(long id)
    {
        Result result = new Result();
        Session session = this.session();
        Transaction beginTransaction = session.beginTransaction();
        StringBuilder query = new StringBuilder()
                .append("delete from ")
                .append(this.entityName)
                .append(" s where s.id =:id");
        Query createQuery = session.createQuery(query.toString());
        createQuery.setParameter("id", id);
        int queryResult = createQuery.executeUpdate();
        beginTransaction.commit();


        if (queryResult > 0) {
            return result.setBool(true);
        }
        return result.setBool(false);
    }

    /**
     * Insert data in database
     *
     * @param className entity to create
     * @param input data inserted
     *
     * @return Entity created
     */
    public Entity insert(Class className, String input)
    {
        Entity entity = (Entity) new Gson()
                .fromJson(input, className);

        Session session = this.session();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(entity);
        transaction.commit();

        if (id != null && id > 0) {
            return entity;
        }
        return null;
    }

    /**
     * update the line in the database
     *
     * @param className entity to update
     * @param input data inserted
     *
     * @return Entity updated
     */
    public Entity update(Class className, String input)
    {
        Entity entity = (Entity) new Gson()
                .fromJson(input, className);

        Session session = this.session();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();

        return entity;
    }

    /**
     * Select lines in the database
     *
     * @return Entity List
     */
    public List<Entity> findAll()
    {
        try {
            return queryExec();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Select lines in the database
     *
     * @param criteria defined Criteria
     * @param limit defined limit
     *
     * @return Entity List
     */
    public List<Entity> findByCriteria(Map<String, Object> criteria, Integer limit)
    {
        try {
            return queryExec(criteria, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Select lines in the database
     *
     * @param criteria defined Criteria
     * @param limit defined limit
     * @param group defined Group
     *
     * @return Entity List
     */
    public List<Entity> findByCriteria(Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {
        try {
            return queryExec(criteria, limit, group);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Finalise the findAll query
     *
     * @return Entity List
     */
    private List<Entity> queryExec ()
    {
        return query(null, null, "from "+this.entityName+" s");
    }

    /**
     * Finalise the findByCriteria query
     *
     * @param criteria defined Criteria
     * @param limit defined limit
     *
     * @return Entity List
     */
    private List<Entity> queryExec (Map<String, Object> criteria, Integer limit)
    {
        StringBuilder querySql = new StringBuilder();
        criteria(criteria, querySql);
        return query(criteria, limit, "from "+this.entityName+" s "+querySql.toString());
    }

    /**
     * Finalise the findByCriteria query
     *
     * @param criteria defined Criteria
     * @param limit defined limit
     * @param group defined Group
     *
     * @return Entity List
     */
    private List<Entity> queryExec (Map<String, Object> criteria, Integer limit, Map<String, String> group)
    {
        StringBuilder querySql = new StringBuilder();
        querySql = criteria(criteria, querySql);
        querySql = group(group, querySql);
        return query(criteria, limit, "from "+this.entityName+" s "+querySql.toString());
    }

    /**
     * Prepare the sql request
     *
     * @param criteria defined Criteria
     * @param querySql request content
     *
     * @return StringBuilder request
     */
    private StringBuilder criteria (Map<String, Object> criteria, StringBuilder querySql)
    {
        querySql.append(criteriaSql(criteria, new StringBuilder(), "and"));
        querySql.insert(0, "where ");
        return querySql;
    }

    /**
     * Prepare the sql request
     * with Criteria
     *
     * @param criteria defined Criteria
     * @param criteriaSql request Criteria content
     * @param operator defined operator between request parts
     *
     * @return String request part
     */
    @SuppressWarnings("unchecked")
    private String criteriaSql (Map<String, Object> criteria, StringBuilder criteriaSql, String operator)
    {
        for (String key: criteria.keySet()){
            String keyName = key.replaceAll("(^KEY[0-9]+)", "");
            String patternNot = "(_not$)";

            if (criteria.get(key) instanceof Map<?,?>) {
                Map<String, Object> orValue = (Map) criteria.get(key);
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

    /**
     * Concatenate request
     * with operator
     *
     * @param criteriaSql request Criteria content
     * @param operator defined operator between request parts
     *
     * @return StringBuilder request part
     */
    private StringBuilder operator (StringBuilder criteriaSql, String operator)
    {
        if (!criteriaSql.toString().equals("")) {
            criteriaSql.append(" ")
                    .append(operator)
                    .append(" ");
        }
        return criteriaSql;
    }

    /**
     * Concatenate request
     * with Group
     *
     * @param group defined Group
     * @param querySql request content
     *
     * @return StringBuilder request
     */
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

    /**
     * Run the database request
     *
     * @param criteria defined criteria
     * @param limit defined limit
     * @param queryString request content
     *
     * @return Entity List
     */
    @SuppressWarnings("unchecked")
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

        List<Entity> queryList = (List) query.list();
        if (queryList == null || queryList.isEmpty()) {
            session.clear();
            return null;
        } else {
            session.clear();
            return queryList;
        }
    }

    /**
     * Add value parameters
     *
     * @param query Query object
     * @param criteria defined criteria
     *
     * @return updated Query
     */
    @SuppressWarnings("unchecked")
    private Query setParameters(Query query, Map<String, Object> criteria)
    {
        for (String key: criteria.keySet()){
            if (criteria.get(key) instanceof Map<?,?>) {
                query = this.setParameters(query, (Map) criteria.get(key));
            } else if (criteria.get(key) != null) {
                query.setParameter(key, criteria.get(key));
            }
        }
        return query;
    }

    /**
     * Open the session with database
     *
     * @return Session object
     */
    private Session session()
    {
        return this.sessionFactory.openSession();
    }

    /**
     * Create the session with database
     *
     * @param properties database properties
     */
    void createSession(Map<String, String> properties)
    {
        Configuration cfg = new Configuration().configure();
        cfg.setProperty("hibernate.connection.url", properties.get("url"));
        cfg.setProperty("hibernate.connection.username", properties.get("username"));
        cfg.setProperty("hibernate.connection.password", properties.get("password"));
        this.sessionFactory = cfg.buildSessionFactory();
    }

    /**
     * Close the session with database
     */
    void closeSession()
    {
        this.sessionFactory.close();
    }
}