package com.query.maker;

public class QueryMaker extends QueryCore
{
    public QueryMaker select(Entity entity)
    {
        // TODO recupérer les annotations de la classe en fonction de son nom (ex : com.siteoffice.Entities.UserModule) => user_module
        this.className = entity.getClassName();
        this.method = "select";
        return this;
    }

    public QueryMaker delete(Class name)
    {
        this.className = "toto";
        this.method = "delete";
        return this;
    }

    public QueryMaker update(Class name)
    {
        this.className = "toto";
        this.method = "update";
        return this;
    }

    public QueryMaker insert(Class name)
    {
        this.className = "toto";
        this.method = "insert";
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