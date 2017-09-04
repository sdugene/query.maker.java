package com.query.maker;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity
{
    /**
     * @return the class name of current object
     */
    public String getClassName()
    {
        return this.getClass().getSimpleName();
    }

    public Map<String, Object> toMap()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return gson.fromJson(json, HashMap.class);
    }

    /**
     * @return the value of id
     */
    public abstract Long getId();
}