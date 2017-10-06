package com.query.maker;

import java.util.HashMap;
import java.util.Map;

public class Order
{
    private Map<String, String> values;

    /**
     * Instantiate Order
     * Set values as empty Map
     */
    public Order() { this.values = new HashMap<String, String>(); }

    /**
     * Put a value into values
     *
     * @param key key of map
     * @param value value put
     *
     * @return Order
     */
    public Order addValue(String key, String value)
    {
        String valueUp = value.toUpperCase();
        if (!"ASC".equals(valueUp) && !"DESC".equals(valueUp)) {
            this.values.put(key, "ASC");
        } else {
            this.values.put(key, valueUp);
        }
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
     * @return Order
     */
    public Order clear()
    {
        this.values = new HashMap<String, String>();
        return this;
    }
}
