package com.query.maker;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Input
{
    private Map<String, Object> values;

    /**
     * Instantiate Input
     * Set values as empty Map
     */
    public Input() { this.values = new HashMap<String, Object>(); }

    /**
     * Put a value into values
     *
     * @param key key of map
     * @param value value put
     *
     * @return Input
     */
    public Input addValue(String key, Object value)
    {
        this.values.put(key, value);
        return this;
    }

    /**
     * @return the content of values
     */
    Map<String, Object> getValues()
    {
        return this.values;
    }

    /**
     * @return json string of values
     */
    public String toJSONString()
    {
        return new Gson().toJson(this);
    }

    /**
     * Set values with Map
     *
     * @param values Map to set
     *
     * @return Input
     */
    public Input setValues(Map<String, Object> values) { this.values = values; return this; }

    /**
     * clear values
     *
     * @return Input
     */
    public Input clear()
    {
        this.values = new HashMap<String, Object>();
        return this;
    }

    /**
     * @param name name of key returned
     *
     * @return the value from a defined key
     */
    Object get(String name)
    {
        return this.values.get(name);
    }
}
