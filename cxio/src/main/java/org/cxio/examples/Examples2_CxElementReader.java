package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.writers.EdgesFragmentWriter;
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
        final AnonymousElement unknown_element = new AnonymousElement("unknown", unknown);

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

        final AnonymousElement anonymous_element = new AnonymousElement("anonymous", anonymous);

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

        final AnonymousElement anonymous_too_1_elem = new AnonymousElement("anonymous too", anonymous_too_1);
        final AnonymousElement anonymous_too_2_elem = new AnonymousElement("anonymous too", anonymous_too_2);
        final AnonymousElement anonymous_too_3_elem = new AnonymousElement("anonymous too", anonymous_too_3);

        final List<AnonymousElement> anonymous_too_elements = new ArrayList<AnonymousElement>();
        anonymous_too_elements.add(anonymous_too_1_elem);
        anonymous_too_elements.add(anonymous_too_2_elem);
        anonymous_too_elements.add(anonymous_too_3_elem);

        // ---------

        final ObjectNode single = m.createObjectNode();
        single.put("1", "1");
        single.put("2", "2");
        single.put("3", "3");
        single.put("4", "4");
        single.put("5", "5");
        final AnonymousElement single_element = new AnonymousElement("single", single);

        // ---------

        final ObjectNode single2 = m.createObjectNode();
        single2.put("1", "1");
        single2.put("2", "2");
        final AnonymousElement single_element2 = new AnonymousElement("single2", single2);

        // ---------

        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        // ------------
        final ObjectNode aa1 = m.createObjectNode();
        aa1.put("k1", "a");
        aa1.put("k2", "b");
        final ObjectNode aa2 = m.createObjectNode();
        aa2.put("k1", "c");
        aa2.put("k2", "d");
        final ObjectNode aa3 = m.createObjectNode();
        aa3.put("k1", "e");
        aa3.put("k2", "f");
        final AnonymousElement a1 = new AnonymousElement("anon", aa1);
        final AnonymousElement a2 = new AnonymousElement("anon", aa2);
        final AnonymousElement a3 = new AnonymousElement("anon", aa3);

        // Writing to CX
        // -------------

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, true);
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());

        w.start();
        w.writeAnonymousAspectElementAsList(unknown_element);
        w.writeAnonymousAspectElementAsList(anonymous_element);
        w.writeAnonymousAspectElements(anonymous_too_elements);
        w.writeAnonymousAspectElementAsList(single_element);
        w.writeAnonymousAspectElementAsList(single_element2);
        w.writeAnonymousAspectElementAsList(single_element2);
        w.writeAspectElements(edges_elements);

        w.writeAnonymousAspectElementAsList(a1);
        w.writeAnonymousAspectElementAsList(a2);
        w.writeAnonymousAspectElementAsList(a3);

        w.end();

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

        test2();

    }

    static void test2() throws IOException {
        final ObjectMapper m = new ObjectMapper();
        final ObjectNode anonymous = m.createObjectNode();
        anonymous.put("one", "1");
        anonymous.put("two", "2");

        final AnonymousElement anonymous_element = new AnonymousElement("anonymous", anonymous);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, true);

        w.start();
        w.writeAnonymousAspectElementAsList(anonymous_element);
        w.end();

        final String cx_json_str = out.toString();

        System.out.println(cx_json_str);

        // By setting the second argument to true, this reader will return
        // anonymous aspect elements:
        final CxElementReader r = CxElementReader.createInstance(cx_json_str, true, null);

        while (r.hasNext()) {
            System.out.println(r.getNext());
        }

    }

}
