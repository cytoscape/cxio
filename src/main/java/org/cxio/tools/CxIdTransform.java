package org.cxio.tools;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * java -Xmx1024m -cp
 * path/to/cxio-0.0.1.jar:path/to/jackson-databind-2.5.0.jar:path/to/jackson
 * -core-2.5.0.jar:path/to/jackson-annotations-2.5.0.jar
 * org.cxio.tools.Matrix2Cx
 *
 *
 */
public final class CxIdTransform {

    private static final String COLUMN_DELIMITER = "\\s+";
    private static final String EDGE_NAME        = "weight";

    public static void main(final String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: Matrix2Cx <infile> <outfile>");
            System.exit(-1);
        }
        final String infile = args[0];
        final String outfile = args[1];

        // final File infile = new File("/Users/cmzmasek/Desktop/m1.txt");
        // final File infile = new File("/Users/cmzmasek/Desktop/X/HN90.csv");
        // final File outfile = new File("/Users/cmzmasek/Desktop/m1o.cx");

        System.out.println("Infile: " + infile);
        System.out.println("Outfile: " + outfile);
        System.out.println();

        final SortedMap<String, Integer> node_to_id = new TreeMap<String, Integer>();
        final SortedMap<Integer, String> id_to_node = new TreeMap<Integer, String>();
        final List<AspectElement> cx_nodes = new ArrayList<AspectElement>();

        final Map<String, String> updated_names = new HashMap<String, String>();

        final List<AspectElement> cx_edges = new ArrayList<AspectElement>();
        final List<AspectElement> cx_node_attributes = new ArrayList<AspectElement>();
        final List<AspectElement> cx_edge_attributes = new ArrayList<AspectElement>();
        int edge_counter = 0;
        int node_counter = 0;
        int row = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (row == 0) {
                    final String[] x = line.trim().split(COLUMN_DELIMITER);
                    final int n = x.length;
                    for (int c = 0; c < n; ++c) {
                        String name = x[c];
                        if ((name != null) && (name.trim().length() > 0)) {
                            if (node_to_id.containsKey(name)) {
                                final String new_name = name + "_2";
                                System.out.print("warning: node '" + name + "' is not unique, replacing it with '" + new_name + "'");
                                updated_names.put(name, new_name);
                                name = new_name;
                                System.out.println();
                            }
                            node_to_id.put(name, node_counter);
                            id_to_node.put(node_counter, name);
                            cx_nodes.add(new NodesElement(node_counter, name));
                            ++node_counter;
                        }
                    }
                    System.out.println("Number of nodes: " + node_counter);
                }
                else {
                    final String[] x = line.trim().split(COLUMN_DELIMITER);
                    int value_counter = 0;
                    final int n = x.length;
                    for (int c = 0; c < n; ++c) {
                        final String value = x[c];
                        if ((value != null) && (value.trim().length() > 0)) {
                            if (c == 0) {
                                if (!value.equals(id_to_node.get(row - 1))) {
                                    boolean ok = false;
                                    if (updated_names.containsKey(value)) {
                                        if (updated_names.get(value).equals(id_to_node.get(row - 1))) {
                                            ok = true;
                                        }
                                    }
                                    if (!ok) {
                                        System.out.println("error: name name in row " + row + " is '" + value + "', expected '" + id_to_node.get(row - 1) + "'");
                                        System.exit(-1);
                                    }
                                }
                                continue;
                            }

                            ++value_counter;
                            final double b = Double.parseDouble(value);
                            if (b > 0) {
                                cx_edges.add(new EdgesElement(edge_counter, row - 1, c - 1));
                                cx_edge_attributes.add(new EdgeAttributesElement((long) edge_counter, EDGE_NAME, value, ATTRIBUTE_DATA_TYPE.DOUBLE));
                                ++edge_counter;
                            }
                        }
                    }
                    if (value_counter != node_counter) {
                        System.out.println("error: row " + row + " has " + value_counter + " values, expected " + node_counter);
                        System.exit(-1);
                    }
                }
                ++row;
            }
            if (row != (node_counter + 1)) {
                System.out.println("error: number of rows is " + row + " , expected " + (node_counter + 1));
                System.exit(-1);
            }
        }
        System.out.println("Number of edges: " + cx_edges.size());
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
        System.out.println();
        System.out.println("OK");

    }
}
