package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.CyGroupsElement;
import org.cxio.aspects.datamodels.CyViewsElement;
import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aux.AspectElementCounts;
import org.cxio.core.CxElementReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;

public class ExamplesOpaque {

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
        cartesian_elements.add(new CartesianLayoutElement(0L, 12L, 21.0, 1.0));
        cartesian_elements.add(new CartesianLayoutElement(1L, 42L, 23.0, 2.0));
        cartesian_elements.add(new CartesianLayoutElement(2L, 34L, 23.0, 3.0));

        final EdgeAttributesElement ea0 = new EdgeAttributesElement(0L, "name", "A", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea1 = new EdgeAttributesElement(0L, "weight", "2", ATTRIBUTE_DATA_TYPE.INTEGER);
        final EdgeAttributesElement ea2 = new EdgeAttributesElement(1L, "name", "B", ATTRIBUTE_DATA_TYPE.STRING);
        final EdgeAttributesElement ea3 = new EdgeAttributesElement(1L, "weight", "3", ATTRIBUTE_DATA_TYPE.INTEGER);

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

        final NodeAttributesElement na0 = new NodeAttributesElement(0L, "expression", v0, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na1 = new NodeAttributesElement(1L, "expression", v1, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        final NodeAttributesElement na2 = new NodeAttributesElement(2L, "expression", v2, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);

        final ArrayList<Long> n = new ArrayList<Long>();
        n.add(0L);
        n.add(1L);
        n.add(2L);

        final NodeAttributesElement na3 = new NodeAttributesElement(1L, n, "species", "Mus musculus", ATTRIBUTE_DATA_TYPE.STRING);

        final List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        //

        final List<Long> applies_to = new ArrayList<Long>();
        applies_to.add(12L);
        final List<AspectElement> vp_elements = new ArrayList<AspectElement>();
        vp_elements.add(new CyVisualPropertiesElement("network", applies_to, 1));

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
        final CyViewsElement cy_views1 = new CyViewsElement(1);
        final CyViewsElement cy_views2 = new CyViewsElement(2);
        views_elements.add(cy_views1);
        views_elements.add(cy_views2);

        //
        final List<AspectElement> hidden_elements = new ArrayList<AspectElement>();
        final HiddenAttributesElement hidden1 = new HiddenAttributesElement(1, "hidden name1", true);
        final HiddenAttributesElement hidden2 = new HiddenAttributesElement(2, "hidden name2", 1.23);
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
        CxioUtil.validate(wmd5, p.getMd5Checksum(), cw, cr);

        // Reading from CX
        // ---------------

        final CxElementReader p2 = CxElementReader.createInstance(cx_json_str, true, true, null);

        while (p2.hasNext()) {
            System.out.println(p2.getNext());
        }

        final AspectElementCounts cr2 = p2.getAspectElementCounts();
        System.out.println(cr2);
        CxioUtil.validate(wmd5, p2.getMd5Checksum(), cw, cr2);
        CxioUtil.validate(wmd5, wmd5, cr, cr2);

    }

}
