package com.query.maker.Core;

import com.query.maker.Criteria;
import com.query.maker.Entity;
import com.query.maker.Group;

public class QueryCore {
    protected DaoManager daoManager = new DaoManager();
    protected Entity entity = null;
    protected String className = null;
    protected String method = null;
    protected Criteria criteria = null;
    protected Group group = null;
    protected int limit = 50;

    protected void clean()
    {
        this.className = null;
        this.method = null;
        this.criteria = null;
        this.group = null;
        this.limit = 50;
    }

    protected void setEntity(Entity entity)
    {
        this.entity = entity;
        this.className = entity.getClassName();
    }

    public void createSession()
    {
        this.daoManager.createSession();
    }

    public void closeSession()
    {
        this.daoManager.closeSession();
    }
}
