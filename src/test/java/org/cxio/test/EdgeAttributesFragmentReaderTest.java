package org.cxio.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class EdgeAttributesFragmentReaderTest {

    @Test
    public void test0() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    }

    @Test
    public void test00() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},[],{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    }

    @Test
    public void test000() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxElementReader p = CxElementReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    }

    @Test
    public void test0000() throws IOException {
        final String t0 = "[{\"numberVerification\":[{\"longNumber\":9223372036854775807}]},[],{\"status\":[{\"error\":\"\",\"success\":true}]}]   ";

        final CxElementReader p = CxElementReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxElementReader.parseAsMap(p);
        assertTrue(r0.isEmpty());
    }
}
