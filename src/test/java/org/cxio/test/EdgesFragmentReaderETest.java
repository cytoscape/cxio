package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class EdgesFragmentReaderETest {

    @Test
    public void testEdgesAspectParsing() throws IOException, ClassNotFoundException {
        final String t0 = "[" + TestUtil.NUMBER_VERIFICATION + ",{\"edges\":[{\"@id\":2,\"s\":\"4\",\"t\":\"5\"}]}," + "{\"edges\":[{\"@id\":\"3\",\"s\":\"6\",\"t\":\"7\",\"i\":\"rel A\"}]}" + "]";

        final CxElementReader p = CxElementReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgesElement.ASPECT_NAME + " aspect", r0.containsKey(EdgesElement.ASPECT_NAME));

        assertFalse("failed to parse " + EdgesElement.ASPECT_NAME + " aspect", r0.get(EdgesElement.ASPECT_NAME).isEmpty());

        assertTrue("failed to parse expected number of " + EdgesElement.ASPECT_NAME + " aspects", r0.get(EdgesElement.ASPECT_NAME).size() == 2);

        final List<AspectElement> edge_aspects = r0.get(EdgesElement.ASPECT_NAME);

        assertTrue("failed to get expected NodeAspect instance", edge_aspects.get(0) instanceof EdgesElement);

        assertTrue("failed to get expected " + EdgesElement.ASPECT_NAME + " aspect", edge_aspects.contains(new EdgesElement("2", "0", "0")));
        assertTrue("failed to get expected " + EdgesElement.ASPECT_NAME + " aspect", edge_aspects.contains(new EdgesElement("3", "0", "0")));

        assertTrue(((EdgesElement) edge_aspects.get(1)).getInteraction().equals("rel A"));

    }

}
