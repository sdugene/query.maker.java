package com.query.maker;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class EntityTest {
    @Test
    public void getClassName()
    {
        User user = new User();
        assertEquals("User", user.getClassName());
    }

    @Test
    public void toMap()
    {
        User user = new User();
        user.setId(1L);

        if (!(user.toMap() instanceof HashMap)) {
            assert(false);
        }

        Long id = ((Double) user.toMap().get("id")).longValue();
        assertEquals("1", String.valueOf(id));
    }

    @Test
    public void getId()
    {
        User user = new User();
        user.setId(1L);
        assertEquals("1", String.valueOf(user.getId()));
    }

}