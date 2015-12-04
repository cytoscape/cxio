package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.aspects.readers.CyGroupsFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class CyGroupsFragmentReaderTest {

    @Test
    public void test() throws IOException {
        final String t0 = CyGroupsFragmentWriterTest.CX_GROUPS_STR;

        final CyGroupsFragmentReader r = CyGroupsFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxReader p = CxReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + CyGroupsElement.ASPECT_NAME + " aspect", r0.containsKey(CyGroupsElement.ASPECT_NAME));

        assertFalse("failed to parse " + CyGroupsElement.ASPECT_NAME + " aspect", r0.get(CyGroupsElement.ASPECT_NAME).isEmpty());

        assertTrue("failed to parse expected number of " + CyGroupsElement.ASPECT_NAME + " aspects", r0.get(CyGroupsElement.ASPECT_NAME).size() == 1);

        final List<AspectElement> aspects = r0.get(CyGroupsElement.ASPECT_NAME);

        final CyGroupsElement g0 = (CyGroupsElement) aspects.get(0);

        assertTrue(g0.getGroupId() == 1L);
        assertTrue(g0.getView() == 222L);
        assertTrue(g0.getName().equals("name"));
        assertTrue(g0.getExternalEdges().size() == 2);
        assertTrue(g0.getInternalEdges().size() == 2);
        assertTrue(g0.getNodes().size() == 2);

    }

}
