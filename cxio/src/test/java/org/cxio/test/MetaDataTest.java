package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.metadata.MetaData;
import org.cxio.metadata.MetaDataElement;
import org.junit.Test;

public class MetaDataTest {

    @Test
    public void test1() {
        final MetaData md = new MetaData();

        final MetaDataElement node_meta = new MetaDataElement();

        node_meta.setName(NodesElement.NAME);
        node_meta.setVersion("1.0");
        node_meta.setIdCounter(200L);
        node_meta.setLastUpdate(1034334343L);
        node_meta.setElementCount(32L);
        node_meta.setConsistencyGroup(1L);

        md.addMetaDataElement(node_meta);

        final MetaDataElement citation_meta = new MetaDataElement();

        citation_meta.setName("Citation");
        citation_meta.setVersion("1.0");
        citation_meta.setLastUpdate(1034334343L);
        citation_meta.setConsistencyGroup(1L);

        final Map<String, String> prop = new TreeMap<String, String>();
        prop.put("name", "curator");
        prop.put("value", "Ideker Lab");
        citation_meta.addProperty(prop);

        citation_meta.put("key1", "value1");
        citation_meta.put("key2", true);

        md.addMetaDataElement(citation_meta);

        assertEquals(2, md.size());

        final MetaDataElement mde0 = md.getMetaDataElement(0);
        final MetaDataElement mde1 = md.getMetaDataElement(1);

        assertEquals(NodesElement.NAME, mde0.getName());
        assertEquals("1.0", mde0.getVersion());
        assertTrue(mde0.getIdCounter() == 200L);
        assertTrue(mde0.getLastUpdate() == 1034334343L);
        assertTrue(mde0.getElementCount() == 32L);
        assertTrue(mde0.getConsistencyGroup() == 1L);

        assertTrue(mde1.getProperties().size() == 1);
        assertTrue(mde1.getProperties().get(0).get("name").equals("curator"));
        assertTrue(mde1.getProperties().get(0).get("value").equals("Ideker Lab"));

        assertTrue(mde1.get("key1").equals("value1"));
        assertTrue(mde1.get("key2").equals(true));

    }

    @Test
    public void test2() throws IOException {
        final MetaData md = new MetaData();

        final MetaDataElement node_meta = new MetaDataElement();

        node_meta.setName(NodesElement.NAME);
        node_meta.setVersion("1.0");
        node_meta.setIdCounter(200L);
        node_meta.setLastUpdate(1034334343L);
        node_meta.setElementCount(32L);
        node_meta.setConsistencyGroup(1L);

        md.addMetaDataElement(node_meta);

        final MetaDataElement citation_meta = new MetaDataElement();

        citation_meta.setName("Citation");
        citation_meta.setVersion("1.0");
        citation_meta.setLastUpdate(1034334343L);
        citation_meta.setConsistencyGroup(1L);

        final Map<String, String> prop = new TreeMap<String, String>();
        prop.put("name", "curator");
        prop.put("value", "Ideker Lab");
        citation_meta.addProperty(prop);

        citation_meta.put("key1", "value1");
        citation_meta.put("key2", true);

        md.addMetaDataElement(citation_meta);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true);

        w.start();
        w.writeMetaData(md);
        w.end();

        final String cx_json_str = out.toString();

        final CxElementReader p = CxElementReader.createInstanceWithAllAvailableReaders(cx_json_str, true);

        assertTrue(p.getMetaData().size() == 1);

        final MetaData my_md = p.getMetaData().get(0);

        assertTrue(my_md.size() == 2);

        final MetaDataElement mde0 = my_md.getMetaDataElement(0);
        final MetaDataElement mde1 = my_md.getMetaDataElement(1);

        assertEquals(NodesElement.NAME, mde0.getName());
        assertEquals("1.0", mde0.getVersion());
        assertTrue(mde0.getIdCounter() == 200L);
        assertTrue(mde0.getLastUpdate() == 1034334343L);
        assertTrue(mde0.getElementCount() == 32L);
        assertTrue(mde0.getConsistencyGroup() == 1L);

        assertTrue(mde1.getProperties().size() == 1);
        assertTrue(mde1.getProperties().get(0).get("name").equals("curator"));
        assertTrue(mde1.getProperties().get(0).get("value").equals("Ideker Lab"));

        assertTrue(mde1.get("key1").equals("value1"));
        assertTrue(mde1.get("key2").equals(true));

    }

}
