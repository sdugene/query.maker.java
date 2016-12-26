package com.query.maker;

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
        queryMaker.className = null;
        queryMaker.method = null;
        queryMaker.criteria = null;
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
        this.criteria = Criteria.getInstance();
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

    public Entity exec()
    {
        List<Entity> queryList = this.daoManager.setEntityName(this.className).findByCriteria(this.criteria.getValues(), 1);

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