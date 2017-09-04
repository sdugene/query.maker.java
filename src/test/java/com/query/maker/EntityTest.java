package com.query.maker;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class EntityTest {
    @Test
    public void getClassName()
    {
        UserTest userTest = new UserTest();
        assertEquals("User", userTest.getClassName());
    }

    @Test
    public void toMap()
    {
        UserTest userTest = new UserTest();
        userTest.setId(1L);

        if (!(userTest.toMap() instanceof HashMap)) {
            assert(false);
        }

        Long id = ((Double) userTest.toMap().get("id")).longValue();
        assertEquals("1", String.valueOf(id));
    }

    @Test
    public void getId()
    {
        UserTest userTest = new UserTest();
        userTest.setId(1L);
        assertEquals("1", String.valueOf(userTest.getId()));
    }

}