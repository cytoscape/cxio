package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;

public class Examples5 {

    public static void main(final String[] args) throws IOException {
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

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("edge0", "name", "A", ATTRIBUTE_TYPE.STRING);
        final EdgeAttributesElement ea1 = new EdgeAttributesElement("edge0", "weight", "2", ATTRIBUTE_TYPE.INTEGER);
        final EdgeAttributesElement ea2 = new EdgeAttributesElement("edge1", "name", "B", ATTRIBUTE_TYPE.STRING);
        final EdgeAttributesElement ea3 = new EdgeAttributesElement("edge1", "weight", "3", ATTRIBUTE_TYPE.INTEGER);

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

        final NodeAttributesElement na0 = new NodeAttributesElement("node0", "expression", v0, ATTRIBUTE_TYPE.DOUBLE);
        final NodeAttributesElement na1 = new NodeAttributesElement("node1", "expression", v1, ATTRIBUTE_TYPE.DOUBLE);
        final NodeAttributesElement na2 = new NodeAttributesElement("node2", "expression", v2, ATTRIBUTE_TYPE.DOUBLE);

        final ArrayList<String> n = new ArrayList<String>();
        n.add("node0");
        n.add("node1");
        n.add("node2");

        final ArrayList<String> mm = new ArrayList<String>();
        mm.add("Mus musculus");
        final NodeAttributesElement na3 = new NodeAttributesElement(null, n, "species", mm, ATTRIBUTE_TYPE.STRING);

        final List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, true);

        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance()); // Will
        // use
        // default
        // time
        // stamp
        // of
        // w
        // (2015/08/14)

        w.start();
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(cartesian_elements);
        w.end();

        final String cx_json_str = out.toString();
        // System.out.println(cx_json_str);

        // --------------------

        final OutputStream out2 = new ByteArrayOutputStream();

        final CxWriter w2 = CxWriter.createInstance(out2, true);

        w2.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w2.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w2.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());

        w2.start();

        w2.startAspectFragment(NodesElement.NAME);
        for (final AspectElement e : nodes_elements) {
            w2.writeAspectElement(e);
        }
        w2.endAspectFragment();

        w2.startAspectFragment(EdgesElement.NAME);
        for (final AspectElement e : edges_elements) {
            w2.writeAspectElement(e);
        }
        w2.endAspectFragment();

        w2.startAspectFragment(CartesianLayoutElement.NAME);
        for (final AspectElement e : cartesian_elements) {
            w2.writeAspectElement(e);
        }
        w2.endAspectFragment();

        w2.end();

        final String cx_json_str2 = out2.toString();
        System.out.println(cx_json_str2);

    }

}
