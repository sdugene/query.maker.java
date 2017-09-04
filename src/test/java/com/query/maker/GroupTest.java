package com.query.maker;

import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {
    @Test
    public void addValue()
    {
        Group group = new Group();

        group.addValue("test", "testValue");
        assertEquals("{test=testValue}", group.getValues().toString());
    }

    @Test
    public void getValues()
    {
        Group group = new Group();
        assertEquals("{}", group.getValues().toString());
    }

    @Test
    public void clear()
    {
        Group group = new Group();

        group.addValue("test3", "testValue3");
        assertEquals("{test3=testValue3}", group.getValues().toString());

        group.clear();
        assertEquals("{}", group.getValues().toString());
    }

}