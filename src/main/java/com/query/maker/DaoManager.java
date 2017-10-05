package com.query.maker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Table;

import static org.springframework.util.StringUtils.capitalize;

class DaoManager
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
     * Truncate Table
     *
     * @return Result Object with boolean
     */
    public Entity truncate(Entity entity)
    {
        Class<?> c = entity.getClass();
        Table table = c.getAnnotation(Table.class);

        Result result = new Result();
        Session session = this.session();
        StringBuilder query = new StringBuilder();
        query.append("truncate table ");
        query.append(table.name());

        session.createSQLQuery(query.toString()).executeUpdate();
        return result.setBool(true);
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
        session.save(entity);
        transaction.commit();

        return entity;
    }

    /**
     * update the line in the database
     *
     * @param entity Entity to update
     * @param input data inserted
     *
     * @return Entity updated
     */
    public Entity update(Entity entity, Input input)
    {
        Map<String, Object> map = entity.toMap();
        map.putAll(input.getValues());

        Entity entityNew = new Gson()
                .fromJson(new Gson().toJson(map), entity.getClass());

        Session session = this.session();
        Transaction transaction = session.beginTransaction();
        session.update(entityNew);
        transaction.commit();

        return entityNew;
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
    public List<Entity> findByCriteria(Criteria criteria, Integer limit)
    {
        return queryExec(criteria, limit);
    }

    /**
     * Select lines in the database
     *
     * @param criteria defined Criteria
     * @param limit defined limit
     * @param group defined Group
     * @param order defined Order
     *
     * @return Entity List
     */
    public List<Entity> findByCriteria(Criteria criteria, Integer limit, Group group, Order order)
    {

        return queryExec(criteria, limit, group, order);
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
    private List<Entity> queryExec (Criteria criteria, Integer limit)
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
    private List<Entity> queryExec (Criteria criteria, Integer limit, Group group, Order order)
    {
        StringBuilder querySql = new StringBuilder();
        querySql = criteria(criteria, querySql);
        querySql = group(group, querySql);
        querySql = order(order, querySql);
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
    private StringBuilder criteria (Criteria criteria, StringBuilder querySql)
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
    private String criteriaSql (Criteria criteria, StringBuilder criteriaSql, String operator)
    {
        StringBuilder newCriteriaSql = criteriaSql;
        for (Map.Entry<String,Object> entry : criteria.getValues().entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            String keyName = key.replaceAll("(^KEY[0-9]+)", "");
            String patternNot = "(_not$)";

            if (value instanceof Map<?,?>) {
                Criteria orValue = new Criteria().setValues((Map) value);
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
    private StringBuilder group(Group group, StringBuilder querySql)
    {
        if (group == null || group.getValues().isEmpty()) {
            return querySql;
        }

        StringBuilder groupSql = new StringBuilder();
        for (Map.Entry<String,String> entry : group.getValues().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == null && !"".equals(groupSql.toString())) {
                groupSql.append(", ");
            }

            if (value == null) {
                groupSql.append("s.")
                        .append(key);
            }
        }
        if (!"".equals(groupSql.toString())) {
            querySql.append(" group by ")
                    .append(groupSql.toString());
        }
        return querySql;
    }

    /**
     * Concatenate request
     * with Order
     *
     * @param order defined Order
     * @param querySql request content
     *
     * @return StringBuilder request
     */
    private StringBuilder order(Order order, StringBuilder querySql)
    {
        if (order == null || order.getValues().isEmpty()) {
            return querySql;
        }

        StringBuilder orderSql = new StringBuilder();
        for (Map.Entry<String,String> entry : order.getValues().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!"".equals(orderSql.toString())) {
                orderSql.append(", ");
            }

            orderSql.append("s.")
                    .append(key)
                    .append(" ")
                    .append(value);
        }
        if (!"".equals(orderSql.toString())) {
            querySql.append(" order by ")
                    .append(orderSql.toString());
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
    private List<Entity> query(Criteria criteria, Integer limit, String queryString)
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
        if (queryList.isEmpty()) {
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
    private Query setParameters(Query query, Criteria criteria)
    {
        Query newQuery = query;
        for (Map.Entry<String,Object> entry : criteria.getValues().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map<?,?>) {
                newQuery = this.setParameters(query, new Criteria().setValues((Map) value));
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