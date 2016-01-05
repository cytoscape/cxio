package org.cxio.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for
 *
 * Simple usage as command line:
 *
 * java -cp path/to/cxio-0.0.1.jar org.cxio.tools.GeneId2Entrez
 *
 *
 */
public final class GeneId2Entrez {

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
        final File inmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez_CLEAN_UP.txt");

        PrintWriter outmap_writer = null;
        File outmap = null;
        if (ALLOW_UNMAPPED_IDS) {
            outmap = new File("/Users/cmzmasek/Desktop/gene_id_to_entrez_TO_ADD2.txt");
            outmap_writer = new PrintWriter(outmap, "UTF-8");
        }
        int counter = -10000;

        final PrintWriter writer = new PrintWriter(outfile, "UTF-8");

        final Map<String, String> id_to_entrz = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(inmap))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ((line.length() > 0) && !line.startsWith("#")) {
                    final String[] x = line.split("\t");
                    if (x.length == 2) {
                        if (x[0].indexOf(", ") < 0) {
                            id_to_entrz.put(x[0], x[1]);
                        }
                        else {
                            final String[] y = x[0].split(", ");
                            for (final String mk : y) {
                                id_to_entrz.put(mk, x[1]);
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

        boolean first = true;
        try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    final String[] ids = line.split(",");
                    for (String id : ids) {
                        id = id.trim();
                        if (id.length() > 0) {
                            if (!id_to_entrz.containsKey(id)) {
                                if (!ALLOW_UNMAPPED_IDS) {
                                    System.out.println("not found: " + id);
                                    System.exit(-1);
                                }
                                else {
                                    outmap_writer.println(id + "\t" + counter);
                                    --counter;
                                }
                            }
                            else {
                                writer.print(",");
                                writer.print(id_to_entrz.get(id));
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
}