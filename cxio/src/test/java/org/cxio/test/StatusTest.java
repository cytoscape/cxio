package org.cxio.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.junit.Test;

public class StatusTest {

    @Test
    public void test1() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, true);
        w.start();
        w.end();

        assertTrue(out.toString().equals("[]"));
    }

    @Test
    public void test2() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, true);
        w.start();
        w.end(true);

        assertTrue(out.toString().equals("[{\"status\":[{\"error\":\"\",\"success\":\"true\"}]}]"));
    }

    @Test
    public void test3() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, true);
        w.start();
        w.setStatus(false, "no memory");
        w.end(true);

        assertTrue(out.toString().equals("[{\"status\":[{\"error\":\"no memory\",\"success\":\"false\"}]}]"));
    }

    @Test
    public void test4() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, true);
        w.start();
        w.setStatus(false, "no memory");
        w.end(true);

        assertTrue(out.toString().equals("[{\"status\":[{\"error\":\"no memory\",\"success\":\"false\"}]}]"));

        final CxReader p = CxReader.createInstanceWithAllAvailableReaders(out.toString(), true);

        while (p.hasNext()) {
            p.getNext();
        }

        assertTrue(p.getStatus().getError().equals("no memory"));
        assertTrue(p.getStatus().isSuccess() == false);
    }

    @Test
    public void test5() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, false, true);
        w.start();
        w.setStatus(false, "no memory");
        w.end(true);

        assertTrue(out.toString().equals("[{\"status\":[{\"error\":\"no memory\",\"success\":\"false\"}]}]"));

        final CxElementReader p = CxElementReader.createInstanceWithAllAvailableReaders(out.toString(), true);

        while (p.hasNext()) {
            p.getNext();
        }

        assertTrue(p.getStatus().getError().equals("no memory"));
        assertTrue(p.getStatus().isSuccess() == false);
    }

}
