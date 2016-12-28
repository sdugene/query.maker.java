package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class Criteria
{
    private Map<String, Object> values;

    public Criteria() { this.values = new HashMap(); }

    public Criteria addValue(String key, String value)
    {
        this.values.put(key, value);
        return this;
    }

    public Map<String, Object> getValues()
    {
        return this.values;
    }
}
