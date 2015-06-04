package cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cxio.NodeElement;

public class NodeElementTest {

    @Test
    public void test() {
        final NodeElement n0 = new NodeElement("0");
        final NodeElement n1 = new NodeElement("0");
        final NodeElement n2 = new NodeElement("1");
        assertTrue(n0.equals(n1));
        assertTrue(n0.equals(n0));
        assertTrue(n1.equals(n0));
        assertFalse(n2.equals(n1));
        assertFalse(n1.equals(n2));
        assertTrue(n0.getId().equals("0"));
    }

}
