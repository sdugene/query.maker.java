package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private Map<String, String> values = new HashMap();

    private Criteria() {}

    private static class SingletonHolder
    {
        private final static Criteria instance = new Criteria();
    }

    public static Criteria getInstance()
    {
        Criteria criteria = SingletonHolder.instance;
        criteria.values = new HashMap();
        return criteria;
    }

    public Criteria addValue(String key, String value)
    {
        this.values.put(key, value);
        return this;
    }

    public Map<String, String> getValues()
    {
        return this.values;
    }
}
