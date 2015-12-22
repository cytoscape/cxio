package org.cxio.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;

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
public final class Matrix2Cx {

    private static final char   COLUMN_DELIMITER = ' ';
    private static final String WEIGHT           = "weight";
    private static final String NAME             = "gene_id";

    public static void main(final String[] args) throws IOException {

        // if (args.length != 0) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }
        final File infile = new File("/Users/cmzmasek/Desktop/m1.txt");
        final File outfile = new File("/Users/cmzmasek/Desktop/m1o.cx");

        final BasicTable<String> t = BasicTableParser.parse(infile, COLUMN_DELIMITER);

        final SortedMap<String, Integer> node_to_id = new TreeMap<String, Integer>();
        final SortedMap<Integer, String> id_to_node = new TreeMap<Integer, String>();

        int node_counter = 0;
        final List<AspectElement> cx_nodes = new ArrayList<AspectElement>();
        for (int c = 0; c < t.getNumberOfColumns(); ++c) {
            final String n = t.getValue(c, 0);
            if ((n != null) && (n.trim().length() > 0)) {
                System.out.println(n);
                if (!node_to_id.containsKey(n)) {
                    node_to_id.put(n, node_counter);
                    id_to_node.put(node_counter, n);
                    cx_nodes.add(new NodesElement(node_counter, n));
                    ++node_counter;
                }
                else {
                    System.out.println("node '" + n + "' is not unique");
                    System.exit(-1);
                }
            }
        }

        final List<AspectElement> cx_edges = new ArrayList<AspectElement>();
        final List<AspectElement> cx_node_attributes = new ArrayList<AspectElement>();
        final List<AspectElement> cx_edge_attributes = new ArrayList<AspectElement>();

        int edge_counter = 0;
        for (int c = 1; c < t.getNumberOfColumns(); ++c) {
            for (int r = 1; r < t.getNumberOfColumns(); ++r) {
                final String v = t.getValueAsString(c, r);
                if ((v != null) && (v.trim().length() > 0)) {
                    final double b = Double.parseDouble(v);
                    if (b > 0) {
                        cx_edges.add(new EdgesElement(edge_counter, r - 1, c - 1));
                        cx_edge_attributes.add(new EdgeAttributesElement((long) edge_counter, "name", v, ATTRIBUTE_DATA_TYPE.STRING));
                        ++edge_counter;
                    }
                }
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
}
