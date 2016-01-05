package org.cxio.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar org.cxio.tools.GeneId2Entrez
 *
 *
 */
public final class GeneId2EntrezCX {

    private final static int COUNTER_START = -20000;

    public static void main(final String[] args) throws IOException {

        // if (args.length != 0) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }
        final File infile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim.cx");
        final File inmap = new File("/Users/cmzmasek/WORK/NBS/gene_id_to_entrez_CLEAN_UP.txt");
        final File outfile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim_entrez.cx");

        System.out.println("Infile: " + infile);
        System.out.println("Map: " + inmap);
        System.out.println("Outfile: " + outfile);
        System.out.println();

        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        final Map<String, String> id_to_entrez = new HashMap<String, String>();

        try (BufferedReader br = new BufferedReader(new FileReader(inmap))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ((line.length() > 0) && !line.startsWith("#")) {
                    final String[] x = line.split("\t");
                    if (x.length == 2) {
                        if (x[0].indexOf(", ") < 0) {
                            id_to_entrez.put(x[0], x[1]);
                        }
                        else {
                            final String[] y = x[0].split(", ");
                            for (final String mk : y) {
                                id_to_entrez.put(mk, x[1]);
                            }
                        }

                    }
                    else if (x.length != 1) {
                        System.out.println("error");
                        System.exit(-1);
                    }
                }
            }
        }

        final CxReader cxr = CxReader.createInstanceWithAllAvailableReaders(infile, true);

        final SortedMap<String, List<AspectElement>> cx = CxReader.parseAsMap(cxr);

        int counter = COUNTER_START;
        final List<AspectElement> cx_nodes = cx.get(NodesElement.ASPECT_NAME);
        for (final AspectElement n : cx_nodes) {
            final NodesElement node = (NodesElement) n;
            final String node_name = node.getNodeName();

            if (id_to_entrez.containsKey(node_name)) {
                node.setNodeName(id_to_entrez.get(node_name));
            }
            else {
                System.out.println(node_name + "\t" + counter);
                --counter;
            }
        }
        if (counter != COUNTER_START) {
            System.exit(-1);
        }
        final List<AspectElement> cx_edges = cx.get(EdgesElement.ASPECT_NAME);
        final List<AspectElement> cx_node_attributes = cx.get(NodeAttributesElement.ASPECT_NAME);
        final List<AspectElement> cx_edge_attributes = cx.get(EdgeAttributesElement.ASPECT_NAME);

        int id_counter = 0;
        if (cx_nodes != null) {
            id_counter += cx_nodes.size();
        }
        if (cx_edges != null) {
            id_counter += cx_edges.size();
        }
        if (cx_node_attributes != null) {
            id_counter += cx_node_attributes.size();
        }
        if (cx_edge_attributes != null) {
            id_counter += cx_edge_attributes.size();
        }

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