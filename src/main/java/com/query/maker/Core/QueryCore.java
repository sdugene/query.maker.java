package com.query.maker.Core;

import com.query.maker.Criteria;
import com.query.maker.Entity;

public class QueryCore {
    protected DaoManager daoManager = new DaoManager();
    protected Entity entity = null;
    protected String className = null;
    protected String method = null;
    protected Criteria criteria = null;
    protected int limit = 50;

    protected void clean()
    {
        this.className = null;
        this.method = null;
        this.criteria = null;
        this.limit = 50;
    }

    protected void setEntity(Entity entity)
    {
        this.entity = entity;
        this.className = entity.getClassName();
    }
}
