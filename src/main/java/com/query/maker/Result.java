package com.query.maker;

public class Result extends Entity
{
    private boolean bool = false;

    /**
     * @return the value of id
     */
    public Long getId() { return null; }

    /**
     * @return the value of bool
     */
    public boolean isBool() { return bool; }

    /**
     * set the value of bool
     *
     * @param bool boolean value
     *
     * @return Result
     */
    public Result setBool(boolean bool)
    {
        this.bool = bool;
        return this;
    }
}