package com.query.maker;

public class QueryMaker {


    public QueryMaker select(Class entity)
    {

        return this;
    }


    public QueryMaker delete(Class name)
    {

        return this;
    }

    public QueryMaker update(Class name)
    {

        return this;
    }

    public QueryMaker insert(Class name)
    {

        return this;
    }

    public QueryMaker column(Column column)
    {

        return this;
    }

    public QueryMaker where(Criteria criteria)
    {

        return this;
    }

    public QueryMaker where(int id)
    {

        return this;
    }

    public QueryMaker join(QueryMaker query, Criteria criteria)
    {

        return this;
    }

    public QueryMaker join(Class entity, Criteria criteria)
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
}
