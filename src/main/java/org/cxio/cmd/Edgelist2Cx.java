package org.cxio.cmd;

import java.io.File;
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
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;
import org.cxio.tools.BasicTable;
import org.cxio.tools.BasicTableParser;

/**
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-x.x.x.jar org.cxio.cmd.Edgelist2Cx -f infile outfile
 *
 *
 */
public final class Edgelist2Cx {

    private static final char   COLUMN_DELIMITER = '\t';
    private static final String WEIGHT           = "weight";

    public static void main(final String[] args) throws IOException {

        File infile = null;
        File outfile = null;
        boolean force_symetrical = false;
        if (args.length == 2) {
            infile = new File(args[0]);
            outfile = new File(args[1]);
        }
        else if (args.length == 3) {
            infile = new File(args[1]);
            outfile = new File(args[2]);
            if (args[0].equals("-f")) {
                force_symetrical = true;
            }
            else {
                error();
            }
        }
        else {
            error();
        }

        if (!infile.exists()) {
            System.out.println("does not exist: " + infile);
            System.exit(-1);
        }

        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        System.out.println("Infile           : " + infile);
        System.out.println("Outfile          : " + outfile);
        System.out.println("Force symmetrical: " + force_symetrical);
        System.out.println();

        final BasicTable<String> t = BasicTableParser.parse(infile, COLUMN_DELIMITER);

        if (t.getNumberOfRows() < 2) {
            System.out.println("edge-list seems ill-formatted");
            System.exit(-1);
        }
        if (t.getNumberOfColumns() != 3) {
            System.out.println("edge-list is ill-formatted, number of columns is: " + t.getNumberOfColumns());
            System.exit(-1);
        }

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

                cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(r), WEIGHT, t.getValue(2, r)));
            }
        }
        else {
            final Set<String> egdes_map = new HashSet<String>();
            int counter = 0;
            for (int r = 0; r < t.getNumberOfRows(); ++r) {
                final Integer node0 = node_to_id.get(t.getValue(0, r));
                final Integer node1 = node_to_id.get(t.getValue(1, r));
                final String w = t.getValue(2, r);
                counter = addSymmetricalEdges(cx_edges, cx_edge_attributes, egdes_map, counter, node0, node1, w);
                counter = addSymmetricalEdges(cx_edges, cx_edge_attributes, egdes_map, counter, node1, node0, w);
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

        System.out.println("OK");

    }

    private static void error() {

        System.out.println("Usage: Edgelist2Cx [-f] <infile: tab-separated edge-list> <outfile: network in cx-format>");
        System.exit(-1);

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
            cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(counter), WEIGHT, weight));
            egdes_map.add(edge_as_str);
            ++counter;
        }
        return counter;
    }
}
