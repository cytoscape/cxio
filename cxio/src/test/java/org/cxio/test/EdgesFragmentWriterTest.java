package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.junit.Test;

public class EdgesFragmentWriterTest {

    @Test
    public void test() throws IOException {

        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final ByteArrayOutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w0 = CxWriter.createInstance(out0, false);
        w0.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());

        w0.startT();
        w0.writeAspectElements(l0);
        w0.end(true, "");

        assertEquals("[{\"status\":[{\"error\":\"\",\"success\":true}]}]", out0.toString());

        final EdgesElement e0 = new EdgesElement(0, 0, 0);
        final EdgesElement e1 = new EdgesElement(1, 1, 1);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(e0);
        l1.add(e1);

        final ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());

        w1.startT();
        w1.writeAspectElements(l1);
        w1.end(true, "");

        assertEquals("[{\"edges\":[{\"@id\":0,\"s\":0,\"t\":0},{\"@id\":1,\"s\":1,\"t\":1}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]", out1.toString());

    }

}
