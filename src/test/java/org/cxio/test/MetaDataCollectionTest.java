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

    private static final String META_DATA_1 = "{\"metaData\":[{\"consistencyGroup\":0,\"elementCount\":39393742,\"idCounter\":4,\"lastUpdate\":5,\"name\":\"name_0\",\"properties\":[{\"key0\":\"value0\"}],\"version\":\"v0\"},{\"elementCount\":24948,\"name\":\"name_1\",\"properties\":[],\"version\":\"v1\"},{\"elementCount\":2034994,\"name\":\"name_2\",\"properties\":[],\"version\":\"v2\"}]}";

    @Test
    public void testRemove() {

        final MetaDataCollection md = new MetaDataCollection();

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);

        final MetaDataElement a = md.remove("name_0");

        assertEquals(md.isEmpty(), true);
        assertEquals(md.size(), 0);
        assertEquals(a, null);

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
        md.setConsistencyGroup("name_0", 0L);
        md.setIdCounter("name_0", 4L);
        md.setLastUpdate("name_0", 5L);
        md.setElementCount("name_0", 39393742L);
        md.setProperty("name_0", "key0", "value0");

        md.setVersion("name_1", "v1");
        md.setElementCount("name_1", 24948L);

        md.setVersion("name_2", "v2");
        md.setElementCount("name_2", 2034994L);

        final OutputStream out = new ByteArrayOutputStream();

        final JsonWriter jw = JsonWriter.createInstance(out, false);

        md.toJson(jw);
        assertTrue(out.toString().equals(META_DATA_1));

    }

    @Test
    public void testFromJson1() throws IOException {
        final MetaDataCollection md = MetaDataCollection.createInstanceFromJson(META_DATA_1);

        assertTrue(md.getElementCount("name_0") == 39393742L);
        assertTrue(md.getConsistencyGroup("name_0") == 0L);
        assertTrue(md.getIdCounter("name_0") == 4L);
        assertTrue(md.getLastUpdate("name_0") == 5L);
        assertTrue(md.getMetaDataElement("name_0").getProperties().get("key0").equals("value0"));
        assertTrue(md.getVersion("name_0").equals("v0"));
        assertTrue(md.getVersion("name_1").equals("v1"));
        assertTrue(md.getVersion("name_2").equals("v2"));

    }

    @Test
    public void testFromJson2() throws IOException {
        final MetaDataCollection md = MetaDataCollection.createInstanceFromJson(META_DATA_1);

        final OutputStream out = new ByteArrayOutputStream();
        final JsonWriter jw = JsonWriter.createInstance(out, false);

        md.toJson(jw);
        assertTrue(out.toString().equals(META_DATA_1));
    }

    @Test
    public void testToJsonEmpty() throws IOException {
        final MetaDataCollection md = new MetaDataCollection();

        final OutputStream out = new ByteArrayOutputStream();

        final JsonWriter jw = JsonWriter.createInstance(out, false);

        md.toJson(jw);

        assertTrue(out.toString().equals("{\"metaData\":[]}"));

    }

    @Test
    public void testEmpty() throws IOException {

        final MetaDataCollection empty = new MetaDataCollection();
        empty.getConsistencyGroup("x");
        empty.getElementCount("x");
        empty.getIdCounter("x");
        empty.getLastUpdate("x");
        empty.getMetaData();
        empty.getMetaDataElement("x");
        empty.getVersion("x");

        final MetaDataElement empty2 = new MetaDataElement();
        empty2.getConsistencyGroup();
        empty2.getElementCount();
        empty2.getIdCounter();
        empty2.getLastUpdate();
        empty2.getData();
        empty2.getProperties();
        empty2.getName();
        empty2.getVersion();

        empty.add(empty2);
        assertTrue(empty.getMetaDataElement("x") == null);
    }

}
