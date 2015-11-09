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
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgeAttributesFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodeAttributesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;

public class Examples5 {

    public static void main(final String[] args) throws IOException {
        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments)
        // --------------------------------------------------------------------
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement(0, 0, 1));
        edges_elements.add(new EdgesElement(1, 0, 2));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement(0));
        nodes_elements.add(new NodesElement(1));
        nodes_elements.add(new NodesElement(2));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement(0, 12, 21.0, 1.0));
        cartesian_elements.add(new CartesianLayoutElement(1, 42, 23.0, 2.0));
        cartesian_elements.add(new CartesianLayoutElement(2, 34, 23.0, 3.0));

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

        final NodeAttributesElement na3 = new NodeAttributesElement(n, "species", "Mus musculus", ATTRIBUTE_DATA_TYPE.STRING);

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
        w.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(node_attributes_elements);
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(edge_attributes_elements);
        w.writeAspectElements(cartesian_elements);
        w.end(true, "");

        final String cx_json_str = out.toString();
        System.out.println(cx_json_str);
        System.out.println(w.getAspectElementCounts());

        // --------------------

        final OutputStream out2 = new ByteArrayOutputStream();

        final CxWriter w2 = CxWriter.createInstance(out2, true);

        w2.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w2.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w2.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());

        w2.start();

        w2.startAspectFragment(NodesElement.ASPECT_NAME);
        for (final AspectElement e : nodes_elements) {
            w2.writeAspectElement(e);
        }
        w2.endAspectFragment();

        w2.startAspectFragment(EdgesElement.ASPECT_NAME);
        for (final AspectElement e : edges_elements) {
            w2.writeAspectElement(e);
        }
        w2.endAspectFragment();

        w2.startAspectFragment(CartesianLayoutElement.ASPECT_NAME);
        for (final AspectElement e : cartesian_elements) {
            w2.writeAspectElement(e);
        }
        w2.endAspectFragment();

        w2.end(true, "");

        final String cx_json_str2 = out2.toString();
        System.out.println(cx_json_str2);
        System.out.println(w.getAspectElementCounts());
    }

}
