package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.core.AspectElementCounts;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.core.OpaqueElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;
import org.cxio.metadata.MetaDataElement;
import org.cxio.util.JsonWriter;
import org.cxio.util.Util;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    
    @Test
    public void testX() throws IOException {
        final MetaDataCollection md = new MetaDataCollection();
        md.setVersion("name_2", "v2");
        System.out.println(md.getMetaDataElement("x"));
        
     // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("node0"));
        nodes_elements.add(new NodesElement("node1"));
        nodes_elements.add(new NodesElement("node2"));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement("node0", 12, 21, 1));
        cartesian_elements.add(new CartesianLayoutElement("node1", 42, 23, 2));
        cartesian_elements.add(new CartesianLayoutElement("node2", 34, 23, 3));

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("edge0", "name", "A", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea1 = new EdgeAttributesElement("edge0", "weight", "2", ATTRIBUTE_DATA_TYPE.INTEGER);
        final EdgeAttributesElement ea2 = new EdgeAttributesElement("edge1", "name", "B", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea3 = new EdgeAttributesElement("edge1", "weight", "3", ATTRIBUTE_DATA_TYPE.INTEGER);

        final List<AspectElement> edge_attributes_elements = new ArrayList<AspectElement>();
        edge_attributes_elements.add(ea0);
        edge_attributes_elements.add(ea1);
        edge_attributes_elements.add(ea2);
        edge_attributes_elements.add(ea3);

        final ArrayList<String> v0 = new ArrayList<String>();
        v0.add("0.0");
        v0.add("0.1");
        final ArrayList<String> v1 = new ArrayList<String>();
        v1.add("1.0");
        v1.add("1.1");
        final ArrayList<String> v2 = new ArrayList<String>();
        v2.add("2.0");
        v2.add("2.1");

        final NodeAttributesElement na0 = new NodeAttributesElement("node0", "expression", v0, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na1 = new NodeAttributesElement("node1", "expression", v1, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na2 = new NodeAttributesElement("node2", "expression", v2, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);

        final ArrayList<String> n = new ArrayList<String>();
        n.add("node0");
        n.add("node1");
        n.add("node2");

        final NodeAttributesElement na3 = new NodeAttributesElement("subnet 1", n, "species", "Mus musculus", ATTRIBUTE_DATA_TYPE.STRING);

        final List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        // OpaqueElement:
        final ObjectMapper m = new ObjectMapper();
        final ObjectNode unknown = m.createObjectNode();
        final ObjectNode node1 = m.createObjectNode();
        node1.put("AA", "aa");
        final List<String> l = new ArrayList<String>();
        l.add("1");
        l.add("2");
        l.add("3");
        final ArrayNode ary = m.valueToTree(l);
        unknown.put("A", "a");
        unknown.put("B", "b");
        unknown.put("C", "c");
        unknown.set("D", node1);
        unknown.putArray("E").addAll(ary);
        final ObjectNode node_a = m.createObjectNode();
        final ObjectNode node_b = m.createObjectNode();
        final ObjectNode node_c = m.createObjectNode();
        node_a.put("_a1", "aa1");
        node_a.put("_a2", "aa2");
        node_a.put("_a3", "aa3");
        node_b.put("_b1", "bb1");
        node_b.put("_b2", "bb2");
        node_b.put("_b3", "bb3");
        node_c.put("_c1", "cc1");
        node_c.put("_c2", "cc2");
        node_c.put("_c3", "cc3");
        final List<ObjectNode> abc = new ArrayList<ObjectNode>();
        abc.add(node_a);
        abc.add(node_b);
        abc.add(node_c);
        unknown.putArray("F").addAll(abc);
        final OpaqueElement unknown_element = new OpaqueElement("unknown", unknown);

        // Meta data
        // ---------
        final MetaDataCollection md_pre = new MetaDataCollection();

        final MetaDataElement node_meta = new MetaDataElement();

        node_meta.setName(NodesElement.NAME);
        node_meta.setVersion("1.0");
        node_meta.setIdCounter(200L);
        node_meta.setLastUpdate(1034334343L);
        node_meta.setElementCount(32L);
        node_meta.setConsistencyGroup(1L);

        md_pre.add(node_meta);

        final MetaDataElement citation_meta = new MetaDataElement();

        citation_meta.setName("Citation");
        citation_meta.setVersion("1.0");
        citation_meta.setLastUpdate(1034334343L);
        citation_meta.setConsistencyGroup(1L);

        final Map<String, String> prop = new TreeMap<String, String>();
        prop.put("name", "curator");
        prop.put("value", "Ideker Lab");
        citation_meta.addProperty(prop);

        md_pre.add(citation_meta);

        final MetaDataCollection md_post = new MetaDataCollection();

        final MetaDataElement meta_post = new MetaDataElement();
        meta_post.setName("post meta data");
        meta_post.put("write time", 0.34);
        meta_post.put("success", true);

        md_post.add(meta_post);

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);
        w.addPreMetaData(md_pre);
        w.addPostMetaData(md_post);

        w.start();
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(cartesian_elements);
        w.writeAspectElements(edge_attributes_elements);
        w.writeAspectElements(node_attributes_elements);
        w.writeOpaqueAspectFragment("unknown_element", unknown_element.toJsonString());
        w.end();

        final String cx_json_str = out.toString();
        System.out.println(cx_json_str);
        final AspectElementCounts cw = w.getAspectElementCounts();

        // Reading from CX using CxElementReader
        // -------------------------------------

        final CxElementReader p = CxElementReader.createInstanceWithAllAvailableReaders(cx_json_str, true, true);

        System.out.println();
        System.out.println("Pre meta datas:");

        System.out.print(p.getPreMetaData());

        System.out.println();

        while (p.hasNext()) {
            final AspectElement e = p.getNext();
            System.out.println(e);
        }

        final AspectElementCounts cr = p.getAspectElementCounts();
        System.out.println(cr);

        System.out.println();
        System.out.println("Post meta datas:");

        System.out.print(p.getPostMetaData());

        System.out.println();
        Util.validate(w.getMd5Checksum(), p.getMd5Checksum(), cw, cr);
        
        
        MetaDataCollection metadata = null;
        MetaDataCollection postmetadata = p.getPostMetaData();
        if ( postmetadata !=null) {
            //if( metadata == null) {
           //     metadata = postmetadata;
           // } else {
            metadata = postmetadata;
                for (MetaDataElement e : postmetadata.toCollection()) {
                    Long cnt = e.getIdCounter();
                    if ( cnt !=null) {
                       metadata.setIdCounter(e.getName(),cnt);
                    }
                    cnt = e.getElementCount() ;
                    if ( cnt !=null) {
                           metadata.setElementCount(e.getName(),cnt);
                    }
                }
            }
        
        

    }

}
