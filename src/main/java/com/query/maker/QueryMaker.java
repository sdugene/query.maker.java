package com.query.maker;

import com.query.maker.Core.*;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.setEntity(entity);
        this.method = "select";
        return this;
    }

    public QueryMaker delete(Entity entity)
    {
        this.setEntity(entity);
        this.method = "delete";
        return this;
    }

    public QueryMaker update(Entity entity)
    {
        this.setEntity(entity);
        this.method = "update";
        return this;
    }

    public QueryMaker insert(Entity entity)
    {
        this.setEntity(entity);
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
        this.criteria.addValue("id", id);
        return this;
    }

    public QueryMaker join(Entity entity, String method, Map<String, String> mJoinCriteria)
    {
        Map<String, Object> joinOn = new HashMap();
        joinOn.put(entity.getClassName(), mJoinCriteria);

        JoinCriteria joinCriteria = new JoinCriteria();
        joinCriteria.addValue(method, joinOn);

        this.joinCriteria = joinCriteria;
        return this;
    }

    public QueryMaker join(Entity entity, String method, Map<String, String> mJoinCriteria, Criteria criteria)
    {
        Map<String, Object> joinOn = new HashMap();
        joinOn.put(entity.getClassName(), mJoinCriteria);

        JoinCriteria joinCriteria = new JoinCriteria();
        joinCriteria.addValue(method, joinOn);

        this.joinCriteria = joinCriteria;
        this.criteria = criteria;
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
        this.group = group;
        return this;
    }

    public QueryMaker groupOrder(Group group)
    {

        return this;
    }

    public List<Entity> exec()
    {
        this.daoManager.setEntityName(this.className);
        List<Entity> queryList = getQueryList();

        this.clean();

        if (queryList == null) {
            return null;
        } else {
            return queryList;
        }
    }

    private List<Entity> getQueryList()
    {
        if (this.method == null) { return null; }

        if (this.joinCriteria != null && !this.joinCriteria.getValues().isEmpty()
                && this.criteria != null && !this.criteria.getValues().isEmpty()
                && this.group != null && !this.group.getValues().isEmpty()) {
            return this.daoManager.findWithJoin(this.criteria.getValues(), this.criteria.getValues(), this.limit, this.group.getValues());
        }

        if (this.joinCriteria != null && !this.joinCriteria.getValues().isEmpty()
                && this.criteria != null && !this.criteria.getValues().isEmpty()) {
            return this.daoManager.findWithJoin(this.criteria.getValues(), this.criteria.getValues(), this.limit);
        }

        if (this.joinCriteria != null && !this.joinCriteria.getValues().isEmpty()
                && this.group != null && !this.group.getValues().isEmpty()) {
            return this.daoManager.findWithJoin(this.criteria.getValues(), null, this.limit, this.group.getValues());
        }

        if (this.joinCriteria != null && !this.joinCriteria.getValues().isEmpty()) {
            return this.daoManager.findWithJoin(this.criteria.getValues(), null, this.limit);
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

    public Entity exec(Input input)
    {
        Entity result = null;
        if (this.method == null) { return null; }
        if (this.method.equals("insert") && input == null) { return null; }

        if (this.method.equals("insert")) {
            result = this.daoManager.insert(this.entity, input.getValues());
        }

        return result;
    }

    public Entity one()
    {
        List<Entity> queryList = this.limit(1).exec();

        if (queryList == null) {
            return null;
        } else {
            this.entity = queryList.get(0);
            return this.entity;
        }
    }

    public String test()
    {
        return this.className;
    }
}