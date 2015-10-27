package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;

public class ExamplesE {

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

        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstanceNEC(out, true);

        w.start();

        try {

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

        }
        catch (final Exception e) {
            w.setStatus(false, e.getMessage()); // This sets the status message
                                                // to be written ("success" =
                                                // false).
        }

        w.end(true); // This writes out a status message before closing. Default
                     // status is "success" = true).

        final String cx_json_str = out.toString();
        System.out.println(w.getAspectElementCounts());
        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------
        final Set<AspectFragmentReader> readers = new HashSet<>();

        final EdgesFragmentReader er = EdgesFragmentReader.createInstance();

        readers.add(er);
        readers.add(NodesFragmentReader.createInstance());
        readers.add(CartesianLayoutFragmentReader.createInstance());
        readers.add(EdgeAttributesFragmentReader.createInstance());
        readers.add(NodeAttributesFragmentReader.createInstance());
        final CxReader p = CxReader.createInstance(cx_json_str, readers);

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
        System.out.println(p.getStatus());

    }

}
