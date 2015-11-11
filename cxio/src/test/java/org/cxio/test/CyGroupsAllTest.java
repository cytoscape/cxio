package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.aspects.readers.CyGroupsFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class CyGroupsAllTest {

    private static final String STR = "[{\"cyGroups\":[{\"@id\":50,\"view\":10,\"name\":\"some group\",\"nodes\":\"all\",\"external_edges\":\"all\",\"internal_edges\":\"all\"}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

    @Test
    public void test1() throws IOException {
        final CyGroupsElement s = new CyGroupsElement(50L, 10L, "some group");
        s.setExternalEdgesAll(true);
        s.setInternalEdgesAll(true);
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
        final CyGroupsFragmentReader r = CyGroupsFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(STR, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        final List<AspectElement> aspects = r0.get(CyGroupsElement.ASPECT_NAME);

        final CyGroupsElement v0 = (CyGroupsElement) aspects.get(0);

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
