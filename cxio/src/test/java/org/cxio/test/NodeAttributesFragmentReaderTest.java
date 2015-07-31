package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;
import org.junit.Test;

public class NodeAttributesFragmentReaderTest {

    @Test
    public void test() throws IOException {
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
                + "{\"weHaveNodesToo\":[{\"nodes\":\"nodes\"}]},"
                + "{\"weHaveEdgesToo\":[{\"edges\":\"edges\"}]},"
                + "{\"nodes\":[{\"@id\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_0\",\"x\":\"123\",\"y\":\"456\"}]},"
                + "{\"nodes\":[{\"@id\":\"_4\"}]},"
                + "{\"nodes\":[{\"@id\":\"_6\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_1\",\"x\":\"3\",\"y\":\"4\"},{\"node\":\"_2\",\"x\":\"5\",\"y\":\"6\"}]},"
                + "{\"nodes\":[{\"@id\":\"_7\"}]},"
                + "{\"nodeAttributesOLD\":[{\"@id\":\"_na0\",\"nodes\":[\"_0\", \"_1\"], \"attributes\":{\"entrez_gene_locuslink\":[\"322397\", \"one more\"],\"name\":[\"_322397\"],\"PSIMI_25_aliases\":[\"322397\",\"80961\"]}},"
                + "{\"@id\":\"_na1\",\"nodes\":[\"_2\"], \"attributes\":{\"key\":[\"value\"]}},"
                + "{\"@id\":\"_na2\",\"nodes\":[\"_3\"]}]},"
                + "{\"edgeAttributesOLD\":[{\"@id\":\"_ea0\",\"edges\":[\"_e0\", \"_e22\"], \"attributes\":{\"interaction\":[\"479019\", \"one more\"],\"name\":[\"768303 (479019) 791595\"],\"PSIMI_25_detection_method\":[\"genetic interference\"]}}]},"
                + "{\"nodeAttributesOLD\":[{\"@id\":\"_na3\",\"nodes\":[\"_33\"]}]},"

                + "{\"nodeAttributesOLD\":[{\"@id\":\"_na4\",\"nodes\":[\"_33\"],\"attributes\":{\"target\":[\"true\"]},  \"types\":{\"target\":\"boolean\"} }]},"

                + "{\"nodeAttributes\":[{\"po\":\"n0\",\"n\":\"name1\",\"v\":\"value\"}]},"
                + "{\"nodeAttributes\":[{\"po\":\"n1\",\"n\":\"name2\",\"v\":\"12\",\"t\":\"integer\"}]},"
                + "{\"nodeAttributes\":[{\"po\":[\"n0\",\"n1\"],\"n\":\"name3\",\"v\":\"true\",\"t\":\"boolean\"}]},"
                + "{\"nodeAttributes\":[{\"po\":[\"n0\",\"n1\",\"n2\"],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"t\":\"short\"}]}"

                + "]";

        final CxReader p = CxReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + NodeAttributesElement.NAME + " aspect",
                   r0.containsKey(NodeAttributesElement.NAME));
        assertFalse("failed to parse " + NodeAttributesElement.NAME + " aspect", r0.get(NodeAttributesElement.NAME)
                    .isEmpty());
        assertTrue("failed to get expected number of " + NodeAttributesElement.NAME + " aspects",
                   r0.get(NodeAttributesElement.NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(NodeAttributesElement.NAME);

        final NodeAttributesElement a0 = (NodeAttributesElement) aspects.get(0);
        assertTrue(a0.getName().equals("name1"));
        assertTrue(a0.getPropertyOf().size() == 1);
        assertTrue(a0.getPropertyOf().contains("n0"));
        assertTrue(a0.getType() == ATTRIBUTE_TYPE.STRING);
        assertTrue(a0.getValues().size() == 1);
        assertTrue(a0.getValues().contains("value"));

        final NodeAttributesElement a1 = (NodeAttributesElement) aspects.get(1);
        assertTrue(a1.getName().equals("name2"));
        assertTrue(a1.getPropertyOf().size() == 1);
        assertTrue(a1.getPropertyOf().contains("n1"));
        assertTrue(a1.getType() == ATTRIBUTE_TYPE.INTEGER);
        assertTrue(a1.getValues().size() == 1);
        assertTrue(a1.getValues().contains("12"));

        final NodeAttributesElement a2 = (NodeAttributesElement) aspects.get(2);
        assertTrue(a2.getName().equals("name3"));
        assertTrue(a2.getPropertyOf().size() == 2);
        assertTrue(a2.getPropertyOf().contains("n0"));
        assertTrue(a2.getPropertyOf().contains("n1"));
        assertTrue(a2.getType() == ATTRIBUTE_TYPE.BOOLEAN);
        assertTrue(a2.getValues().size() == 1);
        assertTrue(a2.getValues().contains("true"));

        final NodeAttributesElement a3 = (NodeAttributesElement) aspects.get(3);
        assertTrue(a3.getName().equals("name4"));
        assertTrue(a3.getPropertyOf().size() == 3);
        assertTrue(a3.getPropertyOf().contains("n0"));
        assertTrue(a3.getPropertyOf().contains("n1"));
        assertTrue(a3.getPropertyOf().contains("n2"));
        assertTrue(a3.getType() == ATTRIBUTE_TYPE.SHORT);
        assertTrue(a3.getValues().size() == 2);
        assertTrue(a3.getValues().contains("1"));
        assertTrue(a3.getValues().contains("2"));

    }
}