package com.query.maker;

import org.junit.Test;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Transactional
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
        properties.put("url", TestConfig.MYSQL_URL);
        properties.put(TestConfig.MYSQL_USERNAME, "tests");
        properties.put("password", TestConfig.MYSQL_PASSWORD);

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