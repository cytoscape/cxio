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
 * java -cp path/to/cxio-0.0.1.jar org.cxio.cmd.GeneSymbol2EntrezP infile outfile
 *
 *
 */
public final class GeneSymbol2EntrezP {

    private static final boolean ALLOW_UNMAPPED_IDS = true;

    public static void main(final String[] args) throws IOException {
        
        if ( !MappingServiceTools.testMappingService() ) {
            System.out.println("Mapping service at " + MappingServiceTools.DEFAULT_MAP_SERVICE_URL_STR + " seems to have a problem, aborting.");
            System.exit(-1);
        }

        if (args.length != 2) {
            System.out.println("Usage: GeneSymbol2EntrezP <infile:comma-separated matrix> <outfile:comma-separated matrix>");
            System.exit(-1);
        }

        final File infile = new File(args[0]);
        final File outfile = new File(args[1]);

        if (!infile.exists()) {
            System.out.println("does not exist: " + infile);
            System.exit(-1);
        }
        if (outfile.exists()) {
            System.out.println("already exists: " + outfile);
            System.exit(-1);
        }

        System.out.println("Infile : " + infile);
        System.out.println("Outfile: " + outfile);
        System.out.println();

        int counter = -1;

        final PrintWriter writer = new PrintWriter(outfile, "UTF-8");

        boolean first = true;
        try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    final List<String> ids = new ArrayList<String>();

                    if (line.indexOf(',') < 0) {
                        System.out.println("illegal format (no comma found in first line)");
                        System.exit(-1);
                    }
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

        writer.close();
        System.out.println("OK");

    }

    private static SortedMap<String, SortedSet<String>> obtainMappings(final List<String> ids) throws IOException, JsonProcessingException {
        final String res = MappingServiceTools.runQuery(ids, MappingServiceTools.DEFAULT_MAP_SERVICE_URL_STR);
        final SortedSet<String> in_types = new TreeSet<String>();
        in_types.add(MappingServiceTools.SYNONYMS);
        in_types.add(MappingServiceTools.SYMBOL);
        final SortedMap<String, SortedSet<String>> matched_ids = new TreeMap<String, SortedSet<String>>();
        final SortedSet<String> unmatched_ids = new TreeSet<String>();

        MappingServiceTools.parseResponse(res, in_types, MappingServiceTools.HUMAN, MappingServiceTools.GENE_ID, matched_ids, unmatched_ids);
        System.out.println("mapped    : " + matched_ids.size());
        System.out.println("not mapped: " + unmatched_ids.size());
        return matched_ids;
    }
}