package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;
import org.junit.Test;

public class NodesFragmentReaderTest {

    @Test
    public void testNodeAspectParsing() throws IOException, ClassNotFoundException {
        final String t0 = "[" + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]}," + "{\"nodes\":[{\"@id\":\"_4\"}]},"
                + "{\"nodes\":[{\"@id\":\"_5\"}]},"
                + "{\"nodes\":[{\"@id\":\"_6\"}]}," + "{\"nodes\":[{\"@id\":\"_7\"}]}" + "]";

        final CxReader p = CxReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + NodesElement.NAME + " aspect", r0.containsKey(NodesElement.NAME));

        assertFalse("failed to parse " + NodesElement.NAME + " aspect", r0.get(NodesElement.NAME).isEmpty());

        assertTrue("failed to parse expected number of " + NodesElement.NAME + " aspects", r0.get(NodesElement.NAME).size() == 8);

        final List<AspectElement> node_aspects = r0.get(NodesElement.NAME);

        assertTrue("failed to get expected NodeAspect instance", node_aspects.get(0) instanceof NodesElement);

        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_0")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_1")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_2")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_3")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_4")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_5")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_6")));
        assertTrue("failed to get expected " + NodesElement.NAME + " aspect", node_aspects.contains(new NodesElement("_7")));

    }

}
