package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.cxio.aux.OpaqueElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Examples2_CxElementReader {

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
        final OpaqueElement unknown_element = new OpaqueElement("unknown", unknown);

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

        final OpaqueElement anonymous_element = new OpaqueElement("anonymous", anonymous);

        // ---------

        final ObjectNode anonymous_too_1 = m.createObjectNode();
        final ObjectNode anonymous_too_2 = m.createObjectNode();
        final ObjectNode anonymous_too_3 = m.createObjectNode();

        anonymous_too_1.put("qwerty", "1");
        anonymous_too_2.put("qwerty", "2");
        anonymous_too_3.put("qwerty", "3");
        anonymous_too_1.put("asdf", "1");
        anonymous_too_2.put("asdf", "2");
        anonymous_too_3.put("asdf", "3");

        final OpaqueElement anonymous_too_1_elem = new OpaqueElement("anonymous too", anonymous_too_1);
        final OpaqueElement anonymous_too_2_elem = new OpaqueElement("anonymous too", anonymous_too_2);
        final OpaqueElement anonymous_too_3_elem = new OpaqueElement("anonymous too", anonymous_too_3);

        final List<OpaqueElement> anonymous_too_elements = new ArrayList<OpaqueElement>();
        anonymous_too_elements.add(anonymous_too_1_elem);
        anonymous_too_elements.add(anonymous_too_2_elem);
        anonymous_too_elements.add(anonymous_too_3_elem);

        // Writing to CX
        // -------------

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, true);

        w.start();
        w.writeOpaqueAspectFragment("unknown", unknown_element.toJsonString());
        w.writeOpaqueAspectFragment("name", anonymous_element);
        w.writeOpaqueAspectFragment2("anon", anonymous_too_elements);

        w.end(true, "");

        final String cx_json_str = out.toString();
        // System.out.println(cx_json_str);

        // Reading from CX
        // ---------------

        final CxElementReader r = CxElementReader.createInstanceWithAllAvailableReaders(cx_json_str, true);

        final List<AspectElement> res = new ArrayList<AspectElement>();
        while (r.hasNext()) {
            final AspectElement element = r.getNext();

            res.add(element);

        }

        for (final AspectElement e : res) {

            System.out.println(e);

        }

        //

        final CxElementReader r2 = CxElementReader.createInstanceWithAllAvailableReaders(cx_json_str, false);

        final SortedMap<String, List<AspectElement>> res2 = CxElementReader.parseAsMap(r2);

        for (final Entry<String, List<AspectElement>> entry : res2.entrySet()) {
            System.out.println("> " + entry.getKey() + ": ");
            for (final AspectElement element : entry.getValue()) {
                System.out.println(element.toString());
            }
        }

    }

}
