package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class NodeAttributesFragmentReaderTest {

    @Test
    public void test() throws IOException {
        final String t0 = "[" + TestUtil.NUMBER_VERIFICATION + ",{\"nodeAttributes\":[{\"po\":\"0\",\"n\":\"name1\",\"v\":\"value\"}]},"
                + "{\"nodeAttributes\":[{\"po\":\"1\",\"n\":\"name2\",\"v\":\"12\",\"d\":\"integer\"}]}," + "{\"nodeAttributes\":[{\"po\":[0,1],\"n\":\"name3\",\"v\":\"true\",\"d\":\"boolean\"}]},"
                + "{\"nodeAttributes\":[{\"po\":[1,2,3],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"d\":\"list_of_short\",\"s\":\"1\"}]}" + "]";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + NodeAttributesElement.ASPECT_NAME + " aspect", r0.containsKey(NodeAttributesElement.ASPECT_NAME));
        assertFalse("failed to parse " + NodeAttributesElement.ASPECT_NAME + " aspect", r0.get(NodeAttributesElement.ASPECT_NAME).isEmpty());
        assertTrue("failed to get expected number of " + NodeAttributesElement.ASPECT_NAME + " aspects", r0.get(NodeAttributesElement.ASPECT_NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(NodeAttributesElement.ASPECT_NAME);

        final NodeAttributesElement a0 = (NodeAttributesElement) aspects.get(0);
        assertTrue(a0.getName().equals("name1"));
        assertTrue(a0.getPropertyOf().size() == 1);
        assertTrue(a0.getPropertyOf().contains(0L));
        assertTrue(a0.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);
        assertTrue(a0.getValue().equals("value"));

        final NodeAttributesElement a1 = (NodeAttributesElement) aspects.get(1);
        assertTrue(a1.getName().equals("name2"));
        assertTrue(a1.getPropertyOf().size() == 1);
        assertTrue(a1.getPropertyOf().contains(1L));
        assertTrue(a1.getDataType() == ATTRIBUTE_DATA_TYPE.INTEGER);
        assertTrue(a1.getValue().equals("12"));

        final NodeAttributesElement a2 = (NodeAttributesElement) aspects.get(2);
        assertTrue(a2.getName().equals("name3"));
        assertTrue(a2.getPropertyOf().size() == 2);
        assertTrue(a2.getPropertyOf().contains(0L));
        assertTrue(a2.getPropertyOf().contains(1L));
        assertTrue(a2.getDataType() == ATTRIBUTE_DATA_TYPE.BOOLEAN);
        assertTrue(a2.getValue().equals("true"));

        final NodeAttributesElement a3 = (NodeAttributesElement) aspects.get(3);
        assertTrue(a3.getName().equals("name4"));
        assertTrue(a3.getPropertyOf().size() == 3);
        assertTrue(a3.getPropertyOf().contains(1L));
        assertTrue(a3.getPropertyOf().contains(2L));
        assertTrue(a3.getPropertyOf().contains(3L));
        assertTrue(a3.getDataType() == ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT);
        assertTrue(a3.getValues().size() == 2);
        assertTrue(a3.getValues().contains("1"));
        assertTrue(a3.getValues().contains("2"));
        assertTrue(a3.getSubnetwork() == 1);

    }
}