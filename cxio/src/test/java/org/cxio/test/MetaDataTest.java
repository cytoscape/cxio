package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.metadata.MetaDataCollection;
import org.cxio.metadata.MetaDataElement;
import org.junit.Test;

public class MetaDataTest {

    @Test
    public void test1() {
        final MetaDataCollection md = new MetaDataCollection();

        final MetaDataElement node_meta = new MetaDataElement();

        node_meta.setName(NodesElement.ASPECT_NAME);
        node_meta.setVersion("1.0");
        node_meta.setIdCounter(200L);
        node_meta.setLastUpdate(1034334343L);
        node_meta.setElementCount(32L);
        node_meta.setConsistencyGroup(1L);

        md.add(node_meta);

        final MetaDataElement citation_meta = new MetaDataElement();

        citation_meta.setName("Citation");
        citation_meta.setVersion("1.0");
        citation_meta.setLastUpdate(1034334343L);
        citation_meta.setConsistencyGroup(1L);

        // final Map<String, String> prop = new TreeMap<String, String>();
        // prop.put("name", "curator");
        // prop.put("value", "Ideker Lab");
        citation_meta.addProperty("name", "curator");
        citation_meta.addProperty("value", "Ideker Lab");

        citation_meta.put("key1", "value1");
        citation_meta.put("key2", true);

        md.add(citation_meta);

        assertEquals(2, md.size());

        final MetaDataElement mde0 = md.getMetaDataElement(NodesElement.ASPECT_NAME);
        final MetaDataElement mde1 = md.getMetaDataElement("Citation");

        assertEquals(NodesElement.ASPECT_NAME, mde0.getName());
        assertEquals("1.0", mde0.getVersion());
        assertTrue(mde0.getIdCounter() == 200L);
        assertTrue(mde0.getLastUpdate() == 1034334343L);
        assertTrue(mde0.getElementCount() == 32L);
        assertTrue(mde0.getConsistencyGroup() == 1L);

        assertTrue(mde1.getProperties().size() == 2);
        assertTrue(mde1.getProperties().get("name").equals("curator"));
        assertTrue(mde1.getProperties().get("value").equals("Ideker Lab"));

        assertTrue(mde1.get("key1").equals("value1"));
        assertTrue(mde1.get("key2").equals(true));

    }

    @Test
    public void test2() throws IOException {
        final MetaDataCollection md = new MetaDataCollection();

        final MetaDataElement node_meta = new MetaDataElement();

        node_meta.setName(NodesElement.ASPECT_NAME);
        node_meta.setVersion("1.0");
        node_meta.setIdCounter(200L);
        node_meta.setLastUpdate(1034334343L);
        node_meta.setElementCount(32L);
        node_meta.setConsistencyGroup(1L);

        md.add(node_meta);

        final MetaDataElement citation_meta = new MetaDataElement();

        citation_meta.setName("Citation");
        citation_meta.setVersion("1.0");
        citation_meta.setLastUpdate(1034334343L);
        citation_meta.setConsistencyGroup(1L);

        citation_meta.addProperty("name", "curator");
        citation_meta.addProperty("value", "Ideker Lab");

        citation_meta.put("key1", "value1");
        citation_meta.put("key2", true);

        md.add(citation_meta);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);
        w.addPreMetaData(md);
        w.start();
        w.end(true, "");

        final String cx_json_str = out.toString();

        final CxElementReader p = CxElementReader.createInstanceWithAllAvailableReaders(cx_json_str, true);

        assertTrue(p.getPreMetaData().size() == 2);

        final MetaDataCollection my_md = p.getPreMetaData();

        assertTrue(my_md.size() == 2);

        final MetaDataElement mde0 = md.getMetaDataElement(NodesElement.ASPECT_NAME);
        final MetaDataElement mde1 = md.getMetaDataElement("Citation");

        assertEquals(NodesElement.ASPECT_NAME, mde0.getName());
        assertEquals("1.0", mde0.getVersion());
        assertTrue(mde0.getIdCounter() == 200L);
        assertTrue(mde0.getLastUpdate() == 1034334343L);
        assertTrue(mde0.getElementCount() == 32L);
        assertTrue(mde0.getConsistencyGroup() == 1L);

        assertTrue(mde1.getProperties().size() == 2);
        assertTrue(mde1.getProperties().get("name").equals("curator"));
        assertTrue(mde1.getProperties().get("value").equals("Ideker Lab"));

        assertTrue(mde1.get("key1").equals("value1"));
        assertTrue(mde1.get("key2").equals(true));

        assertTrue(md.getMetaDataElement("Citation").getName().equals("Citation"));
        assertTrue(md.getMetaDataElement(NodesElement.ASPECT_NAME).getName().equals(NodesElement.ASPECT_NAME));
        assertTrue(md.getMetaDataElement("xyz") == null);

        final Iterator<MetaDataElement> it = md.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }

    @Test
    public void test3() throws IOException {
        final MetaDataCollection md = new MetaDataCollection();

        md.setIdCounter("one", 1L);
        md.setVersion("one", "1");
        md.setVersion("two", "2");
        md.setConsistencyGroup("two", 3L);
        md.setElementCount("two", 4L);
        md.setLastUpdate("two", 5L);

        assertTrue(md.getIdCounter("one") == 1L);
        assertTrue(md.getVersion("one").equals("1"));

        assertTrue(md.getVersion("two").equals("2"));
        assertTrue(md.getConsistencyGroup("two") == 3L);
        assertTrue(md.getElementCount("two") == 4L);
        assertTrue(md.getLastUpdate("two") == 5L);

    }

}
