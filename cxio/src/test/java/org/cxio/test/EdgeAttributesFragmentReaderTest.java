package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;
import org.junit.Test;

public class EdgeAttributesFragmentReaderTest {

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
                + "{\"edgeAttributesOld\":[{\"@id\":\"_ea0\",\"edges\":[\"_e38\", \"_e39\"], \"attributes\":{\"interaction\":[\"479019\", \"one more\"],\"name\":[\"768303 (479019) 791595\"],\"PSIMI_25_detection_method\":[\"genetic interference\"]}}]},"
                + "{\"edgeAttributesOld\":[{\"@id\":\"_ea1\",\"edges\":[\"_e22\", \"_e33\", \"_e44\"]}]},"
                + "{\"edgeAttributesOld\":[{\"@id\":\"_ea2\",\"edges\":[\"_e38\"], \"attributes\":{\"deleted\":[\"true\"]}, \"types\":{\"deleted\":\"boolean\"}}]},"
                + "{\"edgeAttributes\":[{\"po\":\"e0\",\"n\":\"name1\",\"v\":\"value\"}]}," + "{\"edgeAttributes\":[{\"po\":\"e1\",\"n\":\"name2\",\"v\":\"12\",\"t\":\"integer\"}]},"
                + "{\"edgeAttributes\":[{\"po\":[\"e0\",\"e1\"],\"n\":\"name3\",\"v\":\"true\",\"t\":\"boolean\"}]},"
                + "{\"edgeAttributes\":[{\"po\":[\"e0\",\"e1\",\"e2\"],\"n\":\"name4\",\"v\":[\"1\",\"2\"],\"t\":\"short\"}]}" + "]";

        final CxReader p = CxReader.createInstance(t0, Util.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.NAME + " aspect", r0.containsKey(EdgeAttributesElement.NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.NAME + " aspect", r0.get(EdgeAttributesElement.NAME).isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.NAME + " aspects", r0.get(EdgeAttributesElement.NAME).size() == 4);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.NAME);

        final EdgeAttributesElement ea0 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea0.getName().equals("name1"));
        assertTrue(ea0.getPropertyOf().size() == 1);
        assertTrue(ea0.getPropertyOf().contains("e0"));
        assertTrue(ea0.getType() == ATTRIBUTE_TYPE.STRING);
        assertTrue(ea0.getValues().size() == 1);
        assertTrue(ea0.getValues().contains("value"));

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea1.getName().equals("name2"));
        assertTrue(ea1.getPropertyOf().size() == 1);
        assertTrue(ea1.getPropertyOf().contains("e1"));
        assertTrue(ea1.getType() == ATTRIBUTE_TYPE.INTEGER);
        assertTrue(ea1.getValues().size() == 1);
        assertTrue(ea1.getValues().contains("12"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea2.getName().equals("name3"));
        assertTrue(ea2.getPropertyOf().size() == 2);
        assertTrue(ea2.getPropertyOf().contains("e0"));
        assertTrue(ea2.getPropertyOf().contains("e1"));
        assertTrue(ea2.getType() == ATTRIBUTE_TYPE.BOOLEAN);
        assertTrue(ea2.getValues().size() == 1);
        assertTrue(ea2.getValues().contains("true"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(3);
        assertTrue(ea3.getName().equals("name4"));
        assertTrue(ea3.getPropertyOf().size() == 3);
        assertTrue(ea3.getPropertyOf().contains("e0"));
        assertTrue(ea3.getPropertyOf().contains("e1"));
        assertTrue(ea3.getPropertyOf().contains("e2"));
        assertTrue(ea3.getType() == ATTRIBUTE_TYPE.SHORT);
        assertTrue(ea3.getValues().size() == 2);
        assertTrue(ea3.getValues().contains("1"));
        assertTrue(ea3.getValues().contains("2"));

    }

}
