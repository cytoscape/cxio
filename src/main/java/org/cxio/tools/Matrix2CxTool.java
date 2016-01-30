package org.cxio.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.cmd.Matrix2Cx;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;

public final class Matrix2CxTool {

    public final static void matrix2Cx(final File infile, final File outfile, final boolean force_symetrical) throws IOException, FileNotFoundException {
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
        final Set<String> egdes_map = new HashSet<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                if (row == 0) {
                    final String[] x = line.split(Matrix2Cx.COLUMN_DELIMITER);
                    final int n = x.length;
                    if (n < 2) {
                        throw new IOException("illegal format (no delimiter found in first line");
                    }
                    for (int c = 0; c < n; ++c) {
                        String name = x[c];
                        if ((name != null) && (name.trim().length() > 0)) {
                            if (node_to_id.containsKey(name)) {
                                final String new_name = name + "--!";
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
                    System.out.println();
                    System.out.println("Number of nodes: " + node_counter);
                }
                else {
                    final String[] x = line.split(Matrix2Cx.COLUMN_DELIMITER);
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
                                        throw new IOException("error: name name in row " + row + " is '" + value + "', expected '" + id_to_node.get(row - 1) + "'");
                                    }
                                }
                                continue;
                            }
                            ++value_counter;
                            double b = -1;
                            try {
                                b = Double.parseDouble(value);
                            }
                            catch (final NumberFormatException e) {
                                throw new IOException("error: could not parse number from " + value);
                            }
                            if (b > 0) {
                                if (!force_symetrical) {
                                    cx_edges.add(new EdgesElement(edge_counter, row - 1, c - 1));
                                    cx_edge_attributes.add(new EdgeAttributesElement((long) edge_counter, Matrix2Cx.EDGE_NAME, value, ATTRIBUTE_DATA_TYPE.DOUBLE));
                                    ++edge_counter;
                                }
                                else {
                                    final int node0 = row - 1;
                                    final int node1 = c - 1;
                                    final String edge_as_str_01 = String.valueOf(node0) + String.valueOf(node1);
                                    if (!egdes_map.contains(edge_as_str_01)) {
                                        cx_edges.add(new EdgesElement(edge_counter, node0, node1));
                                        cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(edge_counter), Matrix2Cx.EDGE_NAME, value, ATTRIBUTE_DATA_TYPE.DOUBLE));
                                        egdes_map.add(edge_as_str_01);
                                        ++edge_counter;
                                    }
                                    final String edge_as_str_10 = String.valueOf(node1) + String.valueOf(node0);
                                    if (!egdes_map.contains(edge_as_str_10)) {
                                        cx_edges.add(new EdgesElement(edge_counter, node1, node0));
                                        cx_edge_attributes.add(new EdgeAttributesElement(Long.valueOf(edge_counter), Matrix2Cx.EDGE_NAME, value, ATTRIBUTE_DATA_TYPE.DOUBLE));
                                        egdes_map.add(edge_as_str_10);
                                        ++edge_counter;
                                    }
                                }
                            }
                        }
                    }
                    if (value_counter != node_counter) {
                        throw new IOException("error: row " + row + " has " + value_counter + " values, expected " + node_counter);
                    }
                }
                ++row;
            }
            if (row != (node_counter + 1)) {
                throw new IOException("error: number of rows is " + row + " , expected " + (node_counter + 1));
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
    }

}
