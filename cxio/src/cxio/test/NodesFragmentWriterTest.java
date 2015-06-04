package cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cxio.AspectElement;
import cxio.JsonWriter;
import cxio.NodeElement;
import cxio.NodesFragmentWriter;

public class NodesFragmentWriterTest {

    @Test
    public void test() throws IOException {

        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final JsonWriter t0 = JsonWriter.createInstance(out0);

        final NodesFragmentWriter w0 = NodesFragmentWriter.createInstance(t0);

        t0.start();
        w0.write(l0);
        t0.end();

        assertEquals("[{\"nodes\":[]}]", out0.toString());

        final NodeElement n0 = new NodeElement("0");
        final NodeElement n1 = new NodeElement("1");
        final NodeElement n2 = new NodeElement("2");
        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(n0);
        l1.add(n1);
        l1.add(n2);

        final OutputStream out1 = new ByteArrayOutputStream();
        final JsonWriter t1 = JsonWriter.createInstance(out1);

        final NodesFragmentWriter w1 = NodesFragmentWriter.createInstance(t1);

        t1.start();
        w1.write(l1);
        t1.end();

        assertEquals("[{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"}]}]", out1.toString());

        final NodeElement n3 = new NodeElement("3");
        final NodeElement n4 = new NodeElement("4");
        final NodeElement n5 = new NodeElement("5");
        final List<AspectElement> l2 = new ArrayList<AspectElement>();
        l2.add(n3);
        l2.add(n4);
        final List<AspectElement> l3 = new ArrayList<AspectElement>();
        l3.add(n5);

        final OutputStream out2 = new ByteArrayOutputStream();
        final JsonWriter t2 = JsonWriter.createInstance(out2);

        final NodesFragmentWriter w2 = NodesFragmentWriter.createInstance(t1);

        t2.start();
        w2.write(l2);
        w2.write(l3);
        t2.end();

        assertEquals("[{\"nodes\":[{\"@id\":\"3\"},{\"@id\":\"4\"}]},{\"nodes\":[{\"@id\":\"5\"}]}]", out2.toString());

    }

}
