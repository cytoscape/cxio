package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aux.NumberVerification;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.cxio.util.JsonWriter;
import org.junit.Test;

public class NumberVerficationTest {

    @Test
    public void test0() throws IOException {
        final Long l = 0L;
        final NumberVerification n1 = new NumberVerification(l);
        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter w = JsonWriter.createInstance(out, false);
        n1.toJson(w);
        assertTrue(out.toString().equals("{\"numberVerification\":[{\"longNumber\":0}]}"));
    }

    @Test
    public void test1a() throws IOException {
        final Long l = 1000000000000L;
        final NumberVerification n1 = new NumberVerification(l);
        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter w = JsonWriter.createInstance(out, false);
        n1.toJson(w);
        assertTrue(out.toString().equals("{\"numberVerification\":[{\"longNumber\":1000000000000}]}"));
    }

    @Test
    public void test1b() throws IOException {
        final Long l = Long.MAX_VALUE;
        final NumberVerification n1 = new NumberVerification(l);
        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter w = JsonWriter.createInstance(out, false);
        n1.toJson(w);
        assertTrue(out.toString().equals("{\"numberVerification\":[{\"longNumber\":" + l + "}]}"));
    }

    @Test
    public void test2() throws IOException {
        final Long l = 0L;
        final NumberVerification n1 = new NumberVerification(l);
        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter w = JsonWriter.createInstance(out, false);
        n1.toJson(w);
        final String str = out.toString();
        final NumberVerification n2 = NumberVerification.createInstanceFromJson(str);
        assertTrue(n2.getLongNumber() == 0L);
    }

    @Test
    public void test3() throws IOException {
        final Long l = 1000000000000L;
        final NumberVerification n1 = new NumberVerification(l);
        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter w = JsonWriter.createInstance(out, false);
        n1.toJson(w);
        final String str = out.toString();
        final NumberVerification n2 = NumberVerification.createInstanceFromJson(str);

        assertTrue(n2.getLongNumber() == 1000000000000L);
    }

    @Test
    public void test4() throws IOException {
        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final ByteArrayOutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w0 = CxWriter.createInstance(out0, false);
        w0.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w0.start();
        w0.writeAspectElements(l0);
        w0.end(true, "");
        assertEquals("[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]", out0.toString());
    }

    @Test
    public void test5() throws IOException {

        final String str = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

        final CxReader p = CxReader.createInstance(str, CxioUtil.getAllAvailableAspectFragmentReaders());
        CxReader.parseAsMap(p);

    }

    @Test(expected = IOException.class)
    public void test6() throws IOException {

        final String str = "[{\"numberVerification\":[{\"longNumber\":9223372036854775806}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]";

        final CxReader p = CxReader.createInstance(str, CxioUtil.getAllAvailableAspectFragmentReaders());
        CxReader.parseAsMap(p);

    }

}
