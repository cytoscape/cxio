package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;
import org.junit.Test;

public class EdgeAttributesFragmentReaderETest {

    @Test
    public void test() throws IOException {
        final String t0 = "[" + "{\"edgeAttributes\":[{\"po\":\"e0\",\"n\":\"name1\",\"v\":\"value\"}]}," + "{\"edgeAttributes\":[{\"po\":\"e1\",\"n\":\"name2\",\"v\":\"12\",\"d\":\"integer\"}]},"
                + "{\"edgeAttributes\":[{\"po\":[\"e0\",\"e1\"],\"n\":\"name3\",\"v\":\"true\",\"d\":\"boolean\"}]},"
                + "{\"edgeAttributes\":[{\"po\":[\"e0\",\"e1\",\"e2\"],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"d\":\"short\",\"s\":\"1234\"}]}" + "]";

        final CxElementReader p = CxElementReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.NAME + " aspect", r0.containsKey(EdgeAttributesElement.NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.NAME + " aspect", r0.get(EdgeAttributesElement.NAME).isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.NAME + " aspects", r0.get(EdgeAttributesElement.NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.NAME);

        final EdgeAttributesElement ea0 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea0.getName().equals("name1"));
        assertTrue(ea0.getPropertyOf().size() == 1);
        assertTrue(ea0.getPropertyOf().contains("e0"));
        assertTrue(ea0.getDataType() == ATTRIBUTE_TYPE.STRING);
        assertTrue(ea0.getValues().size() == 1);
        assertTrue(ea0.getValues().contains("value"));

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea1.getName().equals("name2"));
        assertTrue(ea1.getPropertyOf().size() == 1);
        assertTrue(ea1.getPropertyOf().contains("e1"));
        assertTrue(ea1.getDataType() == ATTRIBUTE_TYPE.INTEGER);
        assertTrue(ea1.getValues().size() == 1);
        assertTrue(ea1.getValues().contains("12"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea2.getName().equals("name3"));
        assertTrue(ea2.getPropertyOf().size() == 2);
        assertTrue(ea2.getPropertyOf().contains("e0"));
        assertTrue(ea2.getPropertyOf().contains("e1"));
        assertTrue(ea2.getDataType() == ATTRIBUTE_TYPE.BOOLEAN);
        assertTrue(ea2.getValues().size() == 1);
        assertTrue(ea2.getValues().contains("true"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(3);
        assertTrue(ea3.getName().equals("name4"));
        assertTrue(ea3.getPropertyOf().size() == 3);
        assertTrue(ea3.getPropertyOf().contains("e0"));
        assertTrue(ea3.getPropertyOf().contains("e1"));
        assertTrue(ea3.getPropertyOf().contains("e2"));
        assertTrue(ea3.getDataType() == ATTRIBUTE_TYPE.SHORT);
        assertTrue(ea3.getValues().size() == 2);
        assertTrue(ea3.getValues().contains("1"));
        assertTrue(ea3.getValues().contains("2"));
        assertTrue(ea3.getSubnetwork().equals("1234"));

    }

}
