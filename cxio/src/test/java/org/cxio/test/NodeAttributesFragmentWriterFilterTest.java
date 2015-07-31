package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.writers.NodeAttributesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.filters.AspectKeyFilterBasic;
import org.junit.Test;

public class NodeAttributesFragmentWriterFilterTest {

    @Test
    public void test() throws IOException {

        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w0 = CxWriter.createInstance(out0, false);

        w0.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());

        w0.start();
        w0.writeAspectElements(l0);
        w0.end();

        assertEquals("[]", out0.toString());

//        final NodeAttributesElement na0 = new NodeAttributesElement("00");
//        na0.addNode("000");
//        na0.addNode("001");
//        na0.put("X", "x1", ATTRIBUTE_TYPE.STRING);
//        na0.put("X", "x2", ATTRIBUTE_TYPE.STRING);
//        na0.put("X", "x3", ATTRIBUTE_TYPE.STRING);
//        na0.put("Y", "y1", ATTRIBUTE_TYPE.STRING);
//        na0.put("Y", "y2", ATTRIBUTE_TYPE.STRING);
//        na0.put("Y", "y3", ATTRIBUTE_TYPE.STRING);
//        na0.put("I", "1", ATTRIBUTE_TYPE.INTEGER);
//        na0.put("D", "-1.111", ATTRIBUTE_TYPE.DOUBLE);
//        na0.put("F", "2.01", ATTRIBUTE_TYPE.FLOAT);
//        na0.put("L", "1111", ATTRIBUTE_TYPE.LONG);
//        na0.put("B", "true", ATTRIBUTE_TYPE.BOOLEAN);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
      //  l1.add(na0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);

        w1.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals(
                "[{\"nodeAttributes\":[{\"@id\":\"00\",\"nodes\":[\"000\",\"001\"],\"types\":{\"B\":\"boolean\",\"D\":\"double\",\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\"},\"attributes\":{\"B\":[\"true\"],\"D\":[\"-1.111\"],\"F\":[\"2.01\"],\"I\":[\"1\"],\"L\":[\"1111\"],\"X\":[\"x1\",\"x2\",\"x3\"],\"Y\":[\"y1\",\"y2\",\"y3\"]}}]}]",
                out1.toString());

        //
        final OutputStream out2 = new ByteArrayOutputStream();
        final CxWriter w2 = CxWriter.createInstance(out2, false);

        final NodeAttributesFragmentWriter nafw2 = NodeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter2 = new AspectKeyFilterBasic(nafw2.getAspectName());
        filter2.addExcludeAspectKey("se");
        nafw2.addAspectKeyFilter(filter2);
        w2.addAspectFragmentWriter(nafw2);

        w2.start();
        w2.writeAspectElements(l1);
        w2.end();

        assertEquals(
                "[{\"nodeAttributes\":[{\"@id\":\"00\",\"nodes\":[\"000\",\"001\"],\"types\":{\"B\":\"boolean\",\"D\":\"double\",\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\"},\"attributes\":{\"B\":[\"true\"],\"D\":[\"-1.111\"],\"F\":[\"2.01\"],\"I\":[\"1\"],\"L\":[\"1111\"],\"X\":[\"x1\",\"x2\",\"x3\"],\"Y\":[\"y1\",\"y2\",\"y3\"]}}]}]",
                out2.toString());

        //
        final OutputStream out3 = new ByteArrayOutputStream();
        final CxWriter w3 = CxWriter.createInstance(out3, false);

        final NodeAttributesFragmentWriter nafw3 = NodeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter3 = new AspectKeyFilterBasic(nafw2.getAspectName());
        filter3.addExcludeAspectKey("se");
        filter3.addExcludeAspectKey("B");
        filter3.addExcludeAspectKey("D");
        filter3.addExcludeAspectKey("X");
        nafw3.addAspectKeyFilter(filter3);
        w3.addAspectFragmentWriter(nafw3);

        w3.start();
        w3.writeAspectElements(l1);
        w3.end();

        assertEquals(
                "[{\"nodeAttributes\":[{\"@id\":\"00\",\"nodes\":[\"000\",\"001\"],\"types\":{\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\"},\"attributes\":{\"F\":[\"2.01\"],\"I\":[\"1\"],\"L\":[\"1111\"],\"Y\":[\"y1\",\"y2\",\"y3\"]}}]}]",
                out3.toString());

        final OutputStream out4 = new ByteArrayOutputStream();
        final CxWriter w4 = CxWriter.createInstance(out4, false);

        final NodeAttributesFragmentWriter nafw4 = NodeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter4 = new AspectKeyFilterBasic(nafw2.getAspectName());
        filter4.addIncludeAspectKey("se");
        filter4.addIncludeAspectKey("B");
        filter4.addIncludeAspectKey("D");
        filter4.addIncludeAspectKey("X");
        nafw4.addAspectKeyFilter(filter4);
        w4.addAspectFragmentWriter(nafw4);

        w4.start();
        w4.writeAspectElements(l1);
        w4.end();
        assertEquals(
                "[{\"nodeAttributes\":[{\"@id\":\"00\",\"nodes\":[\"000\",\"001\"],\"types\":{\"B\":\"boolean\",\"D\":\"double\"},\"attributes\":{\"B\":[\"true\"],\"D\":[\"-1.111\"],\"X\":[\"x1\",\"x2\",\"x3\"]}}]}]",
                out4.toString());

    }

}
