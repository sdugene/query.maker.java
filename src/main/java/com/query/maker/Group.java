package com.query.maker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastien Dugene on 12/23/2017.
 */
public class Group
{
    private Map<String, String> values;

    /**
     * Instantiate Group
     * Set values as empty Map
     */
    public Group() { this.values = new HashMap<String, String>(); }

    /**
     * Put a value into values
     *
     * @param key key of map
     * @param value value put
     *
     * @return Group
     */
    public Group addValue(String key, String value)
    {
        this.values.put(key, value);
        return this;
    }

    /**
     * @return the content of values
     */
    Map<String, String> getValues()
    {
        return this.values;
    }

    /**
     * clear values
     *
     * @return Group
     */
    public Group clear()
    {
        this.values = new HashMap<String, String>();
        return this;
    }
}
