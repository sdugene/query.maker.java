package com.query.maker;

/**
 * Created by Sebastien Dugene on 12/25/2017.
 */
public abstract class Entity
{
    /**
     * @return the class name of current object
     */
    public String getClassName()
    {
        return this.getClass().getSimpleName();
    }

    /**
     * @return the value of id
     */
    public abstract Long getId();
}