package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aux.AspectElementCounts;
import org.cxio.aux.OpaqueElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;
import org.cxio.metadata.MetaDataElement;
import org.cxio.util.CxioUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Examples_CxElementReader_MetaData {

    public static void main(final String[] args) throws IOException {

        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement(0, 0, 1));
        edges_elements.add(new EdgesElement(1, 0, 2));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("0"));
        nodes_elements.add(new NodesElement("1"));
        nodes_elements.add(new NodesElement("2"));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement(0, 12, 21, 1));
        cartesian_elements.add(new CartesianLayoutElement(1, 42, 23, 2));
        cartesian_elements.add(new CartesianLayoutElement(2, 34, 23, 3));

        final EdgeAttributesElement ea0 = new EdgeAttributesElement(0, "name", "A", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea1 = new EdgeAttributesElement(0, "weight", "2", ATTRIBUTE_DATA_TYPE.INTEGER);
        final EdgeAttributesElement ea2 = new EdgeAttributesElement(1, "name", "B", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea3 = new EdgeAttributesElement(1, "weight", "3", ATTRIBUTE_DATA_TYPE.INTEGER);

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

        final NodeAttributesElement na0 = new NodeAttributesElement(0, "expression", v0, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na1 = new NodeAttributesElement(1, "expression", v1, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na2 = new NodeAttributesElement(2, "expression", v2, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);

        final ArrayList<Long> n = new ArrayList<Long>();
        n.add(0L);
        n.add(1L);
        n.add(2L);

        final NodeAttributesElement na3 = new NodeAttributesElement(1, n, "species", "Mus musculus", ATTRIBUTE_DATA_TYPE.STRING);

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

        node_meta.setName(NodesElement.ASPECT_NAME);
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

        citation_meta.addProperty("name", "curator");
        citation_meta.addProperty("value", "Ideker Lab");

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
        w.end(true, "");

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
        CxioUtil.validate(w.getMd5Checksum(), p.getMd5Checksum(), cw, cr);

    }

}
