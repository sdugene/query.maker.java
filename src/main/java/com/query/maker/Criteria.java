package com.query.maker;

import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

public class Criteria
{
    private Map<String, Object> values = new HashMap<String, Object>();

    public Criteria addValue(String key, Object value)
    {
        this.values.put(key, value);
        return this;
    }

    public Criteria addValue(String key, Object value, String operator)
    {
        return addArrayValue(key, value, operator+"_not");
    }

    public Criteria orValue(String key, Object value)
    {
        return addArrayValue(key, value, "or");
    }

    public Criteria notValue(String key, Object value)
    {
        return addArrayValue(key, value, "and_not");
    }

    private Criteria addArrayValue(String key, Object value, String operator)
    {
        Map<String, Object> notValue = new HashMap<String, Object>();
        if (this.values.get(operator) != null) {
            try {
                BeanUtils.populate(notValue, (Map) this.values.get(operator));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        notValue.put("KEY"+notValue.size()+key, value);
        this.values.put(operator, notValue);
        return this;
    }

    Map<String, Object> getValues()
    {
        return this.values;
    }

    public Criteria setValues(Map<String, Object> values) { this.values = values; return this; }

    public Criteria clear()
    {
        this.values = new HashMap<String, Object>();
        return this;
    }
}
