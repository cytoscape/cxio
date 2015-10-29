package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
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
                + "{\"edgeAttributes\":[{\"po\":[\"e0\",\"e1\",\"e2\"],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"d\":\"list_of_short\",\"s\":\"1234\"}]}" + "]";

        final CxElementReader p = CxElementReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.containsKey(EdgeAttributesElement.ASPECT_NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.get(EdgeAttributesElement.ASPECT_NAME).isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.ASPECT_NAME + " aspects", r0.get(EdgeAttributesElement.ASPECT_NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.ASPECT_NAME);

        final EdgeAttributesElement ea0 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea0.getName().equals("name1"));
        assertTrue(ea0.getPropertyOf().size() == 1);
        assertTrue(ea0.getPropertyOf().contains("e0"));
        assertTrue(ea0.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);
        assertTrue(ea0.getValue().equals("value"));

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea1.getName().equals("name2"));
        assertTrue(ea1.getPropertyOf().size() == 1);
        assertTrue(ea1.getPropertyOf().contains("e1"));
        assertTrue(ea1.getDataType() == ATTRIBUTE_DATA_TYPE.INTEGER);
        assertTrue(ea1.getValue().equals("12"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea2.getName().equals("name3"));
        assertTrue(ea2.getPropertyOf().size() == 2);
        assertTrue(ea2.getPropertyOf().contains("e0"));
        assertTrue(ea2.getPropertyOf().contains("e1"));
        assertTrue(ea2.getDataType() == ATTRIBUTE_DATA_TYPE.BOOLEAN);
        assertTrue(ea2.getValue().equals("true"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(3);
        assertTrue(ea3.getName().equals("name4"));
        assertTrue(ea3.getPropertyOf().size() == 3);
        assertTrue(ea3.getPropertyOf().contains("e0"));
        assertTrue(ea3.getPropertyOf().contains("e1"));
        assertTrue(ea3.getPropertyOf().contains("e2"));
        assertTrue(ea3.getDataType() == ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT);
        assertTrue(ea3.getValues().size() == 2);
        assertTrue(ea3.getValues().contains("1"));
        assertTrue(ea3.getValues().contains("2"));
        assertTrue(ea3.getSubnetwork().equals("1234"));

    }

}
