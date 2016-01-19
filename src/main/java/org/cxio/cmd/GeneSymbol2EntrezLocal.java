package org.cxio.cmd;

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
*
* Simple usage as command line:
*
* java -cp path/to/cxio-x.x.x.jar org.cxio.cmd.GeneSymbol2EntrezLocal infile outfile
*
*
*/
public final class GeneSymbol2EntrezLocal {

   
    public static void main(final String[] args) throws IOException {

        if (args.length != 4) {
            System.out.println("Usage: ");
            System.exit(-1);
        }
        //final File infile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim.cx");
        //final File inmap = new File("/Users/cmzmasek/WORK/NBS/gene_id_to_entrez_CLEAN_UP.txt");
        //final File outfile = new File("/Users/cmzmasek/WORK/NBS/HN90_edgelist_trim_entrez.cx");
        
        final File infile = new File(args[0]);
        final File inmap = new File(args[1]);
        final File outfile = new File(args[2]);
        final int counter_start = Integer.parseInt(args[3]);
        
        
        if (!infile.exists()) {
            System.out.println("does not exist: " + infile);
            System.exit(-1);
        }
        if (!inmap.exists()) {
            System.out.println("does not exist: " + inmap);
            System.exit(-1);
        }
        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }
        if (counter_start >= 0 ) {
            System.out.println("counter start must be negative");
            System.exit(-1);
        }
        
        System.out.println("Infile       : " + infile);
        System.out.println("Map file     : " + inmap);
        System.out.println("Outfile      : " + outfile);
        System.out.println("Counter start: " + counter_start);
        System.out.println();

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
                        System.out.println("format error: " + line);
                        System.exit(-1);
                    }
                }
            }
        }

        final CxReader cxr = CxReader.createInstanceWithAllAvailableReaders(infile, true);

        final SortedMap<String, List<AspectElement>> cx = CxReader.parseAsMap(cxr);

        int counter = counter_start;
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
        if (counter != counter_start) {
            System.out.println("encountered " + (counter - counter_start) + " unmapped symbols which need to be added to the map file (see above), aborting");
            System.exit(0);
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