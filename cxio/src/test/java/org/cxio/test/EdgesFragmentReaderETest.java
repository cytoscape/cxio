package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;
import org.junit.Test;

public class EdgesFragmentReaderETest {

    @Test
    public void testEdgesAspectParsing() throws IOException, ClassNotFoundException {
        final String t0 = "["

        + "{\"edges\":[{\"@id\":\"e2\",\"s\":\"_4\",\"t\":\"_5\"}]}," + "{\"edges\":[{\"@id\":\"e3\",\"s\":\"_6\",\"t\":\"_7\",\"r\":\"rel A\"}]}" + "]";

        final CxElementReader p = CxElementReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgesElement.NAME + " aspect", r0.containsKey(EdgesElement.NAME));

        assertFalse("failed to parse " + EdgesElement.NAME + " aspect", r0.get(EdgesElement.NAME).isEmpty());

        assertTrue("failed to parse expected number of " + EdgesElement.NAME + " aspects", r0.get(EdgesElement.NAME).size() == 2);

        final List<AspectElement> edge_aspects = r0.get(EdgesElement.NAME);

        assertTrue("failed to get expected NodeAspect instance", edge_aspects.get(0) instanceof EdgesElement);

        assertTrue("failed to get expected " + EdgesElement.NAME + " aspect", edge_aspects.contains(new EdgesElement("e2", "0", "0")));
        assertTrue("failed to get expected " + EdgesElement.NAME + " aspect", edge_aspects.contains(new EdgesElement("e3", "0", "0")));

        assertTrue(((EdgesElement) edge_aspects.get(1)).getRelationship().equals("rel A"));

    }

}
