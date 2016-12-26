package com.query.maker.Core;

import com.query.maker.Criteria;

public class QueryCore {
    protected DaoManager daoManager = new DaoManager();
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
}
