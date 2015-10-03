package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.AspectElementCounts;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.Util;
import org.junit.Test;

public class ChecksumTest {

    @Test
    public void test() throws IOException {
        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("node0"));
        nodes_elements.add(new NodesElement("node1"));
        nodes_elements.add(new NodesElement("node2"));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement("node0", 12, 21, 1));
        cartesian_elements.add(new CartesianLayoutElement("node1", 42, 23, 2));
        cartesian_elements.add(new CartesianLayoutElement("node2", 34, 23, 3));

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("edge0", "name", "A", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea1 = new EdgeAttributesElement("edge0", "weight", "2", ATTRIBUTE_DATA_TYPE.INTEGER);
        final EdgeAttributesElement ea2 = new EdgeAttributesElement("edge1", "name", "B", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea3 = new EdgeAttributesElement("edge1", "weight", "3", ATTRIBUTE_DATA_TYPE.INTEGER);

        final List<AspectElement> edge_attributes_elements = new ArrayList<AspectElement>();
        edge_attributes_elements.add(ea0);
        edge_attributes_elements.add(ea1);
        edge_attributes_elements.add(ea2);
        edge_attributes_elements.add(ea3);

        final ArrayList<String> v0 = new ArrayList<String>();
        v0.add("0.0");
        v0.add("0.1");
        final ArrayList<String> v1 = new ArrayList<String>();
        v1.add("1.0");
        v1.add("1.1");
        final ArrayList<String> v2 = new ArrayList<String>();
        v2.add("2.0");
        v2.add("2.1");

        final NodeAttributesElement na0 = new NodeAttributesElement("node0", "expression", v0, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na1 = new NodeAttributesElement("node1", "expression", v1, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na2 = new NodeAttributesElement("node2", "expression", v2, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);

        final ArrayList<String> n = new ArrayList<String>();
        n.add("node0");
        n.add("node1");
        n.add("node2");

       
        final NodeAttributesElement na3 = new NodeAttributesElement("subnet 1", n, "species", "Mus musculus", ATTRIBUTE_DATA_TYPE.STRING);

        final List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        // Writing to CX
        // -------------
        final OutputStream out0 = new ByteArrayOutputStream();

        final CxWriter w0 = CxWriter.createInstanceWithAllAvailableWriters(out0, true, true);

        w0.start();
        w0.writeAspectElements(edges_elements);
        w0.writeAspectElements(nodes_elements);
        w0.writeAspectElements(cartesian_elements);
        w0.writeAspectElements(edge_attributes_elements);
        w0.writeAspectElements(node_attributes_elements);
        w0.end();

        final String cx_json_str0 = out0.toString();

        final AspectElementCounts cw0 = w0.getAspectElementCounts();

        // Writing to CX
        // -------------
        final OutputStream out1 = new ByteArrayOutputStream();

        final CxWriter w1 = CxWriter.createInstanceWithAllAvailableWriters(out1, true, true);

        w1.start();
        w1.writeAspectElements(nodes_elements);
        w1.writeAspectElements(cartesian_elements);
        w1.writeAspectElements(edge_attributes_elements);
        w1.writeAspectElements(node_attributes_elements);
        w1.end();

        final String cx_json_str1 = out1.toString();

        final AspectElementCounts cw1 = w1.getAspectElementCounts();

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers0 = new HashSet<>();

        final EdgesFragmentReader er = EdgesFragmentReader.createInstance();

        readers0.add(er);
        readers0.add(NodesFragmentReader.createInstance());
        readers0.add(CartesianLayoutFragmentReader.createInstance());
        readers0.add(EdgeAttributesFragmentReader.createInstance());
        readers0.add(NodeAttributesFragmentReader.createInstance());
        final CxReader p0 = CxReader.createInstance(cx_json_str0, true, true, readers0);

        while (p0.hasNext()) {
            p0.getNext();
        }

        final AspectElementCounts cr0 = p0.getAspectElementCounts();

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers1 = new HashSet<>();

        readers1.add(er);
        readers1.add(NodesFragmentReader.createInstance());
        readers1.add(CartesianLayoutFragmentReader.createInstance());
        readers1.add(EdgeAttributesFragmentReader.createInstance());
        readers1.add(NodeAttributesFragmentReader.createInstance());
        final CxReader p1 = CxReader.createInstance(cx_json_str1, true, true, readers1);

        while (p1.hasNext()) {
            p1.getNext();
        }

        final byte[] cs_r0 = p0.getMd5Checksum();
        final byte[] cs_r1 = p1.getMd5Checksum();
        final byte[] cs_w0 = w0.getMd5Checksum();
        final byte[] cs_w1 = w1.getMd5Checksum();

        final AspectElementCounts cr1 = p1.getAspectElementCounts();

        assertTrue(AspectElementCounts.isCountsAreEqual(cw0, cw0));
        assertTrue(AspectElementCounts.isCountsAreEqual(cr0, cr0));
        assertTrue(AspectElementCounts.isCountsAreEqual(cw1, cw1));
        assertTrue(AspectElementCounts.isCountsAreEqual(cr1, cr1));
        assertTrue(AspectElementCounts.isCountsAreEqual(cr0, cw0));
        assertTrue(AspectElementCounts.isCountsAreEqual(cr1, cw1));
        assertTrue(AspectElementCounts.isCountsAreEqual(cw0, cr0));
        assertTrue(AspectElementCounts.isCountsAreEqual(cw1, cr1));

        assertFalse(AspectElementCounts.isCountsAreEqual(cr0, cr1));
        assertFalse(AspectElementCounts.isCountsAreEqual(cr1, cr0));

        assertFalse(AspectElementCounts.isCountsAreEqual(cw0, cw1));
        assertFalse(AspectElementCounts.isCountsAreEqual(cw1, cw0));

        assertTrue(Util.isAreByteArraysEqual(cs_r0, cs_w0));
        assertTrue(Util.isAreByteArraysEqual(cs_r1, cs_w1));
        assertTrue(Util.isAreByteArraysEqual(cs_w0, cs_r0));
        assertTrue(Util.isAreByteArraysEqual(cs_w1, cs_r1));

        assertFalse(Util.isAreByteArraysEqual(cs_r0, cs_w1));
        assertFalse(Util.isAreByteArraysEqual(cs_w1, cs_r0));

        assertFalse(Util.isAreByteArraysEqual(cs_w0, cs_r1));
        assertFalse(Util.isAreByteArraysEqual(cs_r1, cs_w0));

    }

}
