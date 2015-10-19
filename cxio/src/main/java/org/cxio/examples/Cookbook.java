package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.OpaqueElement;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.examples.custom_aspects.ContextElement;
import org.cxio.examples.custom_aspects.ContextFragmentReader;
import org.cxio.examples.custom_aspects.ContextFragmentWriter;
import org.cxio.examples.custom_aspects.ProfileElement;
import org.cxio.examples.custom_aspects.ProfileFragmentReader;
import org.cxio.examples.custom_aspects.ProfileFragmentWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Cookbook {

    public static void main(final String[] args) throws IOException, NoSuchAlgorithmException {

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

        basicWriting(edges_elements, nodes_elements, cartesian_elements);

        explicitlyAddingOfWriters(edges_elements, nodes_elements, cartesian_elements);

        writingElementsOneByOne(edges_elements, nodes_elements, cartesian_elements);

        explicitlyAddingWritersWritngOneByOne(edges_elements, nodes_elements, cartesian_elements);

        writingOfAnonymousElements();

        customElementWriterAndReader();

        checksum(edges_elements, nodes_elements, cartesian_elements);

        System.out.println();
        System.out.println();
        System.out.println("OK");

    }

    private final static void basicWriting(final List<AspectElement> edges_elements, final List<AspectElement> nodes_elements, final List<AspectElement> cartesian_elements) throws IOException {
        final OutputStream out = new ByteArrayOutputStream();
        // Pretty pretting is set to true
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);

        w.start();
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(cartesian_elements);
        w.end();
    }

    private final static void customElementWriterAndReader() throws IOException {
        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------

        final List<AspectElement> profile_elements = new ArrayList<AspectElement>();

        profile_elements.add(new ProfileElement("mouse net", "network of mouse interactions"));

        final List<AspectElement> context_elements = new ArrayList<AspectElement>();
        final ContextElement context = new ContextElement();
        context.put("key 1", "value 1");
        context.put("key 2", "value 2");
        context.put("key 3", "value 3");
        context.put("key 4", "value 4");
        context_elements.add(context);

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

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);
        w.addAspectFragmentWriter(ContextFragmentWriter.createInstance());
        w.addAspectFragmentWriter(ProfileFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(profile_elements);
        w.writeAspectElements(context_elements);
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(cartesian_elements);
        w.end();

        final String cx_json_str = out.toString();
        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers = new HashSet<>();
        readers.add(ProfileFragmentReader.createInstance());
        readers.add(ContextFragmentReader.createInstance());

        final CxReader p = CxReader.createInstanceWithAllAvailableReaders(cx_json_str, true, true, readers);

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

    }

    private final static void explicitlyAddingOfWriters(final List<AspectElement> edges_elements, final List<AspectElement> nodes_elements, final List<AspectElement> cartesian_elements)
            throws IOException {
        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstance(out, true);

        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(cartesian_elements);
        w.end();
    }

    private final static void explicitlyAddingWritersWritngOneByOne(final List<AspectElement> edges_elements, final List<AspectElement> nodes_elements, final List<AspectElement> cartesian_elements)
            throws IOException {
        final OutputStream out = new ByteArrayOutputStream();
        // Pretty pretting is set to true.
        final CxWriter w = CxWriter.createInstance(out, true);

        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());

        w.start();

        w.startAspectFragment(NodesElement.NAME);
        for (final AspectElement e : nodes_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.startAspectFragment(EdgesElement.NAME);
        for (final AspectElement e : edges_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.startAspectFragment(CartesianLayoutElement.NAME);
        for (final AspectElement e : cartesian_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.end();
    }

    private final static void writingElementsOneByOne(final List<AspectElement> edges_elements, final List<AspectElement> nodes_elements, final List<AspectElement> cartesian_elements)
            throws IOException {
        final OutputStream out = new ByteArrayOutputStream();
        // Pretty printing is true.
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);

        w.start();

        w.startAspectFragment(NodesElement.NAME);
        for (final AspectElement e : nodes_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.startAspectFragment(EdgesElement.NAME);
        for (final AspectElement e : edges_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.startAspectFragment(CartesianLayoutElement.NAME);
        for (final AspectElement e : cartesian_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.end();
    }

    private final static void writingOfAnonymousElements() throws IOException {
        // Creation of some example AnonymousElements
        final ObjectMapper m = new ObjectMapper();
        final ObjectNode aa1 = m.createObjectNode();
        aa1.put("k1", "a");
        aa1.put("k2", "b");
        final ObjectNode aa2 = m.createObjectNode();
        aa2.put("k1", "c");
        aa2.put("k2", "d");
        final ObjectNode aa3 = m.createObjectNode();
        aa3.put("k1", "e");
        aa3.put("k2", "f");
        final OpaqueElement a1 = new OpaqueElement("anon", aa1);
        final OpaqueElement a2 = new OpaqueElement("anon", aa2);
        final OpaqueElement a3 = new OpaqueElement("anon", aa3);

        final List<String> anon = new ArrayList<String>();
        anon.add(a1.toJsonString());
        anon.add(a2.toJsonString());
        anon.add(a3.toJsonString());

        // Writing to CX
        final OutputStream out = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstance(out, true);

        w.start();
        w.writeOpaqueAspectFragment("anon", anon);
        w.end();

        System.out.println(out.toString());
    }

    private final static void checksum(final List<AspectElement> edges_elements, final List<AspectElement> nodes_elements, final List<AspectElement> cartesian_elements) throws IOException,
            NoSuchAlgorithmException {
        final OutputStream out = new ByteArrayOutputStream();
        // Pretty printing is true.
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);

        w.start();

        w.startAspectFragment(NodesElement.NAME);
        for (final AspectElement e : nodes_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.startAspectFragment(EdgesElement.NAME);
        for (final AspectElement e : edges_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.startAspectFragment(CartesianLayoutElement.NAME);
        for (final AspectElement e : cartesian_elements) {
            w.writeAspectElement(e);
        }
        w.endAspectFragment();

        w.end();

        final byte[] checksum0 = w.getMd5Checksum();

        final CxElementReader r = CxElementReader.createInstanceWithAllAvailableReaders(out.toString(), true, true);

        for (final AspectElement e : r) {
            System.out.println(e);
        }

        final byte[] checksum1 = r.getMd5Checksum();

        for (final byte b : checksum0) {
            System.out.print(b + " ");
        }
        System.out.println();

        for (final byte b : checksum1) {
            System.out.print(b + " ");
        }
        System.out.println();

        if (Util.isAreByteArraysEqual(checksum0, checksum1)) {
            System.out.println("checksums match");
        }
        else {
            System.out.println("checksums do not match!");
        }

    }

}
