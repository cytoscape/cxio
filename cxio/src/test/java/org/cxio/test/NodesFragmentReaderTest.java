package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class NodesFragmentReaderTest {

    @Test
    public void testNodeAspectParsing() throws IOException, ClassNotFoundException {
        final String t0 = "[" + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]}," + "{\"nodes\":[{\"@id\":\"4\"}]}," + "{\"nodes\":[{\"@id\":\"5\"}]},"
                + "{\"nodes\":[{\"@id\":\"6\"}]}," + "{\"nodes\":[{\"@id\":\"7\",\"n\":\"name 2\",\"r\":\"reps 2\"}]}" + "]";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + NodesElement.ASPECT_NAME + " aspect", r0.containsKey(NodesElement.ASPECT_NAME));

        assertFalse("failed to parse " + NodesElement.ASPECT_NAME + " aspect", r0.get(NodesElement.ASPECT_NAME).isEmpty());

        assertTrue("failed to parse expected number of " + NodesElement.ASPECT_NAME + " aspects", r0.get(NodesElement.ASPECT_NAME).size() == 8);

        final List<AspectElement> node_aspects = r0.get(NodesElement.ASPECT_NAME);

        assertTrue("failed to get expected NodeAspect instance", node_aspects.get(0) instanceof NodesElement);

        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("3")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("4")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("5")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("6")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", node_aspects.contains(new NodesElement("7")));
        assertTrue(((NodesElement) node_aspects.get(7)).getNodeName().equals("name 2"));
        assertTrue(((NodesElement) node_aspects.get(7)).getNodeRepresents().equals("reps 2"));
        assertTrue(((NodesElement) node_aspects.get(6)).getNodeName() == null);
        assertTrue(((NodesElement) node_aspects.get(6)).getNodeRepresents() == null);

    }

}
