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
import org.junit.Test;

public class NodeAttributesFragmentWriterTest {

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

        final List<String> po = new ArrayList<String>();
        po.add("a");
        po.add("b");

        final List<String> v = new ArrayList<String>();
        v.add("1");
        v.add("2");

        final NodeAttributesElement na0 = new NodeAttributesElement(po, "name", v, ATTRIBUTE_TYPE.FLOAT);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(na0);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);

        w1.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals("[{\"nodeAttributes\":[{\"po\":[\"a\",\"b\"],\"n\":\"name\",\"v\":[\"1\",\"2\"],\"t\":\"float\"}]}]",
                     out1.toString());

    }

}
