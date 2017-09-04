package com.query.maker;

import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {
    @Test
    public void getClassName()
    {
        User user = new User();
        assertEquals("User", user.getClassName());
    }

    @Test
    public void getId()
    {
        User user = new User();
        user.setId(1L);
        assertEquals("1", String.valueOf(user.getId()));
    }

}