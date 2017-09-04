package com.query.maker.Core;

import com.query.maker.*;

import java.util.Map;

public class QueryCore
{
    protected DaoManager daoManager = new DaoManager();
    protected Class entityClass = null;
    protected String entityClassName = null;
    protected String method = null;
    protected Criteria criteria = null;
    protected Group group = null;
    protected int limit = 50;

    /**
     * clear values
     */
    protected QueryCore clean()
    {
        this.entityClass = null;
        this.entityClassName = null;
        this.method = null;
        this.criteria = null;
        this.group = null;
        this.limit = 50;
        return this;
    }

    /**
     * set entity with defined Entity
     *
     * @param entity Entity to set
     */
    protected void setEntity(Entity entity)
    {
        this.entityClass = entity.getClass();
        this.entityClassName = entity.getClassName();
    }

    /**
     * Call DaoManager createSession method
     *
     * @param properties database properties
     */
    public void createSession(Map<String, String> properties)
    {
        this.daoManager.createSession(properties);
    }

    /**
     * Call DaoManager closeSession method
     */
    public void closeSession()
    {
        this.daoManager.closeSession();
    }
}
