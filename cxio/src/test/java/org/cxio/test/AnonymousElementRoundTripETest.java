package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.core.AnonymousElement;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AnonymousElementRoundTripETest {

    @Test
    public void test() throws IOException {
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
        w.writeAspectElements(edges_elements);
        w.end();

        final String cx_json_str = out.toString();

        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------

        final Set<AspectFragmentReader> readers = new HashSet<>();
        readers.add(EdgesFragmentReader.createInstance());
        final CxElementReader r = CxElementReader.createInstance(cx_json_str, true, readers);

        final SortedMap<String, List<AspectElement>> res = CxElementReader.parseAsMap(r);

        assertEquals(res.size(), 6);
        assertTrue(res.containsKey("anonymous"));
        assertTrue(res.containsKey("anonymous too"));
        assertTrue(res.containsKey("edges"));
        assertTrue(res.containsKey("single"));
        assertTrue(res.containsKey("single2"));
        assertTrue(res.containsKey("unknown"));

        final List<AspectElement> res_anonymous = res.get("anonymous");
        assertTrue(res_anonymous.size() == 1);
        assertTrue(res_anonymous.get(0).toString().equals("anonymous: {\"one\":\"1\",\"two\":\"2\",\"1\":{\"_x\":\"x\",\"2\":{\"_y\":\"y\"}}}"));

        final List<AspectElement> res_anonymous_too = res.get("anonymous too");
        assertTrue(res_anonymous_too.size() == 3);
        assertTrue(res_anonymous_too.get(0).toString().equals("anonymous too: {\"qwerty\":\"1\",\"asdf\":\"1\"}"));
        assertTrue(res_anonymous_too.get(1).toString().equals("anonymous too: {\"qwerty\":\"2\",\"asdf\":\"2\"}"));
        assertTrue(res_anonymous_too.get(2).toString().equals("anonymous too: {\"qwerty\":\"3\",\"asdf\":\"3\"}"));

        final List<AspectElement> res_edges = res.get("edges");
        assertTrue(res_edges.size() == 2);

        final List<AspectElement> res_single = res.get("single");
        assertTrue(res_single.size() == 1);
        assertTrue(res_single.get(0).toString().equals("single: {\"1\":\"1\",\"2\":\"2\",\"3\":\"3\",\"4\":\"4\",\"5\":\"5\"}"));

        final List<AspectElement> res_single2 = res.get("single2");
        assertTrue(res_single2.size() == 1);
        assertTrue(res_single2.get(0).toString().equals("single2: {\"1\":\"1\",\"2\":\"2\"}"));

        final List<AspectElement> res_unknown = res.get("unknown");
        assertTrue(res_unknown.size() == 1);
        assertTrue(res_unknown
                .get(0)
                .toString()
                .equals("unknown: {\"A\":\"a\",\"B\":\"b\",\"C\":\"c\",\"D\":{\"AA\":\"aa\"}," + "\"E\":[\"1\",\"2\",\"3\"],\"F\":[{\"_a1\":\"aa1\",\"_a2\":\"aa2\","
                        + "\"_a3\":\"aa3\"},{\"_b1\":\"bb1\",\"_b2\":\"bb2\",\"_b3\":\"bb3\"}," + "{\"_c1\":\"cc1\",\"_c2\":\"cc2\",\"_c3\":\"cc3\"}]}"));

    }

}
