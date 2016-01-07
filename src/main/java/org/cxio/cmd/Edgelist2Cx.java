package org.cxio.cmd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp
 * path/to/cxio-0.0.1.jar:path/to/jackson-databind-2.5.0.jar:path/to/jackson
 * -core-2.5.0.jar:path/to/jackson-annotations-2.5.0.jar
 * org.cxio.tools.Edgelist2Cx
 *
 *
 */
public final class Edgelist2Cx {

    private static final char   COLUMN_DELIMITER = '\t';
    private static final String WEIGHT           = "weight";
    private static final String NAME             = "gene_id";

    public static void main(final String[] args) throws IOException {

        // if (args.length != 0) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }
        final File infile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim.csv");
        final File outfile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim.cx");

        System.out.println("Infile: " + infile);
        System.out.println("Outfile: " + outfile);
        System.out.println();

        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        final BasicTable<String> t = BasicTableParser.parse(infile, COLUMN_DELIMITER);

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
            // cx_node_attributes.add(new
            // NodeAttributesElement(Long.valueOf(e.getKey()), NAME,
            // e.getValue()));
        }

        for (int r = 0; r < t.getNumberOfRows(); ++r) {
            cx_edges.add(new EdgesElement(r, node_to_id.get(t.getValue(0, r)), node_to_id.get(t.getValue(1, r))));
            cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(r), WEIGHT, t.getValue(2, r)));
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
}
