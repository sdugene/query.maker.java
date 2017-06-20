package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class Group
{
    private Map<String, String> values;

    public Group() { this.values = new HashMap<String, String>(); }

    public Group addValue(String key, String value)
    {
        this.values.put(key, value);
        return this;
    }

    Map<String, String> getValues()
    {
        return this.values;
    }

    public Group clear()
    {
        this.values = new HashMap<String, String>();
        return this;
    }
}
