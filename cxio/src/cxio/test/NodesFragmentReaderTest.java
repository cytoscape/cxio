package cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.junit.Test;

import cxio.AspectElement;
import cxio.AspectFragmentReaderManager;
import cxio.Cx;
import cxio.CxParser;
import cxio.NodeElement;

public class NodesFragmentReaderTest {

    @Test
    public void testNodeAspectParsing() throws IOException, ClassNotFoundException {
        final String t0 = "["
                + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"nodes\":\"_0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"_1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edges\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"_0\",\"property\":\"property zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"_1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"}]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"}]}]},"
                + "{\"weHaveNodesToo\":[{\"nodes\":\"nodes\"}]}," + "{\"weHaveEdgesToo\":[{\"edges\":\"edges\"}]},"
                + "{\"nodes\":[{\"@id\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]},"
                + "{\"nodes\":[{\"@id\":\"_4\"}]}," + "{\"nodes\":[{\"@id\":\"_6\"}]},"
                + "{\"nodes\":[{\"@id\":\"_7\"}]}" + "]";

        final CxParser p = CxParser.createInstance(t0, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxParser.parseAsMap(p);

        assertTrue("failed to parse " + Cx.NODES + " aspect", r0.containsKey(Cx.NODES));

        assertFalse("failed to parse " + Cx.NODES + " aspect", r0.get(Cx.NODES).isEmpty());

        assertTrue("failed to parse expected number of " + Cx.NODES + " aspects", r0.get(Cx.NODES).size() == 8);

        final List<AspectElement> node_aspects = r0.get(Cx.NODES);

        assertTrue("failed to get expected NodeAspect instance", node_aspects.get(0) instanceof NodeElement);

        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_3")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_4")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_5")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_6")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", node_aspects.contains(new NodeElement("_7")));

    }

    @Test(expected = java.io.IOException.class)
    public void testNodeAspectParsingWithExpectedException() throws IOException {
        final String t0 = "["
                + "{\"nodes\":[{\"@name\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"node\":\"_0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"_1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edge\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"_0\",\"property\":\"property zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"_1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]}" + "]";

        final CxParser p = CxParser.createInstance(t0, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        CxParser.parseAsMap(p);

    }

    @Test
    public void testNodeAspectParsingWithNoNodeAspectPresent() throws IOException {
        final String t0 = "["
                + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"nodes\":\"_0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"_1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edges\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"_0\",\"property\":\"property zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"_1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"}]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"}]}]},"
                + "{\"weHaveNodesToo\":[{\"nodes\":\"nodes\"}]}," + "{\"weHaveEdgesToo\":[{\"edges\":\"edges\"}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]}" + "]";

        final CxParser p = CxParser.createInstance(t0, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxParser.parseAsMap(p);

        assertFalse("expected to parse no " + Cx.NODES + " aspects, got some", r0.containsKey(Cx.NODES));

    }

}
