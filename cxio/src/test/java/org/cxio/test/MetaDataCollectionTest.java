package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.cxio.metadata.MetaDataCollection;
import org.cxio.metadata.MetaDataElement;
import org.cxio.util.JsonWriter;
import org.junit.Test;

public class MetaDataCollectionTest {

    @Test
    public void testRemove() {

        final MetaDataCollection md = new MetaDataCollection();

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);

        final MetaDataElement a = md.remove("name_0");

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
        assertEquals(a, null);

        //

        md.setVersion("name_0", "v0");
        assertEquals(md.isEmpty(), false);
        assertEquals(md.size(), 1);

        final MetaDataElement b = md.remove("name_0");

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
        assertNotEquals(b, null);
        assertTrue(b.getVersion().equals("v0"));
        assertTrue(b.getName().equals("name_0"));

        final MetaDataElement c = md.remove("name_0");

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
        assertEquals(c, null);

        //

        md.setVersion("name_0", "v0");
        md.setVersion("name_1", "v1");
        md.setVersion("name_2", "v2");
        assertEquals(md.isEmpty(), false);
        assertEquals(md.size(), 3);

        final MetaDataElement d = md.remove("name_0");

        assertNotEquals(md.isEmpty(), true);
        assertEquals(md.size(), 2);
        assertNotEquals(d, null);
        assertTrue(d.getVersion().equals("v0"));
        assertTrue(d.getName().equals("name_0"));

        final MetaDataElement e = md.remove("name_1");

        assertNotEquals(md.isEmpty(), true);
        assertEquals(md.size(), 1);
        assertNotEquals(e, null);
        assertTrue(e.getVersion().equals("v1"));
        assertTrue(e.getName().equals("name_1"));

        final MetaDataElement f = md.remove("name_2");

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
        assertNotEquals(f, null);
        assertTrue(f.getVersion().equals("v2"));
        assertTrue(f.getName().equals("name_2"));

        final MetaDataElement g = md.remove("name_2");

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
        assertEquals(g, null);

    }

    @Test
    public void testClear() {
        final MetaDataCollection md = new MetaDataCollection();
        md.setVersion("name_0", "v0");
        md.setVersion("name_1", "v1");
        md.setVersion("name_2", "v2");
        assertEquals(md.isEmpty(), false);
        assertEquals(md.size(), 3);

        md.clear();

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
    }

    @Test
    public void testOverwrite() {
        final MetaDataCollection md = new MetaDataCollection();
        md.setVersion("name_0", "v0");
        md.setVersion("name_0", "v1");

        assertEquals(md.size(), 1);
        md.getVersion("name_0").equals("v1");
    }

    @Test
    public void testIterator() {
        final MetaDataCollection md = new MetaDataCollection();
        md.setVersion("name_0", "vname_0");
        md.setVersion("name_1", "vname_1");
        md.setVersion("name_2", "vname_2");

        final Iterator<MetaDataElement> it = md.iterator();

        while (it.hasNext()) {
            final MetaDataElement x = it.next();
            assertTrue(("v" + x.getName()).equals(x.getVersion()));
        }
    }

    @Test
    public void testToArray() {
        final MetaDataCollection md = new MetaDataCollection();
        md.setVersion("name_0", "v");
        md.setVersion("name_1", "v");
        md.setVersion("name_2", "v");

        final Object[] ary = md.toArray();
        assertTrue(ary.length == 3);

        assertTrue(((MetaDataElement) ary[0]).getVersion().equals("v"));
        assertTrue(((MetaDataElement) ary[1]).getVersion().equals("v"));
        assertTrue(((MetaDataElement) ary[2]).getVersion().equals("v"));

    }

    @Test
    public void testToJson() throws IOException {
        final MetaDataCollection md = new MetaDataCollection();

        md.setVersion("name_0", "v0");
        md.setVersion("name_1", "v1");
        md.setVersion("name_2", "v2");
        md.setElementCount("name_0", 2L);

        final OutputStream out = new ByteArrayOutputStream();

        final JsonWriter jw = JsonWriter.createInstance(out, false);

        md.toJson(jw);

        assertTrue(out.toString()
                   .equals("{\"metaData\":[{\"elementCount\":\"2\",\"name\":\"name_0\",\"version\":\"v0\"},{\"name\":\"name_1\",\"version\":\"v1\"},{\"name\":\"name_2\",\"version\":\"v2\"}]}"));

    }

    @Test
    public void testToJsonEmpty() throws IOException {
        final MetaDataCollection md = new MetaDataCollection();

        final OutputStream out = new ByteArrayOutputStream();

        final JsonWriter jw = JsonWriter.createInstance(out, false);

        md.toJson(jw);

        System.out.println(out);

        assertTrue(out.toString().equals("{\"metaData\":[]}"));

    }

}
