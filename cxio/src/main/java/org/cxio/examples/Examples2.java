package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Examples2 {

    public static void main(final String[] args) throws IOException {

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

        // ---------

        final ObjectNode anonymous = m.createObjectNode();
        anonymous.put("one", "1");
        anonymous.put("two", "2");
        final ObjectNode anonymous2 = m.createObjectNode();
        final ObjectNode anonymous3 = m.createObjectNode();
        anonymous2.put("_x", "x");
        anonymous3.put("_y", "y");
        anonymous2.set("2", anonymous3);
        anonymous.set("1", anonymous2);
        // ---------

        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        // Writing to CX
        // -------------

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, true);
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());

        w.start();
        w.write("unknown", unknown);
        w.write("anonymous", anonymous);
        w.write(edges_elements);
        w.end();

        final String cx_json_str = out.toString();
        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------
        final CxReader p = CxReader.createInstance(cx_json_str);
        p.addAspectFragmentReader(EdgesFragmentReader.createInstance());

        while (p.hasNext()) {
            final List<AspectElement> elements = p.getNext();
            if (!elements.isEmpty()) {
                final String aspect_name = elements.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                for (final AspectElement element : elements) {
                    System.out.println(element.toString());
                }
            }
        }

        // System.out.println( m.writeValueAsString(node) );
        // final OutputStream out = new ByteArrayOutputStream();
        // m.writeValue(out, node);
        // System.out.println(out.toString());
        //
        // final OutputStream out2 = new ByteArrayOutputStream();
        // final JsonFactory f = new JsonFactory();
        // JsonGenerator g = f.createGenerator(out2);
        // g.useDefaultPrettyPrinter();
        //
        // DefaultSerializerProvider.Impl sp = new
        // DefaultSerializerProvider.Impl();
        // node.serialize(g ,sp);
        //
        // System.out.println(out2.toString());

    }

}
