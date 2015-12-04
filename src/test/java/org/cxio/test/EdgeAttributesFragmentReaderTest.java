package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class EdgeAttributesFragmentReaderTest {
    
    
    
    @Test
    public void test0() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    } 
    
    @Test
    public void test00() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},[],{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    } 
    
    @Test
    public void test000() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxElementReader p = CxElementReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    } 
    
    @Test
    public void test0000() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},[],{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxElementReader p = CxElementReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    } 

    @Test
    public void test1() throws IOException {
        final String t0 = "[" + TestUtil.NUMBER_VERIFICATION + ",{\"edgeAttributes\":[{\"po\":0,\"n\":\"n1\",\"v\":\"value\"}]},"
                + "{\"edgeAttributes\":[{\"po\":1,\"n\":\"n2\",\"v\":\"12\",\"d\":\"integer\"}]}," + "{\"edgeAttributes\":[{\"po\":[0,1],\"n\":\"n3\",\"v\":\"true\",\"d\":\"boolean\"}]},"
                + "{\"edgeAttributes\":[{\"po\":[0,1,2],\"n\":\"n4\",\"v\":[1,2],\"d\":\"list_of_short\",\"s\":\"1234\"}]}" + "]";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.containsKey(EdgeAttributesElement.ASPECT_NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.get(EdgeAttributesElement.ASPECT_NAME).isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.ASPECT_NAME + " aspects", r0.get(EdgeAttributesElement.ASPECT_NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.ASPECT_NAME);

        final EdgeAttributesElement ea0 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea0.getName().equals("n1"));
        assertTrue(ea0.getPropertyOf().size() == 1);
        assertTrue(ea0.getPropertyOf().contains(0L));
        assertTrue(ea0.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);
        assertTrue(ea0.getValue().equals("value"));

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea1.getName().equals("n2"));
        assertTrue(ea1.getPropertyOf().size() == 1);
        assertTrue(ea1.getPropertyOf().contains(1L));
        assertTrue(ea1.getDataType() == ATTRIBUTE_DATA_TYPE.INTEGER);
        assertTrue(ea1.getValue().equals("12"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea2.getName().equals("n3"));
        assertTrue(ea2.getPropertyOf().size() == 2);
        assertTrue(ea2.getPropertyOf().contains(0L));
        assertTrue(ea2.getPropertyOf().contains(1L));
        assertTrue(ea2.getDataType() == ATTRIBUTE_DATA_TYPE.BOOLEAN);
        assertTrue(ea2.getValue().equals("true"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(3);
        assertTrue(ea3.getName().equals("n4"));
        assertTrue(ea3.getPropertyOf().size() == 3);
        assertTrue(ea3.getPropertyOf().contains(0L));
        assertTrue(ea3.getPropertyOf().contains(1L));
        assertTrue(ea3.getPropertyOf().contains(2L));
        assertTrue(ea3.getDataType() == ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT);
        assertTrue(ea3.getValues().size() == 2);
        assertTrue(ea3.getValues().contains("1"));
        assertTrue(ea3.getValues().contains("2"));
        assertTrue(ea3.getSubnetwork() == 1234);

    }

}
