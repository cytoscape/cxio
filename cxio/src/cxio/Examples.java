package cxio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Examples {

    public static void main(final String[] args) throws IOException {

        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments):
        final List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        final List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("node0"));
        nodes_elements.add(new NodesElement("node1"));
        nodes_elements.add(new NodesElement("node2"));

        final List<AspectElement> cartesian_elements = new ArrayList<AspectElement>();
        cartesian_elements.add(new CartesianLayoutElement("node0", 12, 21));
        cartesian_elements.add(new CartesianLayoutElement("node1", 42, 23));
        cartesian_elements.add(new CartesianLayoutElement("node2", 34, 23));

        final EdgeAttributesElement ea0 = new EdgeAttributesElement("ea0", "edge0");
        final EdgeAttributesElement ea1 = new EdgeAttributesElement("ea1", "edge1");
        ea0.addAttribute("attribute 1", "01");
        ea0.addAttribute("attribute 2", "02");
        ea1.addAttribute("attribute 1", "11");
        ea1.addAttribute("attribute 2", "12");
        final List<AspectElement> edge_attributes_elements = new ArrayList<AspectElement>();
        edge_attributes_elements.add(ea0);
        edge_attributes_elements.add(ea1);

        final NodeAttributesElement na0 = new NodeAttributesElement("na0", "node0");
        final NodeAttributesElement na1 = new NodeAttributesElement("na1", "node1");
        final NodeAttributesElement na2 = new NodeAttributesElement("na2", "node2");
        final NodeAttributesElement na3 = new NodeAttributesElement("na3");
        na0.addAttribute("attribute x", "0.0");
        na0.addAttribute("attribute x", "0.1");
        na1.addAttribute("attribute x", "1.0");
        na1.addAttribute("attribute x", "1.1");
        na2.addAttribute("attribute x", "2.0");
        na2.addAttribute("attribute x", "2.1");
        na3.addAttribute("species", "Mus musculus");
        na3.addNode("node0");
        na3.addNode("node1");
        na3.addNode("node2");
        final List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        // Writing to Json:
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out);

        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());

        w.start();
        w.write(edges_elements);
        w.write(nodes_elements);
        w.write(cartesian_elements);
        w.write(edge_attributes_elements);
        w.write(node_attributes_elements);
        w.end();

        final String cx_json_str = out.toString();

        // Reading from CX Json:
        final Set<AspectFragmentReader> readers = AspectFragmentReaderManager.createInstance()
                .getAvailableAspectFragmentReaders();

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
    }

}
