package com.query.maker;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    @Test
    public void exec()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        List<Entity> list = QueryMaker.getInstance()
                .clean()
                .exec();

        assertEquals("[]", list.toString());

        List<Entity> list2 = QueryMaker.getInstance()
                .clean()
                .select(null)
                .exec();

        assertEquals("[]", list2.toString());

        Criteria criteria = new Criteria();
        criteria.addValue("id", 1L);

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);
        List<User> users = (List) queryMaker.select(new User())
                .where(criteria)
                .exec();

        queryMaker.closeSession();
        assertEquals("1", String.valueOf(users.get(0).getId()));
    }

    @Test
    public void getQueryListTest()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        Criteria criteria = null;

        User user = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria)
                .exec(1L);
        assertEquals("Sebastien", user.getFirstName());

        Criteria criteria2 = new Criteria();

        User user2 = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria2)
                .exec(1l);
        assertEquals("Sebastien", user2.getFirstName());

        Criteria criteria3 = new Criteria();
        criteria3.addValue("firstName", "Sebastien");

        User user3 = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria3)
                .exec(1l);
        assertEquals("Sebastien", user3.getFirstName());

        Group group = new Group();

        User user4 = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria3)
                .group(group)
                .exec(1l);
        assertEquals("Sebastien", user4.getFirstName());

        group.addValue("firstName", null);

        User user5 = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria3)
                .group(group)
                .exec(1l);
        assertEquals("Sebastien", user5.getFirstName());

        queryMaker.closeSession();
    }

    @Test
    public void execInt()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        User user = (User) QueryMaker.getInstance()
                .clean()
                .exec(1L);

        assertEquals(null, user);

        User user2 = (User) QueryMaker.getInstance()
                .clean()
                .select(null)
                .exec(1L);

        assertEquals(null, user2);

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        User user3 = (User) queryMaker.select(new User())
                .exec(2L);
        assertEquals(null, user3);

        User user4 = (User) queryMaker.select(new User())
                .exec(1L);

        queryMaker.closeSession();
        assertEquals("1", String.valueOf(user4.getId()));
    }

    @Test
    public void execInput()
    {
        Input input = new Input();

        User user = (User) QueryMaker.getInstance()
                .clean()
                .exec(input);

        assertEquals(null, user);

        User user2 = (User) QueryMaker.getInstance()
                .clean()
                .select(null)
                .exec(input);

        assertEquals(null, user2);
    }

    @Test
    public void execInputInsertUpdateDelete()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        Input input = new Input();

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        User user = (User) queryMaker.clean()
                .insert(new User())
                .exec(input);
        assertEquals(null, user.getFirstName());

        Input inputError = null;
        User userError = (User) queryMaker.clean()
                .insert(new User())
                .exec(inputError);
        assertEquals(null, userError);

        input.addValue("firstName", "test");
        User user2 = (User) queryMaker.clean()
                .insert(new User())
                .exec(input);

        assertEquals("test", user2.getFirstName());

        Input input2 = new Input();
        input2.addValue("firstName", "test2");
        User user3 = (User) queryMaker.clean()
                .update(user2)
                .exec(input2);

        assertEquals("test2", user3.getFirstName());

        Input input3 = new Input();
        input3.addValue("id", user3.getId());
        Result result = (Result) queryMaker.clean()
            .delete(user2)
            .exec(input3);

        assertEquals(true, result.isBool());

        Input input4 = new Input();
        input4.addValue("id", user.getId());
        Result result2 = (Result) queryMaker.clean()
                .delete(user)
                .exec(input4);
        assertEquals(true, result2.isBool());

        queryMaker.closeSession();
    }

    @Test
    public void one()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        Criteria criteria = new Criteria();
        criteria.addValue("id", 0L);

        User user = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria)
                .one();

        assertEquals(null, user);

        Criteria criteria2 = new Criteria();
        criteria2.addValue("id", 1L);

        User user2 = (User) QueryMaker.getInstance()
                .clean()
                .select(new User())
                .where(criteria2)
                .one();

        assertEquals("Sebastien", user2.getFirstName());

        queryMaker.closeSession();
    }
}