package com.query.maker.Core;

import com.query.maker.Entity;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class QueryCoreTest
{
    private class QueryCoreAnon extends QueryCore
    {
        public String getEntityClassName()
        {
            return entityClassName;
        }
    }

    private class EntityAnon extends Entity
    {
        public Long getId() {
            return null;
        }
    }

    @Test
    public void clean()
    {
        QueryCoreAnon queryCoreAnon = new QueryCoreAnon();
        queryCoreAnon.setEntity(new EntityAnon());

        assertEquals("EntityAnon", queryCoreAnon.getEntityClassName());

        queryCoreAnon.clean();
        assertEquals(null, queryCoreAnon.getEntityClassName());
    }

    @Test
    public void setEntity()
    {
        QueryCoreAnon queryCoreAnon = new QueryCoreAnon();
        queryCoreAnon.setEntity(new EntityAnon());

        assertEquals("EntityAnon", queryCoreAnon.getEntityClassName());
    }

    @Test
    public void createSession()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "test");
        properties.put("password","KS94nik7");

        QueryCoreAnon queryCoreAnon = new QueryCoreAnon();

        try {
            queryCoreAnon.createSession(properties);
        } catch (Exception e) {
            assert(true);
        }

        try {
            queryCoreAnon.closeSession();
        } catch (Exception e) {
            assert(true);
        }

        properties.put("username", "tests");
        queryCoreAnon.createSession(properties);

        try {
            queryCoreAnon.createSession(properties);
        } catch (Exception e) {
            assert(false);
        }

        try {
            queryCoreAnon.closeSession();
        } catch (Exception e) {
            assert(false);
        }
    }

}