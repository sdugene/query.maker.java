package com.query.maker.Core;

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
    public void delete()
    {
    }

    @Test
    public void insert()
    {
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