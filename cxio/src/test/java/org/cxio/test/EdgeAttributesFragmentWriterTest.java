package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.writers.EdgeAttributesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.junit.Test;

public class EdgeAttributesFragmentWriterTest {

    @Test
    public void test1() throws IOException {

        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstance(out0, false);
        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(l0);
        w.end();

        assertEquals("[]", out0.toString());

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("property_of", "name", "1.1", ATTRIBUTE_DATA_TYPE.FLOAT);
        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(ea0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals("[{\"edgeAttributes\":[{\"po\":\"property_of\",\"n\":\"name\",\"v\":\"1.1\",\"d\":\"float\"}]}]", out1.toString());
    }

    @Test
    public void test2() throws IOException {

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("property_of", "name", 1.1f);
        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(ea0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals("[{\"edgeAttributes\":[{\"po\":\"property_of\",\"n\":\"name\",\"v\":\"1.1\",\"d\":\"float\"}]}]", out1.toString());
    }

    @Test
    public void test3() throws IOException {

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("subnetwork1", "property_of1", "name1", 1.1f);
        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(ea0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals("[{\"edgeAttributes\":[{\"s\":\"subnetwork1\",\"po\":\"property_of1\",\"n\":\"name1\",\"v\":\"1.1\",\"d\":\"float\"}]}]", out1.toString());
    }

}
