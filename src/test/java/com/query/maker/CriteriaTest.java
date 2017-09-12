package com.query.maker;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CriteriaTest
{
    @Test
    public void addValueTest()
    {
        Criteria criteria = new Criteria();

        criteria.addValue("test", "testValue");
        assertEquals("{test=testValue}", criteria.getValues().toString());

        criteria.addValue("test2", "testValue2", "OR");
        assertEquals("{test=testValue, OR_not={KEY0test2=testValue2}}", criteria.getValues().toString());


        criteria.addValue("test3", "testValue3", "OR");
        assertEquals("{test=testValue, OR_not={KEY0test2=testValue2, KEY1test3=testValue3}}", criteria.getValues().toString());
    }

    @Test
    public void orValueTest()
    {
        Criteria criteria = new Criteria();

        criteria.orValue("test", "testValue");
        assertEquals("{or={KEY0test=testValue}}", criteria.getValues().toString());
    }

    @Test
    public void notValueTest()
    {
        Criteria criteria = new Criteria();

        criteria.notValue("test", "testValue");
        assertEquals("{and_not={KEY0test=testValue}}", criteria.getValues().toString());
    }

    @Test
    public void getValuesTest()
    {
        Criteria criteria = new Criteria();
        assertEquals("{}", criteria.getValues().toString());
    }

    @Test
    public void setValuesTest()
    {
        Criteria criteria = new Criteria();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "testValue");
        map.put("test2", "testValue2");
        criteria.setValues(map);
        assertEquals("{test2=testValue2, test=testValue}", criteria.getValues().toString());
    }

    @Test
    public void clearTest()
    {
        Criteria criteria = new Criteria();

        criteria.addValue("test3", "testValue3");
        assertEquals("{test3=testValue3}", criteria.getValues().toString());

        criteria.clear();
        assertEquals("{}", criteria.getValues().toString());
    }

}