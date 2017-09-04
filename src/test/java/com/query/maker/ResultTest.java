package com.query.maker;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {
    @Test
    public void getId()
    {
        Result result = new Result();
        assertEquals(null, result.getId());
    }

    @Test
    public void isBool()
    {
        Result result = new Result();
        assertEquals(false, result.isBool());
    }

    @Test
    public void setBool()
    {
        Result result = new Result();
        assertEquals(false, result.isBool());

        result.setBool(true);
        assertEquals(true, result.isBool());
    }
}