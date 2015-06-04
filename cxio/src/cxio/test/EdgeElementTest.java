package cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cxio.EdgeElement;

public class EdgeElementTest {

    @Test
    public void test() {
        final EdgeElement e0 = new EdgeElement("0", "a", "b");
        final EdgeElement e1 = new EdgeElement("0", "c", "d");
        final EdgeElement e2 = new EdgeElement("1", "e", "f");
        assertTrue(e0.equals(e1));
        assertTrue(e0.equals(e0));
        assertTrue(e1.equals(e0));
        assertFalse(e2.equals(e1));
        assertFalse(e1.equals(e2));
        assertTrue(e0.getId().equals("0"));
        assertTrue(e0.getSource().equals("a"));
        assertTrue(e0.getTarget().equals("b"));
    }

}
