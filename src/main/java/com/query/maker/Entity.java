package com.query.maker;

public abstract class Entity
{
    public String getClassName()
    {
        return this.getClass().getSimpleName();
    }

    public abstract Long getId();
}