package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class Input
{
    private Map<String, Object> values;

    public Input() { this.values = new HashMap(); }

    public Input addValue(String key, Object value)
    {
        this.values.put(key, value);
        return this;
    }

    public Map<String, Object> getValues()
    {
        return this.values;
    }

    public Input setValues(Map<String, Object> values) { this.values = values; return this; }

    public Input clear()
    {
        this.values = new HashMap();
        return this;
    }

    public Object get(String name)
    {
        return this.values.get(name);
    }
}
