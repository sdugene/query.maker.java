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
    public void singletonHolderInstantiateTest()
    {
        Class<?> singletonHolder = QueryMaker.class.getDeclaredClasses()[0];

        try {
            Constructor<?> c = singletonHolder.getDeclaredConstructors()[0];
            c.setAccessible(true);
            c.newInstance();
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void getInstanceTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance();
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void selectTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .select(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void deleteTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .delete(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void updateTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .update(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void insertTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .insert(new User());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void tblOperationsTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .tblOperations(new Mail());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void whereTest()
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
    public void limitTest()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .limit(1);
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void groupTest()
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
    public void orderTest()
    {
        Order order = new Order()
                .addValue("test", "ASC");

        QueryMaker queryMaker = QueryMaker.getInstance()
                .order(order);
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void execTest()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        List<Entity> list = QueryMaker.getInstance()
                .exec();

        assertEquals("[]", list.toString());

        List<Entity> list2 = QueryMaker.getInstance()
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

        // Criteria == null
        User user = (User) QueryMaker.getInstance()
                .select(new User())
                .one();
        assertEquals("Sebastien", user.getFirstName());

        // Criteria == empty
        Criteria criteria2 = new Criteria();

        User user2 = (User) QueryMaker.getInstance()
                .select(new User())
                .where(criteria2)
                .one();
        assertEquals("Sebastien", user2.getFirstName());

        // Criteria != empty
        Criteria criteria3 = new Criteria();
        criteria3.addValue("firstName", "Sebastien");

        User user3 = (User) QueryMaker.getInstance()
                .select(new User())
                .where(criteria3)
                .one();
        assertEquals("Sebastien", user3.getFirstName());

        // Criteria != empty & Group == empty
        Group group = new Group();

        User user4 = (User) QueryMaker.getInstance()
                .select(new User())
                .where(criteria3)
                .group(group)
                .one();
        assertEquals("Sebastien", user4.getFirstName());

        // Criteria != empty & Group != empty
        group.addValue("firstName", null);

        User user5 = (User) QueryMaker.getInstance()
                .select(new User())
                .where(criteria3)
                .group(group)
                .one();
        assertEquals("Sebastien", user5.getFirstName());

        queryMaker.closeSession();
    }

    @Test
    public void execIntTest()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        QueryMaker.getInstance().clean();

        User user = (User) QueryMaker.getInstance()
                .exec(1L);

        assertEquals(null, user);

        User user2 = (User) QueryMaker.getInstance()
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
    public void execStringTest()
    {
        QueryMaker.getInstance().clean();

        Result result = (Result) QueryMaker.getInstance()
                .exec("test");

        assertEquals(null, result);

        Result result2 = (Result) QueryMaker.getInstance()
                .select(null)
                .exec("test");

        assertEquals(null, result2);

        Result result3 = (Result) QueryMaker.getInstance()
                .tblOperations(new Mail())
                .exec("test");

        assertEquals(null, result3);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        Result result4 = (Result) queryMaker
                .tblOperations(new Mail())
                .exec("truncate");
        assertEquals(true, result4.isBool());

        queryMaker.closeSession();

    }

    @Test
    public void execInputTest()
    {
        QueryMaker.getInstance().clean();

        Input input = new Input();

        User user = (User) QueryMaker.getInstance()
                .exec(input);

        assertEquals(null, user);

        User user2 = (User) QueryMaker.getInstance()
                .select(null)
                .exec(input);

        assertEquals(null, user2);
    }

    @Test
    public void execInputInsertUpdateDeleteTest()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        Input input = new Input();

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        User user = (User) queryMaker
                .insert(new User())
                .exec(input);
        assertEquals(null, user.getFirstName());

        Input inputError = null;
        User userError = (User) queryMaker
                .insert(new User())
                .exec(inputError);
        assertEquals(null, userError);

        input.addValue("firstName", "test");
        User user2 = (User) queryMaker
                .insert(new User())
                .exec(input);

        assertEquals("test", user2.getFirstName());

        Input input2 = new Input();
        input2.addValue("firstName", "test2");
        User user3 = (User) queryMaker
                .update(user2)
                .exec(input2);

        assertEquals("test2", user3.getFirstName());

        Input input3 = new Input();
        input3.addValue("id", user3.getId());
        Result result = (Result) queryMaker
            .delete(user2)
            .exec(input3);

        assertEquals(true, result.isBool());

        Input input4 = new Input();
        input4.addValue("id", user.getId());
        Result result2 = (Result) queryMaker
                .delete(user)
                .exec(input4);
        assertEquals(true, result2.isBool());

        queryMaker.closeSession();
    }

    @Test
    public void oneTest()
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
                .select(new User())
                .where(criteria)
                .one();

        assertEquals(null, user);

        Criteria criteria2 = new Criteria();
        criteria2.addValue("id", 1L);

        User user2 = (User) QueryMaker.getInstance()
                .select(new User())
                .where(criteria2)
                .one();

        assertEquals("Sebastien", user2.getFirstName());

        queryMaker.closeSession();
    }
}