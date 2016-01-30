package org.cxio.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.cmd.Edgelist2Cx;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;

public final class Edgelist2CxTool {

    public final static void edgelist2Cx(final File outfile, final boolean force_symetrical, final BasicTable<String> t) throws FileNotFoundException, IOException {
        final SortedMap<String, Integer> node_to_id = new TreeMap<String, Integer>();
        final SortedMap<Integer, String> id_to_node = new TreeMap<Integer, String>();

        int node_counter = 0;
        for (int r = 0; r < t.getNumberOfRows(); ++r) {
            final String node_0 = t.getValue(0, r);
            final String node_1 = t.getValue(1, r);

            if (!node_to_id.containsKey(node_0)) {
                node_to_id.put(node_0, node_counter);
                id_to_node.put(node_counter, node_0);
                ++node_counter;
            }
            if (!node_to_id.containsKey(node_1)) {
                node_to_id.put(node_1, node_counter);
                id_to_node.put(node_counter, node_1);
                ++node_counter;
            }
        }

        final List<AspectElement> cx_nodes = new ArrayList<AspectElement>();
        final List<AspectElement> cx_edges = new ArrayList<AspectElement>();
        final List<AspectElement> cx_node_attributes = new ArrayList<AspectElement>();
        final List<AspectElement> cx_edge_attributes = new ArrayList<AspectElement>();

        for (final Entry<Integer, String> e : id_to_node.entrySet()) {
            cx_nodes.add(new NodesElement(e.getKey(), e.getValue()));
        }
        if (!force_symetrical) {
            for (int r = 0; r < t.getNumberOfRows(); ++r) {
                cx_edges.add(new EdgesElement(r, node_to_id.get(t.getValue(0, r)), node_to_id.get(t.getValue(1, r))));
                cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(r), Edgelist2Cx.WEIGHT, t.getValue(2, r)));
            }
        }
        else {
            final Set<String> egdes_map = new HashSet<String>();
            int counter = 0;
            for (int r = 0; r < t.getNumberOfRows(); ++r) {
                final Integer node0 = node_to_id.get(t.getValue(0, r));
                final Integer node1 = node_to_id.get(t.getValue(1, r));
                final String w = t.getValue(2, r);
                counter = Edgelist2CxTool.addSymmetricalEdges(cx_edges, cx_edge_attributes, egdes_map, counter, node0, node1, w);
                counter = Edgelist2CxTool.addSymmetricalEdges(cx_edges, cx_edge_attributes, egdes_map, counter, node1, node0, w);
            }
        }

        final int id_counter = cx_nodes.size() + cx_node_attributes.size() + cx_edges.size() + cx_edge_attributes.size();
        final MetaDataCollection pre_meta_data = new MetaDataCollection();
        pre_meta_data.addMetaDataElement(cx_nodes, 1, "1.0", 1, id_counter);
        pre_meta_data.addMetaDataElement(cx_node_attributes, 1, "1.0", 1, id_counter);
        pre_meta_data.addMetaDataElement(cx_edges, 1, "1.0", 1, id_counter);
        pre_meta_data.addMetaDataElement(cx_edge_attributes, 1, "1.0", 1, id_counter);

        final OutputStream out = new FileOutputStream(outfile);
        final CxWriter w = CxWriter.createInstanceWithAllAvailableWriters(out, true, true);
        w.addPreMetaData(pre_meta_data);
        w.start();
        w.writeAspectElements(cx_nodes);
        w.writeAspectElements(cx_edges);
        w.writeAspectElements(cx_node_attributes);
        w.writeAspectElements(cx_edge_attributes);
        w.end(true, "");

        out.close();
    }

    private final static int addSymmetricalEdges(final List<AspectElement> cx_edges,
                                                final List<AspectElement> cx_edge_attributes,
                                                final Set<String> egdes_map,
                                                int counter,
                                                final Integer node0,
                                                final Integer node1,
                                                final String weight) {
        final String edge_as_str = String.valueOf(node0) + String.valueOf(node1);
        if (!egdes_map.contains(edge_as_str)) {
            cx_edges.add(new EdgesElement(counter, node0, node1));
            cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(counter), Edgelist2Cx.WEIGHT, weight));
            egdes_map.add(edge_as_str);
            ++counter;
        }
        return counter;
    }

}
