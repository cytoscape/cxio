package cxio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

public class Examples {

    public static void main(String[] args) throws IOException {

        // Creating same AspectElements and adding them to Lists (representing
        // AspectFragments):
        List<AspectElement> edges_elements = new ArrayList<AspectElement>();
        edges_elements.add(new EdgesElement("edge0", "node0", "node1"));
        edges_elements.add(new EdgesElement("edge1", "node0", "node2"));

        List<AspectElement> nodes_elements = new ArrayList<AspectElement>();
        nodes_elements.add(new NodesElement("node0"));
        nodes_elements.add(new NodesElement("node1"));
        nodes_elements.add(new NodesElement("node2"));

        EdgeAttributesElement ea0 = new EdgeAttributesElement("ea0", "edge0");
        EdgeAttributesElement ea1 = new EdgeAttributesElement("ea1", "edge1");
        ea0.addAttribute("attribute 1", "01");
        ea0.addAttribute("attribute 2", "02");
        ea1.addAttribute("attribute 1", "11");
        ea1.addAttribute("attribute 2", "12");
        List<AspectElement> edge_attributes_elements = new ArrayList<AspectElement>();
        edge_attributes_elements.add(ea0);
        edge_attributes_elements.add(ea1);

        NodeAttributesElement na0 = new NodeAttributesElement("na0", "node0");
        NodeAttributesElement na1 = new NodeAttributesElement("na1", "node1");
        NodeAttributesElement na2 = new NodeAttributesElement("na2", "node2");
        NodeAttributesElement na3 = new NodeAttributesElement("na3");
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
        List<AspectElement> node_attributes_elements = new ArrayList<AspectElement>();
        node_attributes_elements.add(na0);
        node_attributes_elements.add(na1);
        node_attributes_elements.add(na2);
        node_attributes_elements.add(na3);

        // Writing to Json:
        OutputStream out = new ByteArrayOutputStream();
        JsonWriter jw = JsonWriter.createInstance(out);

        EdgesFragmentWriter eaw = EdgesFragmentWriter.createInstance(jw);
        NodesFragmentWriter nfw = NodesFragmentWriter.createInstance(jw);
        EdgeAttributesFragmentWriter eafw = EdgeAttributesFragmentWriter.createInstance(jw);
        NodeAttributesFragmentWriter nafw = NodeAttributesFragmentWriter.createInstance(jw);

        jw.start();
        eaw.write(edges_elements);
        nfw.write(nodes_elements);
        eafw.write(edge_attributes_elements);
        nafw.write(node_attributes_elements);
        jw.end();

        String cx_json_str = out.toString();
        
        // Pretty printing of CX Json
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(cx_json_str, Object.class);
        String pretty_json = mapper.defaultPrettyPrintingWriter().writeValueAsString(json);
        System.out.println(pretty_json);
        
        // Reading from CX Json:
        Set<AspectFragmentReader> handlers = AspectFragmentReaderManager.createInstance().getAvailableAspectReaders();

        CxParser p = CxParser.createInstance(cx_json_str, handlers);

        while (p.hasNext()) {
            List<AspectElement> elements = p.getNext();
            if (!elements.isEmpty()) {
                String aspect_name = elements.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                for (AspectElement element : elements) {
                    System.out.println(element.toString());
                }
            }
        }
    }

}
