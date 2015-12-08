package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CyTableColumnElement;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class CyTableColumnTest {

    @Test
    public void test1() {
        final CyTableColumnElement e0 = new CyTableColumnElement(12L, "nodes", "weight", ATTRIBUTE_DATA_TYPE.DOUBLE);
        assertTrue(e0.getAspectName().equals(CyTableColumnElement.ASPECT_NAME));
        assertTrue(e0.getDataType() == ATTRIBUTE_DATA_TYPE.DOUBLE);
        assertTrue(e0.getSubnetwork().equals(12L));
        assertTrue(e0.getAppliesTo().equals("nodes"));

    }

    @Test
    public void test2() throws IOException {
        final CyTableColumnElement e0 = new CyTableColumnElement(0L, "nodes", "weight", ATTRIBUTE_DATA_TYPE.DOUBLE);
        final CyTableColumnElement e1 = new CyTableColumnElement(1L, "edges", "exp", ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final CyTableColumnElement e2 = new CyTableColumnElement(2L, "nodes", "name", ATTRIBUTE_DATA_TYPE.STRING);
        final CyTableColumnElement e3 = new CyTableColumnElement("nodes", "all name", ATTRIBUTE_DATA_TYPE.STRING);

        final List<AspectElement> l = new ArrayList<AspectElement>();
        l.add(e0);
        l.add(e1);
        l.add(e2);
        l.add(e3);

        final OutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out0, false, false);

        w.start();
        w.writeAspectElements(l);
        w.end(true, "");

        System.out.println(out0);

        final CxReader p = CxReader.createInstance(out0.toString(), CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + CyTableColumnElement.ASPECT_NAME + " aspect", r0.containsKey(CyTableColumnElement.ASPECT_NAME));
        assertFalse("failed to parse " + CyTableColumnElement.ASPECT_NAME + " aspect", r0.get(CyTableColumnElement.ASPECT_NAME).isEmpty());
        assertTrue("failed to get expected number of " + CyTableColumnElement.ASPECT_NAME + " aspects", r0.get(CyTableColumnElement.ASPECT_NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(CyTableColumnElement.ASPECT_NAME);

        final CyTableColumnElement ee0 = (CyTableColumnElement) aspects.get(0);
        final CyTableColumnElement ee1 = (CyTableColumnElement) aspects.get(1);
        final CyTableColumnElement ee2 = (CyTableColumnElement) aspects.get(2);
        final CyTableColumnElement ee3 = (CyTableColumnElement) aspects.get(3);
        assertTrue(ee0.getName().equals("weight"));
        assertTrue(ee0.getSubnetwork().equals(0L));
        assertTrue(ee0.getAppliesTo().equals("nodes"));
        assertTrue(ee0.getDataType() == ATTRIBUTE_DATA_TYPE.DOUBLE);

        assertTrue(ee1.getName().equals("exp"));
        assertTrue(ee1.getSubnetwork().equals(1L));
        assertTrue(ee1.getAppliesTo().equals("edges"));
        assertTrue(ee1.getDataType() == ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);

        assertTrue(ee2.getName().equals("name"));
        assertTrue(ee2.getSubnetwork().equals(2L));
        assertTrue(ee2.getAppliesTo().equals("nodes"));
        assertTrue(ee2.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(ee3.getName().equals("all name"));
        assertTrue(ee3.getSubnetwork() == null);
        assertTrue(ee3.getAppliesTo().equals("nodes"));
        assertTrue(ee3.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);

    }

}
