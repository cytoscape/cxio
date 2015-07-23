package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.CytoscapeVisualProperties;
import org.cxio.aspects.datamodels.CytoscapeVisualStyleElement;
import org.cxio.aspects.writers.CytoscapeVisualStyleFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.junit.Test;

public class CytoscapeVisualStyleFragmentWriterTest {

    @Test
    public void test() throws IOException {
        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstance(out0, false);
        w.addAspectFragmentWriter(CytoscapeVisualStyleFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(l0);
        w.end();

        assertEquals("[]", out0.toString());

        final CytoscapeVisualStyleElement c1 = new CytoscapeVisualStyleElement("Sample1");
        final CytoscapeVisualProperties cvp0 = new CytoscapeVisualProperties("node:all", "nodes");
        cvp0.put("text-opacity", "1.0");
        cvp0.put("width", "40.0");
        cvp0.put("background-color", "rgb(204,204,255)");
        final CytoscapeVisualProperties cvp1 = new CytoscapeVisualProperties("node:selected", "nodes");
        cvp1.put("background-color", "rgb(255,255,0)");

        c1.addProperties(cvp0);
        c1.addProperties(cvp1);
        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(c1);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(CytoscapeVisualStyleFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        System.out.println(out1.toString());

        assertEquals(
                "[{\"visualStyle\":[{\"title\":\"Sample1\",\"styles\":[{\"applies_to\":\"nodes\",\"selector\":\"node:all\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"}},{\"applies_to\":\"nodes\",\"selector\":\"node:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}}]}]}]",
                out1.toString());

    }

}
