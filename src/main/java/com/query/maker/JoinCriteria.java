package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class JoinCriteria
{
    private Map<String, Object> values;

    public JoinCriteria() { this.values = new HashMap(); }

    public JoinCriteria addValue(String key, Object value)
    {
        this.values.put(key, value);
        return this;
    }

    public Map<String, Object> getValues()
    {
        return this.values;
    }

    public JoinCriteria setValues(Map<String, Object> values) { this.values = values; return this; }

    public JoinCriteria clear()
    {
        this.values = new HashMap();
        return this;
    }
}