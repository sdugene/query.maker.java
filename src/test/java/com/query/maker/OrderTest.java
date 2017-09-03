package com.query.maker;

import org.junit.Test;

public class OrderTest
{
    @Test
    public void instantiateTest()
    {
        Order order = new Order();
        if (!(order instanceof Order)) {
            assert(false);
        }
    }
}