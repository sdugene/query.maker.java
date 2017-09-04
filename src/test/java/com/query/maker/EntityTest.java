package com.query.maker;

import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {
    @Test
    public void getClassName()
    {
        class EntityAnon extends Entity
        {
            public Long getId() {
                return null;
            }
        }

        EntityAnon entityAnon = new EntityAnon();
        assertEquals("EntityAnon", entityAnon.getClassName());
    }

    @Test
    public void getId()
    {
        class EntityAnon extends Entity
        {
            public Long getId() {
                return 1L;
            }
        }

        EntityAnon entityAnon = new EntityAnon();
        assertEquals("1", String.valueOf(entityAnon.getId()));
    }

}