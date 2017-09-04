package com.query.maker.core;

import com.query.maker.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DaoManagerTest
{
    @Test
    public void insertUpdateDelete()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        DaoManager daoManager = new DaoManager();
        daoManager.createSession(properties);
        daoManager.setEntityName(new UserTest().getClassName());

        Input input = new Input();
        input.addValue("firstName", "test");

        UserTest userTest = (UserTest) daoManager.insert(UserTest.class, input);
        assertEquals("test", userTest.getFirstName());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstName", "test2");

        UserTest userTest2 = (UserTest) daoManager.update(userTest, map);
        assertEquals("test2", userTest2.getFirstName());

        Result result = (Result) daoManager.delete(userTest2.getId());
        assertEquals(true, result.isBool());

        Result result2 = (Result) daoManager.delete(userTest2.getId());
        assertEquals(false, result2.isBool());

        daoManager.closeSession();
    }

    @Test
    public void findAll()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        DaoManager daoManager = new DaoManager();
        daoManager.createSession(properties);
        daoManager.setEntityName(new UserTest().getClassName());

        List<Entity> users = daoManager.findAll();
        assertEquals("Sebastien", ((UserTest) users.get(0)).getFirstName());

        daoManager.closeSession();
    }

    @Test
    public void findByCriteria()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        DaoManager daoManager = new DaoManager();
        daoManager.createSession(properties);
        daoManager.setEntityName(new UserTest().getClassName());

        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("id", 1L);
        Map<String, Object> notValue = new HashMap<String, Object>();
        notValue.put("KEY"+notValue.size()+"firstName", "error");
        criteria.put("AND_not", notValue);

        List<Entity> users = daoManager.findByCriteria(criteria,1);
        assertEquals("Sebastien", ((UserTest) users.get(0)).getFirstName());

        Map<String, String> group = new HashMap<String, String>();
        group.put("firstName", null);

        List<Entity> users2 = daoManager.findByCriteria(criteria,1, group);
        assertEquals("Sebastien", ((UserTest) users2.get(0)).getFirstName());

        group.put("id", null);
        group.put("firstName", null);
        group.put("LastName", "test");

        Map<String, Object> criteria2 = new HashMap<String, Object>();
        criteria2.put("id", 1L);
        Map<String, Object> notValue2 = new HashMap<String, Object>();
        notValue2.put("KEY"+notValue2.size()+"firstName", "error");
        criteria2.put("OR_not", notValue2);

        List<Entity> users3 = daoManager.findByCriteria(criteria2,1, group);
        assertEquals("Sebastien", ((UserTest) users3.get(0)).getFirstName());

        Map<String, Object> criteria3 = new HashMap<String, Object>();
        criteria3.put("id", 1L);
        Map<String, Object> notValue3 = new HashMap<String, Object>();
        notValue3.put("KEY"+notValue3.size()+"firstName", "Sebastien");
        criteria3.put("OR", notValue3);

        List<Entity> users4 = daoManager.findByCriteria(criteria3,1);
        assertEquals("Sebastien", ((UserTest) users4.get(0)).getFirstName());

        Map<String, Object> criteria4 = new HashMap<String, Object>();
        criteria4.put("id", 1L);
        Map<String, Object> notValue4 = new HashMap<String, Object>();
        notValue4.put("KEY"+notValue4.size()+"firstName", null);
        criteria4.put("OR_not", notValue4);

        List<Entity> users5 = daoManager.findByCriteria(criteria4,1);
        assertEquals("Sebastien", ((UserTest) users5.get(0)).getFirstName());
    }

    @Test
    public void findByCriteriaError()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        DaoManager daoManager = new DaoManager();
        daoManager.createSession(properties);
        daoManager.setEntityName(new UserTest().getClassName());

        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("id", null);

        Map<String, String> group = new HashMap<String, String>();
        group.put("firstName", "test");

        List<Entity> users = daoManager.findByCriteria(criteria,1, group);
        if (users.isEmpty()) {
            assert(true);
        } else {
            assert(false);
        }
    }

    @Test
    public void createSession()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        DaoManager daoManager = new DaoManager();

        try {
            daoManager.createSession(properties);
            daoManager.closeSession();
        } catch (Exception e) {
            assert(false);
        }
    }

}