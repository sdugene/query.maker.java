package com.query.maker;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest
{
    @Test
    public void getIdTest()
    {
        Result result = new Result();
        assertEquals(null, result.getId());
    }

    @Test
    public void isBoolTest()
    {
        Result result = new Result();
        assertEquals(false, result.isBool());
    }

    @Test
    public void setBoolTest()
    {
        Result result = new Result();
        assertEquals(false, result.isBool());

        result.setBool(true);
        assertEquals(true, result.isBool());
    }
}