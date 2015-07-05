package io.curly.commons.web;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Joao Pedro Evangelista
 */
public class HttpHeadersTests {

    @Test
    public void testAssertName() throws Exception {
        Assert.assertEquals(HttpHeaders.getName(HttpHeaders.API_V1_HAL), "Accept");
    }

    @Test
    public void testAssertValue() throws Exception {
        Assert.assertEquals(HttpHeaders.getValue(HttpHeaders.API_V1_HAL), "application/vnd.curly.v1+hal+json");


    }
}
