package com.query.maker;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.Assert.*;

public class QueryMakerTest
{
    @Test
    public void instantiateTest()
    {
        try {
            Constructor<QueryMaker> c = QueryMaker.class.getDeclaredConstructor();
            c.setAccessible(true);
            c.newInstance();
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void getInstance()
    {
        QueryMaker queryMaker = QueryMaker.getInstance();
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void select()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .select(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void delete()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .delete(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void update()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .update(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void insert()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .insert(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void where()
    {
        Criteria criteria = new Criteria()
                .addValue("test", "testValue");

        QueryMaker queryMaker = QueryMaker.getInstance()
                .where(criteria);
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void limit()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .limit(1);
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void group()
    {
        Group group = new Group()
                .addValue("test", "testValue");

        QueryMaker queryMaker = QueryMaker.getInstance()
                .group(group);
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void exec()
    {
        List<Entity> list = QueryMaker.getInstance()
                .clean()
                .exec();

        assertEquals("[]", list.toString());

        Criteria criteria = new Criteria();
        criteria.addValue("id", "1");

        /*QueryMaker.getInstance().select(new User())
                .where(criteria)
                .exec();*/
    }

    @Test
    public void exec1()
    {
    }

    @Test
    public void exec2()
    {
    }

    @Test
    public void one()
    {
    }

    @Test
    public void clean()
    {
    }

    @Test
    public void setEntity()
    {
    }

    @Test
    public void createSession()
    {
    }

    @Test
    public void closeSession()
    {
    }
}