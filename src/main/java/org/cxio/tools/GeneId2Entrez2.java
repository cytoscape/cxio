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
public final class GeneId2Entrez2 {

    private static final char    COLUMN_DELIMITER   = ',';
    private static final boolean ALLOW_UNMAPPED_IDS = false;

    public static void main(final String[] args) throws IOException {

        // if (args.length != 0) {
        // System.out.println("Usage: ");
        // System.exit(-1);
        // }

        // final File infile = new File("/Users/cmzmasek/Desktop/test.csv");
        final File infile = new File("/Users/cmzmasek/WORK/datafiles/PC_smData_labelled.csv");
        final File outfile = new File("/Users/cmzmasek/Desktop/patient_files/PC_smData_labelled_entrez.csv");
        
        if ( outfile.exists()) {
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

        final BasicTable<String> t = BasicTableParser.parse(infile, COLUMN_DELIMITER);

        for (int c = 0; c < (t.getNumberOfColumns() - 1); ++c) {
            final String val = t.getValue(c, 0);
            if (!id_to_entrz.containsKey(val)) {
                if (!ALLOW_UNMAPPED_IDS) {
                    System.out.println("not found: " + val);
                    System.exit(-1);
                }
                else {
                    outmap_writer.println(val + "\t" + counter);
                    --counter;
                }
            }
            else {
                writer.print(",");
                writer.print(id_to_entrz.get(val));
            }
        }
        writer.println();
        writer.flush();

        for (int r = 1; r < t.getNumberOfRows(); ++r) {
            for (int c = 0; c < t.getNumberOfColumns(); ++c) {
                if (c > 0) {
                    writer.print(",");
                }
                writer.print(t.getValue(c, r));
            }
            writer.println();
            writer.flush();
        }

        if (ALLOW_UNMAPPED_IDS) {
            outmap_writer.close();
        }
        writer.close();
        System.out.println("OK");

    }
}