package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CyViewsElement;
import org.cxio.aspects.readers.CyViewsFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class CyViewsTest {

    private static final Object CX_VIEWS_STR_1 = "[{\"cyViews\":[{\"@id\":1,\"s\":2},{\"@id\":22,\"s\":33}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

    @Test
    public void test1() {
        final CyViewsElement v1 = new CyViewsElement(1L, 2L);
        final CyViewsElement v2 = new CyViewsElement(22L, 33L);
        v1.toString();
        v2.toString();
        assertTrue(v1.getViewId() == 1L);
        assertTrue(v2.getViewId() == 22L);

        assertTrue(v1.getSubnetworkId() == 2L);
        assertTrue(v2.getSubnetworkId() == (3L + 30L));

    }

    @Test
    public void test2() throws IOException {
        final CyViewsElement v1 = new CyViewsElement(1L, 2L);
        final CyViewsElement v2 = new CyViewsElement(22L, 33L);
        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstanceWithAllAvailableWriters(out1, false, false);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(v1);
        l1.add(v2);

        w1.start();
        w1.writeAspectElements(l1);
        w1.end(true, "");
        assertEquals(CX_VIEWS_STR_1, out1.toString());

    }

    @Test
    public void test3() throws IOException {
        final CyViewsFragmentReader r = CyViewsFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(CX_VIEWS_STR_1, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue(r0.containsKey(CyViewsElement.ASPECT_NAME));

        assertFalse(r0.get(CyViewsElement.ASPECT_NAME).isEmpty());

        assertTrue(r0.get(CyViewsElement.ASPECT_NAME).size() == 2);

        final List<AspectElement> aspects = r0.get(CyViewsElement.ASPECT_NAME);

        final CyViewsElement v0 = (CyViewsElement) aspects.get(0);
        final CyViewsElement v1 = (CyViewsElement) aspects.get(1);
        assertTrue(v0.getAspectName().equals(CyViewsElement.ASPECT_NAME));

        assertTrue(v0.getSubnetworkId() == 1L);
        assertTrue(v1.getSubnetworkId() == 22L);

        assertTrue(v0.getViewId() == 2L);
        assertTrue(v1.getViewId() == (3L + 30L));

    }

}
