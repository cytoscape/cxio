package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cxio.aspects.datamodels.EdgesElement;
import org.junit.Test;

public class EdgeElementTest {

    @Test
    public void test() {
        final EdgesElement e0 = new EdgesElement(0, 11, 22);
        final EdgesElement e1 = new EdgesElement(0, 44, 33);
        final EdgesElement e2 = new EdgesElement(1, 55, 66);
        assertTrue(e0.equals(e1));
        assertTrue(e0.equals(e0));
        assertTrue(e1.equals(e0));
        assertFalse(e2.equals(e1));
        assertFalse(e1.equals(e2));
        assertTrue(e0.getId()==0);
        assertTrue(e0.getSource()==11);
        assertTrue(e0.getTarget()==22);
    }

}
