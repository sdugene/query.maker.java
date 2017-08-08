package com.query.maker;

import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastien Dugene on 12/23/2017.
 */
public class Criteria
{
    private Map<String, Object> values = new HashMap<String, Object>();

    /**
     * Instantiate Criteria
     * Set values as empty Map
     */
    public Criteria() { this.values = new HashMap<String, Object>(); }

    /**
     * Put a value into values
     * return Criteria
     *
     * @param key key of map
     * @param value value put
     */
    public Criteria addValue(String key, Object value)
    {
        this.values.put(key, value);
        return this;
    }

    /**
     * Put a value into values with defined operator with "_not"
     *
     * @param key key of map
     * @param value value put
     * @param operator operator
     */
    public Criteria addValue(String key, Object value, String operator)
    {
        return addArrayValue(key, value, operator+"_not");
    }

    /**
     * Put a value into values with operator "or"
     *
     * @param key key of map
     * @param value value put
     */
    public Criteria orValue(String key, Object value)
    {
        return addArrayValue(key, value, "or");
    }

    /**
     * Put a value into values with operator "and_not"
     *
     * @param key key of map
     * @param value value put
     */
    public Criteria notValue(String key, Object value)
    {
        return addArrayValue(key, value, "and_not");
    }

    /**
     * Put a value into values with defined operator
     *
     * @param key key of map
     * @param value value put
     * @param operator operator
     */
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

    /**
     * return the content of values
     */
    Map<String, Object> getValues()
    {
        return this.values;
    }

    /**
     * Set values with Map
     * return Criteria
     *
     * @param values Map to set
     */
    public Criteria setValues(Map<String, Object> values) { this.values = values; return this; }

    /**
     * clear values
     * return Criteria
     */
    public Criteria clear()
    {
        this.values = new HashMap<String, Object>();
        return this;
    }
}
