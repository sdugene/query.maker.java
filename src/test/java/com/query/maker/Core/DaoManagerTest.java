package com.query.maker.Core;

import com.query.maker.Input;
import com.query.maker.Result;
import com.query.maker.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DaoManagerTest
{
    @Test
    public void setEntityName()
    {
    }

    @Test
    public void insertUpdateDelete()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        DaoManager daoManager = new DaoManager();
        daoManager.createSession(properties);
        daoManager.setEntityName(new User().getClassName());

        Input input = new Input();
        input.addValue("firstName", "test");

        User user = (User) daoManager.insert(User.class, input);
        assertEquals("test", user.getFirstName());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("firstName", "test2");

        User user2 = (User) daoManager.update(user, map);
        assertEquals("test2", user2.getFirstName());

        Result result = (Result) daoManager.delete(user2.getId());
        assertEquals(true, result.isBool());

        Result result2 = (Result) daoManager.delete(user2.getId());
        assertEquals(false, result2.isBool());
    }

    @Test
    public void update()
    {
    }

    @Test
    public void findAll()
    {
    }

    @Test
    public void findByCriteria()
    {
    }

    @Test
    public void findByCriteria1()
    {
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

    @Test
    public void closeSession()
    {
    }

}