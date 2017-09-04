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
                .select(new UserTest());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void delete()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .delete(new UserTest());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void update()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .update(new UserTest());
        if (!(queryMaker instanceof QueryMaker)) {
            assert(false);
        }
    }

    @Test
    public void insert()
    {
        QueryMaker queryMaker = QueryMaker.getInstance()
                .insert(new UserTest());
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
        List<UserTest> userTests = (List) queryMaker.select(new UserTest())
                .where(criteria)
                .exec();

        queryMaker.closeSession();
        assertEquals("1", String.valueOf(userTests.get(0).getId()));
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
        UserTest userTest = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .one();
        assertEquals("Sebastien", userTest.getFirstName());

        // Criteria == empty
        Criteria criteria2 = new Criteria();

        UserTest userTest2 = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .where(criteria2)
                .one();
        assertEquals("Sebastien", userTest2.getFirstName());

        // Criteria != empty
        Criteria criteria3 = new Criteria();
        criteria3.addValue("firstName", "Sebastien");

        UserTest userTest3 = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .where(criteria3)
                .one();
        assertEquals("Sebastien", userTest3.getFirstName());

        // Criteria != empty & Group == empty
        Group group = new Group();

        UserTest userTest4 = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .where(criteria3)
                .group(group)
                .one();
        assertEquals("Sebastien", userTest4.getFirstName());

        // Criteria != empty & Group != empty
        group.addValue("firstName", null);

        UserTest userTest5 = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .where(criteria3)
                .group(group)
                .one();
        assertEquals("Sebastien", userTest5.getFirstName());

        queryMaker.closeSession();
    }

    @Test
    public void execInt()
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("url", "jdbc:mysql://91.121.66.115:3306/siteoffice_test");
        properties.put("username", "tests");
        properties.put("password", "KS94nik7");

        QueryMaker.getInstance().clean();

        UserTest userTest = (UserTest) QueryMaker.getInstance()
                .exec(1L);

        assertEquals(null, userTest);

        UserTest userTest2 = (UserTest) QueryMaker.getInstance()
                .select(null)
                .exec(1L);

        assertEquals(null, userTest2);

        QueryMaker queryMaker = QueryMaker.getInstance();
        queryMaker.createSession(properties);

        UserTest userTest3 = (UserTest) queryMaker.select(new UserTest())
                .exec(2L);
        assertEquals(null, userTest3);

        UserTest userTest4 = (UserTest) queryMaker.select(new UserTest())
                .exec(1L);

        queryMaker.closeSession();
        assertEquals("1", String.valueOf(userTest4.getId()));
    }

    @Test
    public void execInput()
    {
        QueryMaker.getInstance().clean();

        Input input = new Input();

        UserTest userTest = (UserTest) QueryMaker.getInstance()
                .exec(input);

        assertEquals(null, userTest);

        UserTest userTest2 = (UserTest) QueryMaker.getInstance()
                .select(null)
                .exec(input);

        assertEquals(null, userTest2);
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

        UserTest userTest = (UserTest) queryMaker
                .insert(new UserTest())
                .exec(input);
        assertEquals(null, userTest.getFirstName());

        Input inputError = null;
        UserTest userTestError = (UserTest) queryMaker
                .insert(new UserTest())
                .exec(inputError);
        assertEquals(null, userTestError);

        input.addValue("firstName", "test");
        UserTest userTest2 = (UserTest) queryMaker
                .insert(new UserTest())
                .exec(input);

        assertEquals("test", userTest2.getFirstName());

        Input input2 = new Input();
        input2.addValue("firstName", "test2");
        UserTest userTest3 = (UserTest) queryMaker
                .update(userTest2)
                .exec(input2);

        assertEquals("test2", userTest3.getFirstName());

        Input input3 = new Input();
        input3.addValue("id", userTest3.getId());
        Result result = (Result) queryMaker
            .delete(userTest2)
            .exec(input3);

        assertEquals(true, result.isBool());

        Input input4 = new Input();
        input4.addValue("id", userTest.getId());
        Result result2 = (Result) queryMaker
                .delete(userTest)
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

        UserTest userTest = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .where(criteria)
                .one();

        assertEquals(null, userTest);

        Criteria criteria2 = new Criteria();
        criteria2.addValue("id", 1L);

        UserTest userTest2 = (UserTest) QueryMaker.getInstance()
                .select(new UserTest())
                .where(criteria2)
                .one();

        assertEquals("Sebastien", userTest2.getFirstName());

        queryMaker.closeSession();
    }
}