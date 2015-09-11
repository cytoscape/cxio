package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;
import org.junit.Test;

public class NodeAttributesFragmentReaderTestE {

    @Test
    public void test() throws IOException {
        final String t0 = "[" + "{\"nodeAttributes\":[{\"po\":\"n0\",\"n\":\"name1\",\"v\":\"value\"}]}," + "{\"nodeAttributes\":[{\"po\":\"n1\",\"n\":\"name2\",\"v\":\"12\",\"d\":\"integer\"}]},"
                + "{\"nodeAttributes\":[{\"po\":[\"n0\",\"n1\"],\"n\":\"name3\",\"v\":\"true\",\"d\":\"boolean\"}]},"
                + "{\"nodeAttributes\":[{\"po\":[\"n0\",\"n1\",\"n2\"],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"d\":\"short\",\"s\":\"subnet A\"}]}" + "]";

        final CxElementReader p = CxElementReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);

        assertTrue("failed to parse " + NodeAttributesElement.NAME + " aspect", r0.containsKey(NodeAttributesElement.NAME));
        assertFalse("failed to parse " + NodeAttributesElement.NAME + " aspect", r0.get(NodeAttributesElement.NAME).isEmpty());
        assertTrue("failed to get expected number of " + NodeAttributesElement.NAME + " aspects", r0.get(NodeAttributesElement.NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(NodeAttributesElement.NAME);

        final NodeAttributesElement a0 = (NodeAttributesElement) aspects.get(0);
        assertTrue(a0.getName().equals("name1"));
        assertTrue(a0.getPropertyOf().size() == 1);
        assertTrue(a0.getPropertyOf().contains("n0"));
        assertTrue(a0.getDataType() == ATTRIBUTE_TYPE.STRING);
        assertTrue(a0.getValues().size() == 1);
        assertTrue(a0.getValues().contains("value"));

        final NodeAttributesElement a1 = (NodeAttributesElement) aspects.get(1);
        assertTrue(a1.getName().equals("name2"));
        assertTrue(a1.getPropertyOf().size() == 1);
        assertTrue(a1.getPropertyOf().contains("n1"));
        assertTrue(a1.getDataType() == ATTRIBUTE_TYPE.INTEGER);
        assertTrue(a1.getValues().size() == 1);
        assertTrue(a1.getValues().contains("12"));

        final NodeAttributesElement a2 = (NodeAttributesElement) aspects.get(2);
        assertTrue(a2.getName().equals("name3"));
        assertTrue(a2.getPropertyOf().size() == 2);
        assertTrue(a2.getPropertyOf().contains("n0"));
        assertTrue(a2.getPropertyOf().contains("n1"));
        assertTrue(a2.getDataType() == ATTRIBUTE_TYPE.BOOLEAN);
        assertTrue(a2.getValues().size() == 1);
        assertTrue(a2.getValues().contains("true"));

        final NodeAttributesElement a3 = (NodeAttributesElement) aspects.get(3);
        assertTrue(a3.getName().equals("name4"));
        assertTrue(a3.getPropertyOf().size() == 3);
        assertTrue(a3.getPropertyOf().contains("n0"));
        assertTrue(a3.getPropertyOf().contains("n1"));
        assertTrue(a3.getPropertyOf().contains("n2"));
        assertTrue(a3.getDataType() == ATTRIBUTE_TYPE.SHORT);
        assertTrue(a3.getValues().size() == 2);
        assertTrue(a3.getValues().contains("1"));
        assertTrue(a3.getValues().contains("2"));
        assertTrue(a3.getSubnetwork().equals("subnet A"));

    }
}