package com.query.maker;

import com.query.maker.Core.*;

import java.util.ArrayList;
import java.util.List;

public class QueryMaker extends QueryCore
{
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
    }

    /**
     * @return QueryMaker instance
     */
    public static QueryMaker getInstance()
    {
        return QueryMaker.SingletonHolder.instance;
    }

    /**
     * Set the method as "select"
     *
     * @param entity selected Entity
     * @return QueryMaker
     */
    public QueryMaker select(Entity entity)
    {
        this.setEntity(entity);
        this.method = "select";
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
        this.setEntity(entity);
        this.method = "delete";
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
        this.setEntity(entity);
        this.method = "update";
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
        this.setEntity(entity);
        this.method = "insert";
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
        this.daoManager.setEntityName(this.entityClassName);
        List<Entity> queryList = getQueryList();

        this.clean();

        if (queryList == null) {
            return new ArrayList<Entity>();
        } else {
            return queryList;
        }
    }

    /**
     * Execute the query
     *
     * @param id line id to return
     * @return result Entity
     */
    public Entity exec(Long id)
    {
        this.criteria = new Criteria()
                .addValue("id", id);
        List<Entity> queryList = this.limit(1).exec();

        if (queryList == null) {
            return null;
        } else {
            return queryList.get(0);
        }
    }

    /**
     * Execute the query
     *
     * @param input query Input
     * @return result Entity
     */
    public Entity exec(Input input)
    {
        Entity result = null;
        if (this.method == null) {
            return null;
        }
        if ("insert".equals(this.method) && input == null) {
            return null;
        }

        if ("insert".equals(this.method)) {
            result = this.daoManager.insert(this.entityClass, input.toJSONString());
        }

        if ("update".equals(this.method)) {
            result = this.daoManager.update(this.entityClass, input.toJSONString());
        }

        if ("delete".equals(this.method)) {
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
        if (this.method == null) {
            return new ArrayList<Entity>();
        }

        if (this.criteria != null && !this.criteria.getValues().isEmpty()
                && this.group != null && !this.group.getValues().isEmpty()) {
            return this.daoManager.findByCriteria(this.criteria.getValues(), this.limit, this.group.getValues());
        }

        if (this.criteria != null && !this.criteria.getValues().isEmpty()) {
            return this.daoManager.findByCriteria(this.criteria.getValues(), this.limit);
        }

        return this.daoManager.findAll();
    }

    /**
     * Execute the query with a limit at 1
     *
     * @return result Entity
     */
    public Entity one()
    {
        List<Entity> queryList = this.limit(1).exec();

        if (queryList == null) {
            return null;
        } else {
            return queryList.get(0);
        }
    }
}