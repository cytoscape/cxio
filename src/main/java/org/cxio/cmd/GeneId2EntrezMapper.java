package org.cxio.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.cxio.tools.MappingServiceTools;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar org.cxio.tools.GeneId2Entrez
 *
 *
 */
public final class GeneId2EntrezMapper {

    private static final boolean ALLOW_UNMAPPED_IDS = false;

    public static void main(final String[] args) throws IOException {

        // if (args.length != 0) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }

        // final File infile = new File("/Users/cmzmasek/Desktop/test.csv");
        final File infile = new File("/Users/cmzmasek/WORK/datafiles/PC_smData_labelled.csv");
        final File outfile = new File("/Users/cmzmasek/Desktop/patient_files/PC_smData_labelled.csv");

        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        PrintWriter outmap_writer = null;
        File outmap = null;
        if (ALLOW_UNMAPPED_IDS) {
            outmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez_TO_ADD.txt");
            outmap_writer = new PrintWriter(outmap, "UTF-8");
        }
        int counter = -10000;

        final PrintWriter writer = new PrintWriter(outfile, "UTF-8");

        boolean first = true;
        try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    final List<String> ids = new ArrayList<String>();
                    for (String id : line.split(",")) {
                        id = id.trim();
                        if (id.length() > 0) {
                            ids.add(id);
                        }
                    }

                    final SortedMap<String, SortedSet<String>> matched_ids = obtainMappings(ids);

                    for (final String id : ids) {
                        if (matched_ids.containsKey(id)) {
                            writer.print(",");
                            writer.print(matched_ids.get(id).first());
                        }
                        else {
                            if (!ALLOW_UNMAPPED_IDS) {
                                System.out.println("not found: " + id);
                                System.exit(-1);
                            }
                            else {
                                System.out.println(id + "\t" + counter);
                                --counter;
                            }
                        }
                    }
                    writer.println();
                    writer.flush();
                }
                else {
                    writer.print(line);
                    writer.println();
                    writer.flush();
                }
            }
        }

        if (ALLOW_UNMAPPED_IDS) {
            outmap_writer.close();
        }
        writer.close();
        System.out.println("OK");

    }

    private static SortedMap<String, SortedSet<String>> obtainMappings(final List<String> ids) throws IOException, JsonProcessingException {
        final String res = MappingServiceTools.runQuery(ids, "http://54.200.201.85:3000/map");
        final SortedSet<String> in_types = new TreeSet<String>();
        in_types.add(MappingServiceTools.SYNONYMS);
        in_types.add(MappingServiceTools.SYMBOL);
        final SortedMap<String, SortedSet<String>> matched_ids = new TreeMap<String, SortedSet<String>>();
        final SortedSet<String> unmatched_ids = new TreeSet<String>();

        MappingServiceTools.parseResponse(res, in_types, MappingServiceTools.HUMAN, MappingServiceTools.GENE_ID, matched_ids, unmatched_ids);
        System.out.println(matched_ids);
        System.out.println(unmatched_ids);
        return matched_ids;
    }
}