package com.query.maker;

import com.query.maker.Core.DaoManager;
import com.query.maker.Core.QueryCore;

import java.util.HashMap;
import java.util.List;

public class QueryMaker extends QueryCore
{
    private QueryMaker() {}

    private static class SingletonHolder
    {
        private final static QueryMaker instance = new QueryMaker();
    }

    public static QueryMaker getInstance()
    {
        QueryMaker queryMaker = QueryMaker.SingletonHolder.instance;
        return queryMaker;
    }

    public QueryMaker select(Entity entity)
    {
        this.className = entity.getClassName();
        this.method = "select";
        return this;
    }

    public QueryMaker delete(Entity entity)
    {
        this.className = entity.getClassName();
        this.method = "delete";
        return this;
    }

    public QueryMaker update(Entity entity)
    {
        this.className = entity.getClassName();
        this.method = "update";
        return this;
    }

    public QueryMaker insert(Entity entity)
    {
        this.className = entity.getClassName();
        this.method = "insert";
        return this;
    }

    public QueryMaker column(Column column)
    {

        return this;
    }

    public QueryMaker where(Criteria criteria)
    {
        this.criteria = criteria;
        return this;
    }

    public QueryMaker where(Long id)
    {
        this.criteria = new Criteria();
        this.criteria.addValue("id", id.toString());
        return this;
    }

    public QueryMaker join(QueryMaker query, Criteria criteria)
    {

        return this;
    }

    public QueryMaker join(Entity entity, Criteria criteria)
    {

        return this;
    }

    public QueryMaker limit(int limit)
    {
        this.limit = limit;
        return this;
    }

    public QueryMaker order(Order order)
    {

        return this;
    }

    public QueryMaker group(Group group)
    {

        return this;
    }

    public QueryMaker groupOrder(Group group)
    {

        return this;
    }

    public List<Entity> exec()
    {
        this.daoManager.setEntityName(this.className);
        List<Entity> queryList;

        if (this.criteria.getValues().isEmpty()) {
            queryList = this.daoManager.findAll();
        } else {
            queryList = this.daoManager.findByCriteria(this.criteria.getValues(), limit);
        }

        this.clean();

        if (queryList == null) {
            return null;
        } else {
            return queryList;
        }
    }

    public Entity one()
    {
        List<Entity> queryList = this.limit(1).exec();

        if (queryList == null) {
            return null;
        } else {
            return queryList.get(0);
        }
    }

    public String test()
    {
        return this.className;
    }
}