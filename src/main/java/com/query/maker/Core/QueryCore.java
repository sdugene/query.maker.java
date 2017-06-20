package com.query.maker.Core;

import com.query.maker.*;

import java.util.Map;

/**
 * Created by Sebastien Dugene on 12/25/2017.
 */
public class QueryCore {
    protected DaoManager daoManager = new DaoManager();
    protected Entity entity = null;
    protected String className = null;
    protected String method = null;
    protected Criteria criteria = null;
    protected Group group = null;
    protected Order order = null;
    protected int limit = 50;

    /**
     * clear values
     */
    protected void clean()
    {
        this.className = null;
        this.method = null;
        this.criteria = null;
        this.group = null;
        this.limit = 50;
    }

    /**
     * set entity with defined Entity
     *
     * @param entity Entity to set
     */
    protected void setEntity(Entity entity)
    {
        this.entity = entity;
        this.className = entity.getClassName();
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
