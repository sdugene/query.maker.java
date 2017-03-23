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
        if (this.values.get("or") != null) {
            orValue = (Map) this.values.get("or");
        }

        orValue.put("KEY"+orValue.size()+"-"+key, value);
        this.values.put("or", orValue);
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
