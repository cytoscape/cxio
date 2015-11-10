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

import org.cxio.aspects.datamodels.SubNetworkElement;
import org.cxio.aspects.readers.SubNetworkFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class SubnetworkAllTest {

    private static final String STR = "[{\"subNetworks\":[{\"@id\":50,\"nodes\":\"all\",\"edges\":\"all\"}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

    @Test
    public void test1() throws IOException {
        final SubNetworkElement s = new SubNetworkElement(50L);
        s.setEdgesAll(true);
        s.setNodesAll(true);

        final List<AspectElement> l = new ArrayList<AspectElement>();
        l.add(s);

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, false);

        w.start();
        w.writeAspectElements(l);
        w.end(true, "");

        assertEquals(STR, out.toString());
    }

    @Test
    public void test2() throws IOException {
        final SubNetworkFragmentReader r = SubNetworkFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(STR, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue(r0.containsKey(SubNetworkElement.ASPECT_NAME));

        assertFalse(r0.get(SubNetworkElement.ASPECT_NAME).isEmpty());

        assertTrue(r0.get(SubNetworkElement.ASPECT_NAME).size() == 1);

        final List<AspectElement> aspects = r0.get(SubNetworkElement.ASPECT_NAME);

        final SubNetworkElement v0 = (SubNetworkElement) aspects.get(0);

        assertTrue(v0.getAspectName().equals(SubNetworkElement.ASPECT_NAME));

        assertTrue(v0.isEdgesAll());
        assertTrue(v0.isNodesAll());

        assertTrue(v0.getEdges().isEmpty());
        assertTrue(v0.getNodes().isEmpty());

    }

    @Test
    public void test3() throws IOException {
        final SubNetworkFragmentReader r = SubNetworkFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(STR, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue(r0.containsKey(SubNetworkElement.ASPECT_NAME));

        assertFalse(r0.get(SubNetworkElement.ASPECT_NAME).isEmpty());

        assertTrue(r0.get(SubNetworkElement.ASPECT_NAME).size() == 1);

        final List<AspectElement> aspects = r0.get(SubNetworkElement.ASPECT_NAME);

        final SubNetworkElement v0 = (SubNetworkElement) aspects.get(0);

        final List<AspectElement> l = new ArrayList<AspectElement>();
        l.add(v0);

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, false);

        w.start();
        w.writeAspectElements(l);
        w.end(true, "");

        assertEquals(STR, out.toString());

    }

}
