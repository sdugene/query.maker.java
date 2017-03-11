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

    public void setValues(Map<String, Object> values) { this.values = values; }

    public Input clear()
    {
        this.values = new HashMap();
        return this;
    }
}
