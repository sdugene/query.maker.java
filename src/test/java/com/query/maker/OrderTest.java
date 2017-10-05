package com.query.maker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest
{
    @Test
    public void addValueTest()
    {
        Order order = new Order();

        order.addValue("test", "testValue");
        assertEquals("{test=ASC}", order.getValues().toString());

        order.clear();
        order.addValue("test", "ASC");
        assertEquals("{test=ASC}", order.getValues().toString());

        order.clear();
        order.addValue("test", "DESC");
        assertEquals("{test=DESC}", order.getValues().toString());
    }

    @Test
    public void getValuesTest()
    {
        Group group = new Group();
        assertEquals("{}", group.getValues().toString());
    }

    @Test
    public void clearTest()
    {
        Order order = new Order();

        order.addValue("test3", "testValue3");
        assertEquals("{test3=ASC}", order.getValues().toString());

        order.clear();
        assertEquals("{}", order.getValues().toString());
    }

}