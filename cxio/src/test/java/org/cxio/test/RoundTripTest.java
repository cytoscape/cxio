package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class RoundTripTest {

    @Test
    public void testRoundTrip0() throws IOException {
        final String a0 = "[]";
        final String res = TestUtil.cyCxRoundTrip(a0);
        assertEquals(a0, res);
    }

    @Test
    public void testRoundTrip1() throws IOException {

        final String a0 = "[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},"
                + "{\"@id\":\"_3\"},{\"@id\":\"_5\"},{\"@id\":\"_4\"},{\"@id\":\"_6\"},"
                + "{\"@id\":\"_7\"}]},{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\","
                + "\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"},"
                + "{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"},{\"@id\":\"e3\","
                + "\"source\":\"_6\",\"target\":\"_7\"}]},{\"cartesianLayout\":[{\"node\":\"_0\","
                + "\"x\":123.0,\"y\":456.0},{\"node\":\"_1\",\"x\":3.0,\"y\":4.0},{\"node\":\"_2\","
                + "\"x\":5.0,\"y\":6.0}]},{\"edgeAttributes\":[{\"po\":\"e0\",\"n\":\"name1\","
                + "\"v\":\"value\"},{\"po\":\"e1\",\"n\":\"name2\",\"v\":\"12\",\"t\":\"integer\"},"
                + "{\"po\":[\"e0\",\"e1\"],\"n\":\"name3\",\"v\":\"true\",\"t\":\"boolean\"},"
                + "{\"po\":[\"e0\",\"e1\",\"e2\"],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"t\":\"short\"}]}]";

        final String res = TestUtil.cyCxRoundTrip(a0);

        assertEquals(a0, res);

    }

}
