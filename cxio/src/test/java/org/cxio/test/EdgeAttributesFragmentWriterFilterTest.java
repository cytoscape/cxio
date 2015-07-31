package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.writers.EdgeAttributesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.filters.AspectKeyFilter;
import org.cxio.filters.AspectKeyFilterBasic;
import org.junit.Test;

public class EdgeAttributesFragmentWriterFilterTest {

    @Test
    public void test() throws IOException {
        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstance(out0, false);
        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(l0);
        w.end();

        assertEquals("[]", out0.toString());

//        final EdgeAttributesElement ea0 = new EdgeAttributesElement("00");
//        ea0.addEdge("000");
//        ea0.addEdge("001");
//        ea0.putValue("A", "a1");
//        ea0.putValue("A", "a2");
//        ea0.putValue("A", "a3");
//
//        ea0.putValue("B", "b1");
//        ea0.putValue("B", "b2");
//        ea0.putValue("B", "b3");
//
//        ea0.putValue("X", "false");
//        ea0.putType("X", "boolean");
//
//        ea0.putValue("Y", "true");
//        ea0.putType("Y", ATTRIBUTE_TYPE.BOOLEAN);
//        ea0.putValue("Z", true);
//
//        ea0.putValue("L", 1l);
//        ea0.putValue("D", 2.0);
//        ea0.putValue("F", 3.0f);
//        ea0.putValue("I", 4);
//        ea0.putValue("I", 5);
//        ea0.putValue("I", 6);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
       // l1.add(ea0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals(
                "[{\"edgeAttributes\":[{\"@id\":\"00\",\"edges\":[\"000\",\"001\"],\"types\":{\"D\":\"double\",\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\",\"X\":\"boolean\",\"Y\":\"boolean\",\"Z\":\"boolean\"},\"attributes\":{\"A\":[\"a1\",\"a2\",\"a3\"],\"B\":[\"b1\",\"b2\",\"b3\"],\"D\":[\"2.0\"],\"F\":[\"3.0\"],\"I\":[\"4\",\"5\",\"6\"],\"L\":[\"1\"],\"X\":[\"false\"],\"Y\":[\"true\"],\"Z\":[\"true\"]}}]}]",
                out1.toString());

        //
        final OutputStream out2 = new ByteArrayOutputStream();
        final CxWriter w2 = CxWriter.createInstance(out2, false);

        final EdgeAttributesFragmentWriter eafw2 = EdgeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter2 = new AspectKeyFilterBasic(EdgeAttributesElement.NAME);
        eafw2.addAspectKeyFilter(filter2);
        w2.addAspectFragmentWriter(eafw2);

        w2.start();
        w2.writeAspectElements(l1);
        w2.end();

        assertEquals(
                "[{\"edgeAttributes\":[{\"@id\":\"00\",\"edges\":[\"000\",\"001\"],\"types\":{\"D\":\"double\",\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\",\"X\":\"boolean\",\"Y\":\"boolean\",\"Z\":\"boolean\"},\"attributes\":{\"A\":[\"a1\",\"a2\",\"a3\"],\"B\":[\"b1\",\"b2\",\"b3\"],\"D\":[\"2.0\"],\"F\":[\"3.0\"],\"I\":[\"4\",\"5\",\"6\"],\"L\":[\"1\"],\"X\":[\"false\"],\"Y\":[\"true\"],\"Z\":[\"true\"]}}]}]",
                out2.toString());

        //
        final OutputStream out3 = new ByteArrayOutputStream();
        final CxWriter w3 = CxWriter.createInstance(out3, false);

        final EdgeAttributesFragmentWriter eafw3 = EdgeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter3 = new AspectKeyFilterBasic(EdgeAttributesElement.NAME);
        filter3.addExcludeAspectKey("AA");
        filter3.addExcludeAspectKey("ZZ");
        eafw3.addAspectKeyFilter(filter3);
        w3.addAspectFragmentWriter(eafw3);

        w3.start();
        w3.writeAspectElements(l1);
        w3.end();

        assertEquals(
                "[{\"edgeAttributes\":[{\"@id\":\"00\",\"edges\":[\"000\",\"001\"],\"types\":{\"D\":\"double\",\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\",\"X\":\"boolean\",\"Y\":\"boolean\",\"Z\":\"boolean\"},\"attributes\":{\"A\":[\"a1\",\"a2\",\"a3\"],\"B\":[\"b1\",\"b2\",\"b3\"],\"D\":[\"2.0\"],\"F\":[\"3.0\"],\"I\":[\"4\",\"5\",\"6\"],\"L\":[\"1\"],\"X\":[\"false\"],\"Y\":[\"true\"],\"Z\":[\"true\"]}}]}]",
                out3.toString());

        //
        final OutputStream out4 = new ByteArrayOutputStream();
        final CxWriter w4 = CxWriter.createInstance(out4, false);

        final EdgeAttributesFragmentWriter eafw4 = EdgeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter4 = new AspectKeyFilterBasic(EdgeAttributesElement.NAME);
        filter4.addIncludeAspectKey("A");
        filter4.addIncludeAspectKey("B");
        filter4.addIncludeAspectKey("X");
        filter4.addIncludeAspectKey("Y");
        filter4.addIncludeAspectKey("Z");
        filter4.addIncludeAspectKey("L");
        filter4.addIncludeAspectKey("D");
        filter4.addIncludeAspectKey("F");
        filter4.addIncludeAspectKey("I");

        eafw4.addAspectKeyFilter(filter4);
        w4.addAspectFragmentWriter(eafw4);

        w4.start();
        w4.writeAspectElements(l1);
        w4.end();

        assertEquals(
                "[{\"edgeAttributes\":[{\"@id\":\"00\",\"edges\":[\"000\",\"001\"],\"types\":{\"D\":\"double\",\"F\":\"float\",\"I\":\"integer\",\"L\":\"long\",\"X\":\"boolean\",\"Y\":\"boolean\",\"Z\":\"boolean\"},\"attributes\":{\"A\":[\"a1\",\"a2\",\"a3\"],\"B\":[\"b1\",\"b2\",\"b3\"],\"D\":[\"2.0\"],\"F\":[\"3.0\"],\"I\":[\"4\",\"5\",\"6\"],\"L\":[\"1\"],\"X\":[\"false\"],\"Y\":[\"true\"],\"Z\":[\"true\"]}}]}]",
                out4.toString());

        //
        final OutputStream out5 = new ByteArrayOutputStream();
        final CxWriter w5 = CxWriter.createInstance(out5, false);

        final EdgeAttributesFragmentWriter eafw5 = EdgeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter5 = new AspectKeyFilterBasic(EdgeAttributesElement.NAME);
        filter5.addIncludeAspectKey("D");

        eafw5.addAspectKeyFilter(filter5);
        w5.addAspectFragmentWriter(eafw5);

        w5.start();
        w5.writeAspectElements(l1);
        w5.end();

        assertEquals(
                "[{\"edgeAttributes\":[{\"@id\":\"00\",\"edges\":[\"000\",\"001\"],\"types\":{\"D\":\"double\"},\"attributes\":{\"D\":[\"2.0\"]}}]}]",
                out5.toString());

        //
        final OutputStream out6 = new ByteArrayOutputStream();
        final CxWriter w6 = CxWriter.createInstance(out6, false);

        final EdgeAttributesFragmentWriter eafw6 = EdgeAttributesFragmentWriter.createInstance();
        final AspectKeyFilter filter6 = new AspectKeyFilterBasic(EdgeAttributesElement.NAME);

        filter6.addExcludeAspectKey("A");
        filter6.addExcludeAspectKey("B");
        filter6.addExcludeAspectKey("X");
        filter6.addExcludeAspectKey("Y");
        filter6.addExcludeAspectKey("Z");
        filter6.addExcludeAspectKey("L");
        filter6.addExcludeAspectKey("F");
        filter6.addExcludeAspectKey("I");
        eafw6.addAspectKeyFilter(filter6);
        w6.addAspectFragmentWriter(eafw6);

        w6.start();
        w6.writeAspectElements(l1);
        w6.end();

        assertEquals(
                "[{\"edgeAttributes\":[{\"@id\":\"00\",\"edges\":[\"000\",\"001\"],\"types\":{\"D\":\"double\"},\"attributes\":{\"D\":[\"2.0\"]}}]}]",
                out6.toString());

    }

}
