package com.query.maker.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.query.maker.Entity;
import com.query.maker.Input;
import com.query.maker.Result;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import static org.springframework.util.StringUtils.capitalize;

public class DaoManager
{
    private String entityName = null;
    private SessionFactory sessionFactory = null;

    private static final String FROM = "from";

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
    public Entity insert(Class className, Input input)
    {
        Entity entity = (Entity) new Gson()
                .fromJson(input.toJSONString(), className);

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
     * @param entity Entity to update
     * @param input data inserted
     *
     * @return Entity updated
     */
    public Entity update(Entity entity, Map<String, Object> input)
    {
        Map<String, Object> map = entity.toMap();
        map.putAll(input);

        Entity entityJr = new Gson()
                .fromJson(new Gson().toJson(map), entity.getClass());

        Session session = this.session();
        Transaction transaction = session.beginTransaction();
        session.update(entityJr);
        transaction.commit();

        return entityJr;
    }

    /**
     * Select lines in the database
     *
     * @return Entity List
     */
    public List<Entity> findAll()
    {
        return queryExec();
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
        return queryExec(criteria, limit);
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
        return queryExec(criteria, limit, group);
    }

    /**
     * Finalise the findAll query
     *
     * @return Entity List
     */
    private List<Entity> queryExec ()
    {
        return query(null, null, FROM+" "+this.entityName+" s");
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
        return query(criteria, limit, FROM+" "+this.entityName+" s "+querySql.toString());
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
        return query(criteria, limit, FROM+" "+this.entityName+" s "+querySql.toString());
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
        StringBuilder newCriteriaSql = criteriaSql;
        for (Map.Entry<String,Object> entry : criteria.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            String keyName = key.replaceAll("(^KEY[0-9]+)", "");
            String patternNot = "(_not$)";

            if (value instanceof Map<?,?>) {
                Map<String, Object> orValue = (Map) value;
                newCriteriaSql = operator(criteriaSql, key);
                newCriteriaSql.append("(")
                        .append(this.criteriaSql(orValue, new StringBuilder(), key))
                        .append(")");
            } else if (value == null && operator.matches(".*"+patternNot)) {
                String operatorCut = key.replaceAll(patternNot, "");
                newCriteriaSql = operator(criteriaSql, operatorCut);
                newCriteriaSql.append("s.")
                        .append(keyName)
                        .append(" is not null");
            } else if (value == null) {
                newCriteriaSql = operator(criteriaSql, operator);
                newCriteriaSql.append("s.")
                        .append(keyName)
                        .append(" is null");
            } else if (operator.matches(".*"+patternNot)) {
                String operatorCut = key.replaceAll(patternNot, "");
                newCriteriaSql = operator(criteriaSql, operatorCut);
                newCriteriaSql.append("s.")
                        .append(keyName)
                        .append(" != :")
                        .append(key);
            } else {
                newCriteriaSql = operator(criteriaSql, operator);
                newCriteriaSql.append("s.")
                        .append(keyName)
                        .append(" = :")
                        .append(key);
            }
        }
        return newCriteriaSql.toString();
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
        if (!"".equals(criteriaSql.toString())) {
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
        for (Map.Entry<String,String> entry : group.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!"".equals(groupSql.toString())) {
                groupSql.append(", ");
            }

            if (value == null) {
                groupSql.append("s.")
                        .append(key);
            } else {
                groupSql.append("s.")
                        .append(key)
                        .append(" ")
                        .append(value);
            }
        }
        if (!"".equals(groupSql.toString())) {
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
            return new ArrayList<Entity>();
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
        Query newQuery = query;
        for (Map.Entry<String,Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map<?,?>) {
                newQuery = this.setParameters(query, (Map) value);
            } else if (value != null) {
                newQuery.setParameter(key, value);
            }
        }
        return newQuery;
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