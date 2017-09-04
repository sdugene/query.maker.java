package com.query.maker;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class InputTest {
    @Test
    public void addValue()
    {
        Input input = new Input();

        input.addValue("test", "testValue");
        assertEquals("{test=testValue}", input.getValues().toString());
    }

    @Test
    public void getValues()
    {
        Input input = new Input();
        assertEquals("{}", input.getValues().toString());
    }

    @Test
    public void toJSONString()
    {
        Input input = new Input();
        assertEquals("{}", input.toJSONString());
    }

    @Test
    public void setValues()
    {
        Input input = new Input();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "testValue");
        map.put("test2", "testValue2");
        input.setValues(map);
        assertEquals("{test2=testValue2, test=testValue}", input.getValues().toString());
    }

    @Test
    public void clear()
    {
        Input input = new Input();

        input.addValue("test3", "testValue3");
        assertEquals("{test3=testValue3}", input.getValues().toString());

        input.clear();
        assertEquals("{}", input.getValues().toString());
    }

    @Test
    public void get()
    {
        Input input = new Input();

        input.addValue("test3", "testValue3");
        assertEquals("{test3=testValue3}", input.getValues().toString());

        assertEquals("testValue3", input.get("test3"));
    }

}