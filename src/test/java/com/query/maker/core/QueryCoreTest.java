package com.query.maker.core;

import com.query.maker.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class QueryCoreTest
{
    private class QueryCoreAnon extends QueryCore
    {
        public String getEntityClassName() {
            return entityClassName;
        }
    }

    @Test
    public void cleanTest() {
        QueryCoreAnon queryCoreAnon = new QueryCoreAnon();
        queryCoreAnon.setEntity(new User());

        assertEquals("User", queryCoreAnon.getEntityClassName());

        queryCoreAnon.clean();
        assertEquals(null, queryCoreAnon.getEntityClassName());
    }

    @Test
    public void setEntityTest() {
        QueryCoreAnon queryCoreAnon = new QueryCoreAnon();
        queryCoreAnon.setEntity(new User());

        assertEquals("User", queryCoreAnon.getEntityClassName());
    }

    @Test
    public void createSessionTest() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "test");
        properties.put("password", "KS94nik7");

        QueryCoreAnon queryCoreAnon = new QueryCoreAnon();

        try {
            queryCoreAnon.createSession(properties);
        } catch (Exception e) {
            assert (true);
        }

        try {
            queryCoreAnon.closeSession();
        } catch (Exception e) {
            assert (true);
        }

        properties.put("username", "tests");
        queryCoreAnon.createSession(properties);

        try {
            queryCoreAnon.createSession(properties);
        } catch (Exception e) {
            assert (false);
        }

        try {
            queryCoreAnon.closeSession();
        } catch (Exception e) {
            assert (false);
        }
    }
}