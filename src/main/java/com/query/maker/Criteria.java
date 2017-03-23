package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class Criteria
{
    private Map<String, Object> values = new HashMap();

    public Criteria addValue(String key, Object value)
    {
        this.values.put(key, value);
        return this;
    }

    public Criteria orValue(String key, Object value)
    {
        Map<String, Object> orValue = new HashMap();
        if (this.values.get(key) != null) {
            System.out.println(this.values.get(key));
            orValue = (Map) this.values.get(key);
        }
        orValue.put(key, value);
        System.out.println(orValue);
        this.values.put("or", orValue);
        System.out.println(orValue);
        return this;
    }

    public Map<String, Object> getValues()
    {
        return this.values;
    }

    public Criteria setValues(Map<String, Object> values) { this.values = values; return this; }

    public Criteria clear()
    {
        this.values = new HashMap();
        return this;
    }
}
