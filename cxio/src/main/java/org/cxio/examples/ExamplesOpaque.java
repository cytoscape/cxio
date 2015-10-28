package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.aspects.datamodels.CyViewsElement;
import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.AspectElementCounts;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.Util;

public class ExamplesOpaque {

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

        //

        final List<String> applies_to = new ArrayList<String>();
        applies_to.add("12");
        final List<AspectElement> vp_elements = new ArrayList<AspectElement>();
        vp_elements.add(new CyVisualPropertiesElement("network", applies_to, "view1"));

        //

        final List<AspectElement> group_elements = new ArrayList<AspectElement>();

        final CyGroupsElement cy_groups = new CyGroupsElement("group_id", "group_view", "group_name");
        cy_groups.addExternalEdge("e1");
        cy_groups.addExternalEdge("e2");
        cy_groups.addInternalEdge("e3");
        cy_groups.addNode("n1");
        cy_groups.addNode("n2");
        cy_groups.addNode("n3");
        cy_groups.addNode("n4");

        group_elements.add(cy_groups);

        //

        final List<AspectElement> views_elements = new ArrayList<AspectElement>();
        final CyViewsElement cy_views1 = new CyViewsElement("views_subnetwork_id1");
        final CyViewsElement cy_views2 = new CyViewsElement("views_subnetwork_id2");
        views_elements.add(cy_views1);
        views_elements.add(cy_views2);

        //
        final List<AspectElement> hidden_elements = new ArrayList<AspectElement>();
        final HiddenAttributesElement hidden1 = new HiddenAttributesElement("hidden subnetwork1", "hidden name1", true);
        final HiddenAttributesElement hidden2 = new HiddenAttributesElement("hidden subnetwork2", "hidden name2", 1.23);
        hidden_elements.add(hidden1);
        hidden_elements.add(hidden2);

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);

        w.start();
        w.writeAspectElements(edges_elements);
        w.writeAspectElements(nodes_elements);
        w.writeAspectElements(cartesian_elements);
        w.writeAspectElements(edge_attributes_elements);
        w.writeAspectElements(node_attributes_elements);
        w.writeAspectElements(vp_elements);
        w.writeAspectElements(group_elements);
        w.writeAspectElements(views_elements);
        w.writeAspectElements(hidden_elements);
        w.end(true, "");

        final String cx_json_str = out.toString();

        final AspectElementCounts cw = w.getAspectElementCounts();

        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------

        final CxReader p = CxReader.createInstance(cx_json_str, true, true, null);

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

        final AspectElementCounts cr = p.getAspectElementCounts();
        System.out.println(cr);
        final byte[] wmd5 = w.getMd5Checksum();
        Util.validate(wmd5, p.getMd5Checksum(), cw, cr);

        // Reading from CX
        // ---------------

        final CxElementReader p2 = CxElementReader.createInstance(cx_json_str, true, true, null);

        while (p2.hasNext()) {
            System.out.println(p2.getNext());
        }

        final AspectElementCounts cr2 = p2.getAspectElementCounts();
        System.out.println(cr2);
        Util.validate(wmd5, p2.getMd5Checksum(), cw, cr2);
        Util.validate(wmd5, wmd5, cr, cr2);

    }

}
