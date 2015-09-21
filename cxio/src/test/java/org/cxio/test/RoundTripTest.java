package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class RoundTripTest {

    private final static String A0  = "[{\"nodes\":[{\"@id\":\"node0\"},{\"@id\":\"node1\"},{\"@id\":\"node2\"}]},"
            + "{\"edges\":[{\"@id\":\"edge0\",\"s\":\"node0\",\"t\":\"node1\"},{\"@id\":\"edge1\",\"s\":\"node0\",\"t\":\"node2\"}]},"
            + "{\"cartesianLayout\":[{\"node\":\"node0\",\"x\":12.0,\"y\":21.0,\"z\":1.0},{\"node\":\"node1\",\"x\":42.0,\"y\":23.0,\"z\":2.0},{\"node\":\"node2\",\"x\":34.0,\"y\":23.0,\"z\":3.0}]},"
            + "{\"nodeAttributes\":[{\"po\":\"node0\",\"n\":\"expression\",\"v\":[\"0.0\",\"0.1\"],\"d\":\"double\"},{\"po\":\"node1\",\"n\":\"expression\",\"v\":[\"1.0\",\"1.1\"],\"d\":\"double\"},{\"po\":\"node2\",\"n\":\"expression\",\"v\":[\"2.0\",\"2.1\"],\"d\":\"double\"},{\"s\":\"subnet 1\",\"po\":[\"node0\",\"node1\",\"node2\"],\"n\":\"species\",\"v\":\"Mus musculus\"}]},{\"edgeAttributes\":[{\"po\":\"edge0\",\"n\":\"name\",\"v\":\"A\"},{\"po\":\"edge0\",\"n\":\"weight\",\"v\":\"2\",\"d\":\"integer\"},{\"po\":\"edge1\",\"n\":\"name\",\"v\":\"B\"},{\"po\":\"edge1\",\"n\":\"weight\",\"v\":\"3\",\"d\":\"integer\"}]}]";

    private final static String A1  = "[{\"somethingelse\":[{\"i\":\"edge0\",\"s\":\"node0\",\"t\":\"node1\"},{\"iddd\":\"edge1\",\"s\":\"node0\",\"t\":\"node2\"}]},"
            + "{\"somethingelse2\":[{\"ids\":[\"a\",\"b\",\"c\"],\"s\":\"node0\",\"t\":\"node1\"},{\"idz\":\"edge1\",\"s\":\"node0\",\"t\":\"node2\"}]},"
            + "{\"somethingelse3\":[{\"xx\":{\"a\":\"b\",\"c\":[\"x\"]},\"s\":\"node0\",\"t\":\"node1\"},{\"zz\":\"edge1\",\"s\":\"node0\",\"t\":\"node2\"}]},"
            + "{\"somethingelse4\":[\"a\",\"b\"]},"
            + "{\"somethingelse5\":[\"a\",\"b\", {\"c\":[\"d\",\"e\",{\"f\":\"g\"}]},{\"h\":[\"h1\",\"h2\"]}]},"
            + "{\"somethingelse6\":[  {\"xx\":{\"a\":\"b\",\"c\":[\"x\"]}}]},"
            + "{\"edges\":[{\"@id\":\"edge0\",\"s\":\"node0\",\"t\":\"node1\"},{\"@id\":\"edge1\",\"s\":\"node0\",\"t\":\"node2\"}]},"
            + "{\"nodes\":[{\"@id\":\"node0\"},{\"@id\":\"node1\"},{\"@id\":\"node2\"}]},"
            + "{\"cartesianLayout\":[{\"node\":\"node0\",\"x\":12.0,\"y\":21.0,\"z\":1.0},{\"node\":\"node1\",\"x\":42.0,\"y\":23.0,\"z\":2.0},{\"node\":\"node2\",\"x\":34.0,\"y\":23.0,\"z\":3.0}]},"
            + "{\"edgeAttributes\":[{\"po\":\"edge0\",\"n\":\"name\",\"v\":\"A\"},{\"po\":\"edge0\",\"n\":\"weight\",\"v\":\"2\",\"d\":\"integer\"},{\"po\":\"edge1\",\"n\":\"name\",\"v\":\"B\"},{\"po\":\"edge1\",\"n\":\"weight\",\"v\":\"3\",\"d\":\"integer\"}]},{\"nodeAttributes\":[{\"po\":\"node0\",\"n\":\"expression\",\"v\":[\"0.0\",\"0.1\"],\"d\":\"double\"},{\"po\":\"node1\",\"n\":\"expression\",\"v\":[\"1.0\",\"1.1\"],\"d\":\"double\"},{\"po\":\"node2\",\"n\":\"expression\",\"v\":[\"2.0\",\"2.1\"],\"d\":\"double\"},{\"s\":\"subnet 1\",\"po\":[\"node0\",\"node1\",\"node2\"],\"n\":\"species\",\"v\":\"Mus musculus\"}]},{\"somethingelse7\":[\"aa\",\"bb\", {\"cc\":[\"dd\",\"ee\",{\"f\":\"g\"}]},{\"h\":[\"h1\",\"h2\"]}]}]";

    private final static String A1R = "[{\"nodes\":[{\"@id\":\"node0\"},{\"@id\":\"node1\"},{\"@id\":\"node2\"}]},"
            + "{\"edges\":[{\"@id\":\"edge0\",\"s\":\"node0\",\"t\":\"node1\"},{\"@id\":\"edge1\",\"s\":\"node0\",\"t\":\"node2\"}]},"
            + "{\"cartesianLayout\":[{\"node\":\"node0\",\"x\":12.0,\"y\":21.0,\"z\":1.0},{\"node\":\"node1\",\"x\":42.0,\"y\":23.0,\"z\":2.0},{\"node\":\"node2\",\"x\":34.0,\"y\":23.0,\"z\":3.0}]},"
            + "{\"nodeAttributes\":[{\"po\":\"node0\",\"n\":\"expression\",\"v\":[\"0.0\",\"0.1\"],\"d\":\"double\"},{\"po\":\"node1\",\"n\":\"expression\",\"v\":[\"1.0\",\"1.1\"],\"d\":\"double\"},{\"po\":\"node2\",\"n\":\"expression\",\"v\":[\"2.0\",\"2.1\"],\"d\":\"double\"},{\"s\":\"subnet 1\",\"po\":[\"node0\",\"node1\",\"node2\"],\"n\":\"species\",\"v\":\"Mus musculus\"}]},"
            + "{\"edgeAttributes\":[{\"po\":\"edge0\",\"n\":\"name\",\"v\":\"A\"},{\"po\":\"edge0\",\"n\":\"weight\",\"v\":\"2\",\"d\":\"integer\"},{\"po\":\"edge1\",\"n\":\"name\",\"v\":\"B\"},{\"po\":\"edge1\",\"n\":\"weight\",\"v\":\"3\",\"d\":\"integer\"}]}]";

    @Test
    public void testRoundTrip0() throws IOException {
        final String a0 = "[]";
        final String res = TestUtil.cyCxRoundTrip(a0, true);
        assertEquals(a0, res);
    }

    @Test
    public void testRoundTrip1() throws IOException {

        final String res = TestUtil.cyCxRoundTrip(A0, true);
        assertEquals(A0, res);

    }

    @Test
    public void testRoundTrip2() throws IOException {

        final String res = TestUtil.cyCxElementRoundTrip(A0, true);
        assertEquals(A0, res);

    }

    @Test
    public void testRoundTrip3() throws IOException {

        final String res = TestUtil.cyCxRoundTrip(A1, false);
        assertEquals(A1R, res);

    }

    @Test
    public void testRoundTrip4() throws IOException {

        final String res = TestUtil.cyCxElementRoundTrip(A1, false);
        assertEquals(A1R, res);

    }

}
