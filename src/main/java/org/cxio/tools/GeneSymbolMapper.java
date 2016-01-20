package org.cxio.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.metadata.MetaDataCollection;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GeneSymbolMapper {

    public static final String FIELD_NODES_NAME    = "nodes.name";
    // public static final String MAP_SERVICE_URL_STR =
    // "http://52.35.61.6:8080/idmapping/v1/map";
    // public static final String MAP_SERVICE_URL_STR =
    // "http://52.35.61.6/idmapping/v1/map";

    public static final String MAP_SERVICE_URL_STR = "http://52.33.174.107:3000/map";

    public final static void run(final File infile, final File outfile, final String field) throws IOException, JsonProcessingException, FileNotFoundException {
        final SortedMap<String, List<AspectElement>> cx = readInfile(infile);
        performMapping(cx, field);
        writeOutfile(outfile, cx);
    }

    private final static void performMapping(final SortedMap<String, List<AspectElement>> cx, final String field) throws IOException, JsonProcessingException {
        if (field.equals(FIELD_NODES_NAME)) {
            final List<AspectElement> cx_nodes = cx.get(NodesElement.ASPECT_NAME);
            final List<String> node_names = new ArrayList<String>();
            for (final AspectElement n : cx_nodes) {
                node_names.add(((NodesElement) n).getNodeName());
            }

            final SortedMap<String, SortedSet<String>> matched_ids = new TreeMap<String, SortedSet<String>>();
            final SortedSet<String> unmatched_ids = new TreeSet<String>();
            System.out.println("going to run query on " + MAP_SERVICE_URL_STR);
            final String res = MappingServiceTools.runQuery(node_names, MAP_SERVICE_URL_STR);
            final SortedSet<String> in_types = new TreeSet<String>();
            in_types.add(MappingServiceTools.SYNONYMS);
            in_types.add(MappingServiceTools.SYMBOL);
            MappingServiceTools.parseResponse(res, in_types, MappingServiceTools.HUMAN, MappingServiceTools.GENE_ID, matched_ids, unmatched_ids);
            // System.out.println(matched_ids);
            // System.out.println(unmatched_ids);
            int counter = -1;
            for (final AspectElement n : cx_nodes) {
                final NodesElement node = (NodesElement) n;
                final String node_name = node.getNodeName();

                if (matched_ids.containsKey(node_name)) {
                    node.setNodeName(matched_ids.get(node_name).first());
                }
                else {
                    System.out.println(node_name + "\t" + counter);
                    --counter;
                }
            }
        }
        else {
            throw new IllegalArgumentException("unknown field: " + field);
        }
    }

    private final static void writeOutfile(final File outfile, final SortedMap<String, List<AspectElement>> cx) throws FileNotFoundException, IOException {
        final List<AspectElement> cx_nodes = cx.get(NodesElement.ASPECT_NAME);
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
    }

    private final static SortedMap<String, List<AspectElement>> readInfile(final File infile) throws IOException {
        final CxReader cxr = CxReader.createInstanceWithAllAvailableReaders(infile, true);
        final SortedMap<String, List<AspectElement>> cx = CxReader.parseAsMap(cxr);
        return cx;
    }

}
