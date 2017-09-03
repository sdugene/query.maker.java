package com.query.maker;

import org.junit.Test;

public class ColumnTest
{
    @Test
    public void instantiateTest()
    {
        Column column = new Column();
        if (!(column instanceof Column)) {
            assert(false);
        }
    }
}