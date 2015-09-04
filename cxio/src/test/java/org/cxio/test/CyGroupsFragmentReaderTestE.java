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
import org.cxio.core.CxElementReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

public class CyGroupsFragmentReaderTestE {

    @Test
    public void test() throws IOException {
        final String t0 = CyGroupsFragmentWriterTest.CX_GROUPS_STR;

        final CyGroupsFragmentReader r = CyGroupsFragmentReader.createInstance();
        final Set<AspectFragmentReader> readers = new HashSet<AspectFragmentReader>();
        readers.add(r);

        final CxElementReader p = CxElementReader.createInstance(t0, readers);
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);

        assertTrue("failed to parse " + CyGroupsElement.NAME + " aspect", r0.containsKey(CyGroupsElement.NAME));

        assertFalse("failed to parse " + CyGroupsElement.NAME + " aspect", r0.get(CyGroupsElement.NAME).isEmpty());

        assertTrue("failed to parse expected number of " + CyGroupsElement.NAME + " aspects", r0.get(CyGroupsElement.NAME).size() == 1);

        final List<AspectElement> aspects = r0.get(CyGroupsElement.NAME);

        final CyGroupsElement g0 = (CyGroupsElement) aspects.get(0);

        assertTrue(g0.getGroupId().equals("group_id"));
        assertTrue(g0.getView().equals("view"));
        assertTrue(g0.getName().equals("name"));
        assertTrue(g0.getExternalEdges().size() == 2);
        assertTrue(g0.getInternalEdges().size() == 2);
        assertTrue(g0.getNodes().size() == 2);

    }

}
