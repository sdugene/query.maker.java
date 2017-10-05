package com.query.maker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DaoManagerTest
{
    Map<String, String> properties = new HashMap<String, String>();
    DaoManager daoManager = new DaoManager();


    @Before
    public void setUp()
    {
        this.properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        this.properties.put("username", "tests");
        this.properties.put("password", "KS94nik7");

        this.daoManager.createSession(properties);
    }

    @After
    public void tearDown()
    {
        daoManager.closeSession();
    }

    @Test
    public void insertUpdateDeleteTest()
    {
        this.daoManager.setEntityName(new User().getClassName());

        Input input = new Input();
        input.addValue("firstName", "test");

        User user = (User) daoManager.insert(User.class, input);
        assertEquals("test", user.getFirstName());

        Input input2 = new Input();
        input.addValue("firstName", "test2");

        User user2 = (User) daoManager.update(user, input);
        assertEquals("test2", user2.getFirstName());

        Result result = (Result) daoManager.delete(user2.getId());
        assertEquals(true, result.isBool());

        Result result2 = (Result) daoManager.delete(user2.getId());
        assertEquals(false, result2.isBool());
    }

    @Test
    public void truncateTest()
    {
        Result result = (Result) daoManager.truncate(new Mail());
        assertEquals(true, result.isBool());
    }

    @Test
    public void findAllTest()
    {
        this.daoManager.setEntityName(new User().getClassName());

        List<Entity> users = daoManager.findAll();
        assertEquals("Sebastien", ((User) users.get(0)).getFirstName());
    }

    @Test
    public void findByCriteriaTest()
    {
        this.daoManager.setEntityName(new User().getClassName());

        Criteria criteria = new Criteria();
        criteria.addValue("id", 1L);
        criteria.addValue("firstName", "error", "AND_not");

        List<Entity> users = daoManager.findByCriteria(criteria,1);
        assertEquals("Sebastien", ((User) users.get(0)).getFirstName());

        Group group = new Group();
        group.addValue("firstName", null);

        Order order = new Order();

        List<Entity> users2 = daoManager.findByCriteria(criteria,1, group, order);
        assertEquals("Sebastien", ((User) users2.get(0)).getFirstName());

        group.addValue("id", null);
        group.addValue("firstName", null);
        group.addValue("LastName", "test");

        Criteria criteria2 = new Criteria();
        criteria2.addValue("id", 1L);
        criteria2.addValue("firstName", "error", "OR_not");

        List<Entity> users3 = daoManager.findByCriteria(criteria2,1, group, null);
        assertEquals("Sebastien", ((User) users3.get(0)).getFirstName());

        Criteria criteria3 = new Criteria();
        criteria3.addValue("id", 1L);
        criteria3.orValue("firstName", "Sebastien");

        List<Entity> users4 = daoManager.findByCriteria(criteria3,1);
        assertEquals("Sebastien", ((User) users4.get(0)).getFirstName());

        Criteria criteria4 = new Criteria();
        criteria4.addValue("id", 1L);
        criteria4.addValue("firstName", null, "OR_not");

        List<Entity> users5 = daoManager.findByCriteria(criteria4,1);
        assertEquals("Sebastien", ((User) users5.get(0)).getFirstName());
    }

    @Test
    public void findByCriteriaErrorTest()
    {
        this.daoManager.setEntityName(new User().getClassName());

        Criteria criteria = new Criteria();
        criteria.addValue("id", null);

        Group group = new Group();
        group.addValue("firstName", "test");

        Order order = new Order();
        order.addValue("id", "ASC");

        List<Entity> users = daoManager.findByCriteria(criteria,1, group, order);
        if (users.isEmpty()) {
            assert(true);
        } else {
            assert(false);
        }
    }

    @Test
    public void createSessionTest()
    {
        DaoManager daoManager = new DaoManager();

        try {
            daoManager.createSession(this.properties);
            daoManager.closeSession();
        } catch (Exception e) {
            assert(false);
        }
    }

}