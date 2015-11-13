package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class UTF8Test {

    @Test
    public void testUTF8RT1() throws IOException {
        final String t0 = "[{\"edgeAttributes\":[{\"po\":0,\"n\":\"한글hangul\",\"v\":\"value\"},{\"po\":1,\"n\":\"漢字kanjiひらがなカタカナ\",\"v\":\"12\",\"d\":\"integer\"},"
                + "{\"po\":[0,1],\"n\":\"ราชอาณาจักรไทย\",\"v\":\"true\",\"d\":\"boolean\"},"
                + "{\"s\":1234,\"po\":[0,1,2],\"n\":\"繁體字\",\"v\":[\"1\",\"2\"],\"d\":\"list_of_short\"}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.containsKey(EdgeAttributesElement.ASPECT_NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.get(EdgeAttributesElement.ASPECT_NAME).isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.ASPECT_NAME + " aspects", r0.get(EdgeAttributesElement.ASPECT_NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.ASPECT_NAME);

        final EdgeAttributesElement ea0 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea0.getName().equals("한글hangul"));
        assertTrue(ea0.getPropertyOf().size() == 1);
        assertTrue(ea0.getPropertyOf().contains(0L));
        assertTrue(ea0.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);
        assertTrue(ea0.getValue().equals("value"));

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea1.getName().equals("漢字kanjiひらがなカタカナ"));
        assertTrue(ea1.getPropertyOf().size() == 1);
        assertTrue(ea1.getPropertyOf().contains(1L));
        assertTrue(ea1.getDataType() == ATTRIBUTE_DATA_TYPE.INTEGER);
        assertTrue(ea1.getValue().equals("12"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea2.getName().equals("ราชอาณาจักรไทย"));
        assertTrue(ea2.getPropertyOf().size() == 2);
        assertTrue(ea2.getPropertyOf().contains(0L));
        assertTrue(ea2.getPropertyOf().contains(1L));
        assertTrue(ea2.getDataType() == ATTRIBUTE_DATA_TYPE.BOOLEAN);
        assertTrue(ea2.getValue().equals("true"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(3);
        assertTrue(ea3.getName().equals("繁體字"));
        assertTrue(ea3.getPropertyOf().size() == 3);
        assertTrue(ea3.getPropertyOf().contains(0L));
        assertTrue(ea3.getPropertyOf().contains(1L));
        assertTrue(ea3.getPropertyOf().contains(2L));
        assertTrue(ea3.getDataType() == ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT);
        assertTrue(ea3.getValues().size() == 2);
        assertTrue(ea3.getValues().contains("1"));
        assertTrue(ea3.getValues().contains("2"));
        assertTrue(ea3.getSubnetwork() == 1234);
        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstanceWithAllAvailableWriters(out1, false, false);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(ea0);
        l1.add(ea1);
        l1.add(ea2);
        l1.add(ea3);

        w1.startT();
        w1.writeAspectElements(l1);
        w1.end(true, "");

        assertEquals(t0, out1.toString());

    }

    @Test
    public void testUTF8RT2() throws IOException {
        final String t0 = "[{\"edgeAttributes\":[{\"po\":0,\"n\":\"\uD55C\uAE00hangul\",\"v\":\"value\"},{\"po\":1,\"n\":\"\u6F22\u5B57kanji\u3072\u3089\u304C\u306A\u30AB\u30BF\u30AB\u30CA\",\"v\":\"12\",\"d\":\"integer\"},{\"po\":[0,1],\"n\":\"\u0E23\u0E32\u0E0A\u0E2D\u0E32\u0E13\u0E32\u0E08\u0E31\u0E01\u0E23\u0E44\u0E17\u0E22\",\"v\":\"true\",\"d\":\"boolean\"},{\"s\":1234,\"po\":[0,1,2],\"n\":\"\u7E41\u9AD4\u5B57\",\"v\":[\"1\",\"2\"],\"d\":\"list_of_short\"}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.containsKey(EdgeAttributesElement.ASPECT_NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.ASPECT_NAME + " aspect", r0.get(EdgeAttributesElement.ASPECT_NAME).isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.ASPECT_NAME + " aspects", r0.get(EdgeAttributesElement.ASPECT_NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.ASPECT_NAME);

        final EdgeAttributesElement ea0 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea0.getName().equals("한글hangul"));
        assertTrue(ea0.getPropertyOf().size() == 1);
        assertTrue(ea0.getPropertyOf().contains(0L));
        assertTrue(ea0.getDataType() == ATTRIBUTE_DATA_TYPE.STRING);
        assertTrue(ea0.getValue().equals("value"));

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea1.getName().equals("漢字kanjiひらがなカタカナ"));
        assertTrue(ea1.getPropertyOf().size() == 1);
        assertTrue(ea1.getPropertyOf().contains(1L));
        assertTrue(ea1.getDataType() == ATTRIBUTE_DATA_TYPE.INTEGER);
        assertTrue(ea1.getValue().equals("12"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea2.getName().equals("ราชอาณาจักรไทย"));
        assertTrue(ea2.getPropertyOf().size() == 2);
        assertTrue(ea2.getPropertyOf().contains(0L));
        assertTrue(ea2.getPropertyOf().contains(1L));
        assertTrue(ea2.getDataType() == ATTRIBUTE_DATA_TYPE.BOOLEAN);
        assertTrue(ea2.getValue().equals("true"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(3);
        assertTrue(ea3.getName().equals("繁體字"));
        assertTrue(ea3.getPropertyOf().size() == 3);
        assertTrue(ea3.getPropertyOf().contains(0L));
        assertTrue(ea3.getPropertyOf().contains(1L));
        assertTrue(ea3.getPropertyOf().contains(2L));
        assertTrue(ea3.getDataType() == ATTRIBUTE_DATA_TYPE.LIST_OF_SHORT);
        assertTrue(ea3.getValues().size() == 2);
        assertTrue(ea3.getValues().contains("1"));
        assertTrue(ea3.getValues().contains("2"));
        assertTrue(ea3.getSubnetwork() == 1234);
        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstanceWithAllAvailableWriters(out1, false, false);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(ea0);
        l1.add(ea1);
        l1.add(ea2);
        l1.add(ea3);

        w1.startT();
        w1.writeAspectElements(l1);
        w1.end(true, "");

        assertEquals(t0, out1.toString());

    }

}
