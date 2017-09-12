package com.query.maker;

import com.query.maker.core.*;

import java.util.ArrayList;
import java.util.List;

public class QueryMaker extends QueryCore
{
    private static final String SELECT = "select";
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";
    private static final String TRUNCATE = "truncate";
    private static final String OPERATIONS = "operations";

    /**
     * Default constructor
     */
    private QueryMaker() {}

    /**
     * Singleton holder
     */
    private static class SingletonHolder
    {
        private static final QueryMaker instance = new QueryMaker();

        private SingletonHolder()
        {
            throw new IllegalStateException("Utility class");
        }
    }

    /**
     * @return QueryMaker instance
     */
    public static QueryMaker getInstance()
    {
        return QueryMaker.SingletonHolder.instance;
    }

    /**
     * clear values
     */
    @Override
    public QueryMaker clean()
    {
        super.clean();
        return this;
    }

    /**
     * Set the method as "select"
     *
     * @param entity selected Entity
     * @return QueryMaker
     */
    public QueryMaker select(Entity entity)
    {
        super.clean();
        this.setEntity(entity);
        this.method = SELECT;
        return this;
    }

    /**
     * Set the method as "delete"
     *
     * @param entity deleted Entity
     * @return QueryMaker
     */
    public QueryMaker delete(Entity entity)
    {
        super.clean();
        this.setEntity(entity);
        this.method = DELETE;
        return this;
    }

    /**
     * Set the method as "update"
     *
     * @param entity updated Entity
     * @return QueryMaker
     */
    public QueryMaker update(Entity entity)
    {
        super.clean();
        this.setEntity(entity);
        this.method = UPDATE;
        return this;
    }

    /**
     * Set the method as "insert"
     *
     * @param entity inserted Entity
     * @return QueryMaker
     */
    public QueryMaker insert(Entity entity)
    {
        super.clean();
        this.setEntity(entity);
        this.method = INSERT;
        return this;
    }

    /**
     * Set the method as "operation"
     *
     * @param entity inserted Entity
     * @return QueryMaker
     */
    public QueryMaker tblOperations(Entity entity)
    {
        super.clean();
        this.setEntity(entity);
        this.method = OPERATIONS;
        return this;
    }

    /**
     * Set the critera
     *
     * @param criteria query Criteria
     * @return QueryMaker
     */
    public QueryMaker where(Criteria criteria)
    {
        this.criteria = criteria;
        return this;
    }

    /**
     * Set the limit
     *
     * @param limit query limit
     * @return QueryMaker
     */
    public QueryMaker limit(int limit)
    {
        this.limit = limit;
        return this;
    }

    /**
     * Set the group
     * @param group query Group
     * @return QueryMaker
     */
    public QueryMaker group(Group group)
    {
        this.group = group;
        return this;
    }

    /**
     * Execute the query
     *
     * @return result List
     */
    public List<Entity> exec()
    {
        if (this.method == null
                || this.entityClassName == null) {
            return new ArrayList<Entity>();
        }

        this.daoManager.setEntityName(this.entityClassName);
        List<Entity> queryList = this.getQueryList();

        this.clean();
        return queryList;
    }

    /**
     * Execute the query
     *
     * @param id line id to return
     * @return result Entity
     */
    public Entity exec(Long id)
    {
        if (this.method == null
                || this.entityClassName == null) {
            return null;
        }

        this.criteria = new Criteria()
                .addValue("id", id);
        List<Entity> queryList = this.limit(1).exec();

        if (queryList.isEmpty()) {
            return null;
        } else {
            return queryList.get(0);
        }
    }

    /**
     * Execute a specific table operation query
     *
     * @param operation table operation to execute
     * @return result Entity
     */
    public Entity exec(String operation)
    {
        if (this.method == null
                || !OPERATIONS.equals(this.method)) {
            return null;
        }

        Entity result = null;
        if (TRUNCATE.equals(operation)) {
            result = this.daoManager.truncate(this.entity);
        }

        return result;
    }

    /**
     * Execute the query
     *
     * @param input query Input
     * @return result Entity
     */
    public Entity exec(Input input)
    {
        if (this.method == null
                || this.entityClassName == null) {
            return null;
        }

        Entity result = null;
        if (INSERT.equals(this.method) && input == null) {
            return null;
        }

        if (INSERT.equals(this.method)) {
            result = this.daoManager.insert(this.entityClass, input);
        }

        if (UPDATE.equals(this.method)) {

            result = this.daoManager.update(this.entity, input.getValues());
        }

        if (DELETE.equals(this.method)) {
            this.daoManager.setEntityName(this.entityClassName);
            result = this.daoManager.delete((Long) input.get("id"));
        }

        return result;
    }

    /**
     * Call DaoManager method corresponding
     * with the defined Criteria / method
     *
     * @return result List
     */
    private List<Entity> getQueryList()
    {
        if (this.criteria == null) {
            return this.daoManager
                    .findAll();
        } else if (this.criteria.getValues().isEmpty()) {
            return this.daoManager
                    .findAll();
        } else {
            if (this.group == null) {
                return this.daoManager
                        .findByCriteria(this.criteria.getValues(), this.limit);
            } else if (this.group.getValues().isEmpty()) {
                return this.daoManager
                        .findByCriteria(this.criteria.getValues(), this.limit);
            } else {
                return this.daoManager
                        .findByCriteria(this.criteria.getValues(), this.limit, this.group.getValues());
            }
        }
    }

    /**
     * Execute the query with a limit at 1
     *
     * @return result Entity
     */
    public Entity one()
    {
        List<Entity> queryList = this.limit(1).exec();

        if (queryList.isEmpty()) {
            return null;
        } else {
            return queryList.get(0);
        }
    }
}